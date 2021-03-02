package com.lb.abcd.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName 实体的公用类，一些公用的属性
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 10:39
 * @Version 1.0
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页")
    @TableField(exist = false)
    private Integer current = 1;

    @ApiModelProperty(value = "页大小")
    @TableField(exist = false)
    private Integer size = 10;

    @ApiModelProperty(value = "sql分页查询时用到，起始")
    @TableField(exist = false)
    private Integer startRow;

    public Integer getStartRow(){

        if(current != null && size != null){
            return current * size - size;
        }else{
            return 0;
        }
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }
}
