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
import java.util.Date;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:27
 * @Version 1.0
 */

@Data
@Entity
/** 会进行是否为空的校验， 全部参数的构造函数的自动生成*/
@AllArgsConstructor
/** 无参构造函数*/
@NoArgsConstructor
@ApiModel(value = "Permission对象")
@Table(name = "sys_permission")
@TableName("sys_permission")
public class Permission extends BaseEntity {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "菜单权限编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "菜单权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "授权(如：sys:user:add)")
    @TableField("perms")
    private String perms;

    @ApiModelProperty(value = "访问地址URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "资源请求类型")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "父级菜单权限id")
    @TableField("pid")
    private String pid;

    @ApiModelProperty(value = "父级菜单权限名称")
    private transient String pidName;

    @ApiModelProperty(value = "排序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单权限类型(1:目录;2:菜单;3:按钮)")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "状态1:正常 0：禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除(1未删除；0已删除)")
    @TableField("deleted")
    private Integer deleted;
}
