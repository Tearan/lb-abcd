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
import javax.persistence.Transient;
import java.util.Date;

/**
 * @ClassName Dept
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:29
 * @Version 1.0
 */

@Data
@Entity
/** 会进行是否为空的校验， 全部参数的构造函数的自动生成*/
@AllArgsConstructor
/** 无参构造函数*/
@NoArgsConstructor
@ApiModel(value = "Dept对象")
@Table(name = "sys_dept",schema = "public")
@TableName("sys_dept")
public class Dept {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "部门名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父级id")
    @TableField("pid")
    private String pid;

    @ApiModelProperty(value = "状态(0:正常；1:弃用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "部门经理user_id")
    @TableField("dept_manager_id")
    private String deptManagerId;

    @ApiModelProperty(value = "部门经理名称")
    @TableField("manager_name")
    private String managerName;

    @ApiModelProperty(value = "部门经理联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除(0未删除；1已删除)")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "父级部门名称")
    @Transient
    @TableField(exist = false)
    private String pidName;
}
