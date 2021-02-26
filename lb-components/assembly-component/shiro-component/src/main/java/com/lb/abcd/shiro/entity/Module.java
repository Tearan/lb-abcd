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
 * @ClassName Modules
 * @Description 权限实体类
 * @Author Terran
 * @Date 2021/1/31 15:11
 * @Version 1.0
 */
@Data
@Entity
/** 会进行是否为空的校验， 全部参数的构造函数的自动生成*/
@AllArgsConstructor
/** 无参构造函数*/
@NoArgsConstructor
@ApiModel(value = "权限")
@Table(name = "module",schema = "public")
@TableName("module")
public class Module {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "权限名")
    @Column(name = "mname")
    private String mname;

    @ApiModelProperty(value = "角色列表")
    @Transient
    private List<Role> roles;
}
