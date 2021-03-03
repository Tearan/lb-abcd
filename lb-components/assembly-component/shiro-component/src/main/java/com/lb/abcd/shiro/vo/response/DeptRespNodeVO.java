package com.lb.abcd.shiro.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName DeptRespNodeVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 9:58
 * @Version 1.0
 */

@Data
public class DeptRespNodeVO {

    @ApiModelProperty(value = "部门id")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty("是否展开 默认true")
    private boolean spread=true;

    @ApiModelProperty(value = "子集叶子节点")
    private List<?> children;
}
