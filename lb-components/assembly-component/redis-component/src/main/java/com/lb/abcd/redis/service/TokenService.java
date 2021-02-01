package com.lb.abcd.redis.service;

/**
 * @ClassName TokenService
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 13:45
 * @Version 1.0
 */
public interface TokenService {

    /**
     * 创建token
     * @return
     */
    String createToken();

    /**
     * 检验token
     * @return
     */
    boolean checkToken();
}
