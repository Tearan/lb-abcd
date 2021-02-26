package com.lb.abcd.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author Terran
 * @Date 2021/1/31 14:19
 * @Version 1.0
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户")
@Table(name = "user",schema = "public")
@TableName("user")
public class User {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty("用户主键ID")
    @Id
    private String id;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户账号不能为空")
    @Column(name = "username")
    private String username;

    /**
     * 加密使用的盐值
     */
    @ApiModelProperty(value = "盐值",hidden = true)
    @Column(name = "salt")
    private String salt;

    @ApiModelProperty("密码")
    @NotNull(message = "用户密码不能为空")
    @Column(name = "password")
    private String password;

    @ApiModelProperty("权限说明:[2.查看权限; 3.查看和编辑权限; 6.查看和授权权限; 7.查看、编辑、授权权限]")
    @Column(name = "authority")
    private Integer authority;

    @ApiModelProperty("角色列表")
    @Transient
    private List<Role> roles;
}
