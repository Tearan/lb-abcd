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
@Table(name = "user_role",schema = "public")
@TableName("user_role")
public class UserRole {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "用户ID")
    @Column(name = "u_id")
    private String uId;

    @ApiModelProperty(value = "权限ID")
    @Column(name = "r_id")
    private String rId;
}
