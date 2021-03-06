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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName userRole
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/3 10:13
 * @Version 1.0
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="用戶角色")
@Table(name = "sys_user_role")
@TableName("sys_user_role")
public class UserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;
}
