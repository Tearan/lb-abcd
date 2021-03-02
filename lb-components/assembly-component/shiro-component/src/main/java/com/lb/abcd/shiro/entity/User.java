package com.lb.abcd.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lb.abcd.system.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:16
 * @Version 1.0
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User对象")
@Table(name = "sys_user",schema = "public")
@TableName("sys_user")
public class User extends BaseEntity {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "账户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "加密盐值")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "用户密码密文")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "部门id")
    @TableField("dept_id")
    private String deptId;

    @ApiModelProperty(value = "真实名称")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "邮箱(唯一)")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "账户状态(0.正常;1.锁定)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "性别(0.男;1.女)")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "是否删除(0.未删除;1.已删除)")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "创建人")
    @TableField("create_id")
    private String createId;

    @ApiModelProperty(value = "更新人")
    @TableField("update_id")
    private String updateId;

    @ApiModelProperty(value = "创建来源(0.web;1.android;2.ios )")
    @TableField("create_where")
    private Integer createWhere;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "部门名称")
    @Transient
    private String deptName;
}
