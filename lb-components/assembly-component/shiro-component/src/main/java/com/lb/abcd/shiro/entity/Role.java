package com.lb.abcd.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import com.lb.abcd.system.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
@ApiModel(value = "角色")
@Table(name = "sys_role")
@TableName("role")
public class Role extends BaseEntity {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @ApiModelProperty(value = "状态(1:正常;0:弃用)")
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

    @ApiModelProperty(value = "权限树")
    @Transient
    @TableField(exist = false)
    private transient List<PermissionRespNodeVO> permissionRespNode;
}
