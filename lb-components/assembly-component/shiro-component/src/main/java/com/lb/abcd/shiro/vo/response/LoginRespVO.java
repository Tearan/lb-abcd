package com.lb.abcd.shiro.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName LoginRespVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:30
 * @Version 1.0
 */

@Data
public class LoginRespVO {

    @ApiModelProperty(value = "正常的业务token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
}
