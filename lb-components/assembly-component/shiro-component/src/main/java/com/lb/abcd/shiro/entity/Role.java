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
import java.util.List;

/**
 * @ClassName Role
 * @Description 角色实体类
 * @Author Terran
 * @Date 2021/1/31 15:07
 * @Version 1.0
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="角色")
@Table(name = "role",schema = "public")
@TableName("role")
public class Role {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private Integer id;

    @ApiModelProperty(value = "角色名")
    @Column(name = "rname")
    private String rname;

    @ApiModelProperty(value = "用户列表")
    @Transient
    private List<User> users;

    @ApiModelProperty(value = "权限列表")
    @Transient
    private List<Module> modules;
}
