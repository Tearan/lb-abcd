package com.lb.abcd.jwt.util;

import com.lb.abcd.jwt.config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;


/**
 * @ClassName JWTUtil
 * @Description  JWT工具类
 *              主要实现token的签发、验证和数据解析。
 * @Author Terran
 * @Date 2021/1/31 14:21
 * @Version 1.0
 */
@Slf4j
public class JWTUtil {

    private static Duration accessTokenExpireTime;

    private static Duration refreshTokenExpirePcTime;

    private static Duration refreshTokenExpireAppTime;

    private static String tokenSecret;

    private static String issuer;

    /**
     * 设置token过期时间及密钥盐
     */
    public static void setTokenSettings(JwtConfig jwtConfig) {
        accessTokenExpireTime = jwtConfig.getAccessTokenExpireTime();
        refreshTokenExpirePcTime = jwtConfig.getRefreshTokenExpirePcTime();
        refreshTokenExpireAppTime = jwtConfig.getRefreshTokenExpireAppTime();
        tokenSecret = jwtConfig.getTokenSecret();
        issuer = jwtConfig.getIssuer();
    }

    /**
     * 生成 access_token
     */
    public static String getAccessToken(String subject, Map<String,Object> claims){

        return generateToken(issuer,subject,claims,accessTokenExpireTime.toMillis(),tokenSecret);
    }
    /**
     * 签发token
     */
    public static String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMillis, String secret) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("type","JWT");
        if(null!=claims){
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        builder.setIssuedAt(now);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        builder.signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    // 上面我们已经有生成 access_token 的方法，下面加入生成 refresh_token 的方法(PC 端过期时间短一些)

    /**
     * 生产 PC refresh_token
     */
    public static String getRefreshToken(String subject,Map<String,Object> claims){
        return generateToken(issuer,subject,claims,refreshTokenExpirePcTime.toMillis(),tokenSecret);
    }

    /**
     * 生产 App端 refresh_token
     */
    public static String getRefreshAppToken(String subject,Map<String,Object> claims){
        return generateToken(issuer,subject,claims,refreshTokenExpireAppTime.toMillis(),tokenSecret);
    }

    /**
     * 从令牌中获取数据声明
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(tokenSecret)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            if(e instanceof ClaimJwtException){
                claims = ((ClaimJwtException) e).getClaims();
            }
        }
        return claims;
    }

    /**
     * 获取用户id
     */
    public static String getUserId(String token){
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            log.error("获取用户id:【{}】",e);
        }
        return userId;
    }

    /**
     * 获取用户名
     */
    public static String getUserName(String token){
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            /** 用戶名称 KEY*/
            username = (String) claims.get("jwt-user-name-key");
        } catch (Exception e) {
            log.error("获取用户名:【{}】",e);
        }
        return username;
    }

    /**
     * 验证token 是否过期
     */
    public static Boolean isTokenExpired(String token) {

        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("验证token,是否过期:【{}】",e);
            return true;
        }
    }
    /**
     * 校验令牌
     */
    public static Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (null!=claimsFromToken && !isTokenExpired(token));
    }

    /**
     * 刷新token
     */
    public static String refreshToken(String refreshToken,Map<String, Object> claims) {
        String refreshedToken;
        try {
            Claims parserclaims = getClaimsFromToken(refreshToken);

            /** 刷新token的时候如果为空说明原先的 用户信息不变 所以就引用上个token里的内容 */
            if(null == claims){
                claims = parserclaims;
            }
            refreshedToken = generateToken(parserclaims.getIssuer(),parserclaims.getSubject(),claims,accessTokenExpireTime.toMillis(),tokenSecret);
        } catch (Exception e) {
            refreshedToken = null;
            log.error("刷新token:【{}】",e);
        }
        return refreshedToken;
    }

    /**
     * 获取token的剩余过期时间
     */
    public static long getRemainingTime(String token){
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration().getTime()-nowMillis;
        } catch (Exception e) {
            log.error("获取token的剩余过期时间:【{}】",e);
        }
        return result;
    }
}
