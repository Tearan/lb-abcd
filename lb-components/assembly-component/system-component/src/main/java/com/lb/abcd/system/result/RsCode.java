package com.lb.abcd.system.result;

import lombok.Getter;

/**
 * @ClassName ReCode
 * @Description 响应码枚举
 * @Author Terran
 * @Date 2021/2/2 15:46
 * @Version 1.0
 */

@Getter
public enum RsCode {

    /**
     * code = 1：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     * code = 2010001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     * code = 2010002：(凭证过期) 客户端请求刷新凭证接口
     * code = 2010003：没有权限禁止访问
     * code = 200xxxx：系统主动抛出的业务异常
     * code = 6000001：系统异常
     */

    /** 成功状态码*/
    SUCCESS(1, "操作成功"),
    
    /** 系统异常: 6000001 */
    SYSTEM_ERROR(6000001,"系统异常请稍后再试"),
    
    /** （授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录: 2010001 */
    ACCOUNT_LOCK(2010001,"该账号被锁定,请联系系统管理员"),
    TOKEN_ERROR(2010001,"用户未登录，请重新登录"),
    TOKEN_NOT_NULL(2010001,"token 不能为空"),
    SHIRO_AUTHENTICATION_ERROR(2010001,"用户认证异常"),
    ACCOUNT_HAS_DELETED_ERROR(2010001,"该账号已被删除，请联系系统管理员"),

    /** （授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录: 2010002 */
    TOKEN_PAST_DUE(2010002,"token失效,请刷新token"),

    ACCOUNT_LOCK_TIP(2010012,"该账号被锁定,请联系系统管理员"),
    OPERATION_MENU_PERMISSION_UPDATE(2010013,"操作的菜单权限存在子集关联不允许变更"),
    ROLE_PERMISSION_RELATION(2010014, "该菜单权限存在子集关联，不允许删除"),
    NOT_PERMISSION_DELETED_DEPT(2010015,"该组织机构下还关联着用户，不允许删除"),
    OLD_PASSWORD_ERROR(2010016,"旧密码不匹配"),

    /** 没有权限禁止访问: 2030001*/
    NOT_PERMISSION(2030001,"没有权限访问该资源"),

    /** 系统主动抛出的业务异常: 200xxxx*/
    DATA_ERROR( 2000001,"传入数据异常"),
    METHOD_IDENTITY_ERROR( 2000002,"数据校验异常"),
    ACCOUNT_ERROR(2000003,"该账号不存在"),
    ACCOUNT_HAS_ERROR(2000003,"该账号已存在"),
    ACCOUNT_PASSWORD_ERROR(2000004,"用户名密码不匹配"),
    OPERATION_ERROR(2000005,"操作失败"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(2000006,"操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(2000007,"操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(2000008,"操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(2000009,"菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(2000010,"菜单权限的标识符不能为空"),
    OPERATION_MENU_PERMISSION_URL_METHOD_NULL(2000011,"菜单权限的请求方式不能为空"),
    OPERATION_MENU_PERMISSION_URL_CODE_NULL(2000011,"菜单权限的按钮标识不能为空"),
    UPLOAD_FILE_ERROR(2000012,"上传失败"),
    FILE_TOO_LARGE( 2000013,"上传的文件超出范围"),
    CODE_NULL( 2000014,"请输入验证码"),
    CODE_EXPIRE( 20000014,"验证码已过期"),
    CODE_ERROR( 20000014,"验证码不正确"),
    PASSWORD_ERROR( 20000015,"两次输入密码不相同"),
    ;
    
    private Integer code;

    private String msg;

    RsCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
