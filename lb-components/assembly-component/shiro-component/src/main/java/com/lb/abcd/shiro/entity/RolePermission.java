package com.lb.abcd.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @ClassName RolePermission
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:25
 * @Version 1.0
 */

@Data
@Entity
/** 会进行是否为空的校验， 全部参数的构造函数的自动生成*/
@AllArgsConstructor
/** 无参构造函数*/
@NoArgsConstructor
@ApiModel(value = "RolePermission对象")
@Table(name = "sys_role_permission",schema = "public")
@TableName("sys_role_permission")
public class RolePermission {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "菜单权限id")
    @TableField("permission_id")
    private String permissionId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;
}
