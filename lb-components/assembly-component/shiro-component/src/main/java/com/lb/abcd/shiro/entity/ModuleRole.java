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
 * @ClassName ModuleRole
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/3 10:10
 * @Version 1.0
 */

@Data
@Entity
/** 会进行是否为空的校验， 全部参数的构造函数的自动生成*/
@AllArgsConstructor
/** 无参构造函数*/
@NoArgsConstructor
@ApiModel(value = "角色权限")
@Table(name = "module_role",schema = "public")
@TableName("module_role")
public class ModuleRole {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "角色ID")
    @Column(name = "r_id")
    private String rId;

    @ApiModelProperty(value = "权限ID")
    @Column(name = "u_id")
    private String mId;
}
