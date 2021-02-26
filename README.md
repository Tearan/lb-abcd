# LB-ABCD 物联网平台

## ABCD代表含义

A:应用使能【AEP】
B:业务分析【BAP】
C:连接管理【CMP】
D:设备管理【DMP】

## 模块

```bash

-------------------------- > lb-abcd-hub < --------------------------

--------|--------|--------| lb-components   ### > 公共组件模块
        
        --------| network-component     ## > 网络层-管控
            --------| http-component    # > http
            --------| https-component   # > https
            --------| mqtt-component    # > mqtt
            --------| tcp-component     # > tcp
            --------| udp-component     # > udp
            ........            

        --------| platform-component    ## > 平台层-管控
            --------| ctwing-component  # > 电信-CTWING
            --------| ezviz-component   # > 海康-萤石-EZVIZ
            --------| isc-component     # > 海康-ISC
            --------| onenet-component  # > 移动-IOT
            --------| telecom-component # > 电信-IOT
            ........            

--------|--------|--------| lb-manager      ### > 业务管理模块
        
        --------| 

--------|-------- lb-separate      #服务启动模块
```

## 1.PostgreSQL简介
PostGreSQL是一个功能强大的开源对象关系数据库管理系统(ORDBMS)，
号称世界上最先进的开源关系型数据库经过长达15年以上的积极开发和不断改进，
PostGreSQL已在可靠性、稳定性、数据一致性等获得了很大的提升。
对比时下最流行的 MySQL 来说，PostGreSQL 拥有更灵活，
更高度兼容标准的一些特性。此外，PostGreSQL基于MIT开源协议，其开放性极高，
这也是其成为各个云计算大T 主要的RDS数据库的根本原因。

## 2.常用注解类型
注意，不要错用了异常类型，比如在int上不可用@size

@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。

常用注解如下:

@AssertFalse 校验false
@AssertTrue 校验true
@DecimalMax(value=,inclusive=) 小于等于value，
inclusive=true,是小于等于
@DecimalMin(value=,inclusive=) 与上类似
@Max(value=) 小于等于value
@Min(value=) 大于等于value
@NotNull  检查Null
@Past  检查日期
@Pattern(regex=,flag=)  正则
@Size(min=, max=)  字符串，集合，map限制大小
@Validate 对po实体类进行校验

## 3.mybatis-plus

wrapper参数:
当查询条件复杂的时候,我们可以使用MP的条件构造器,
请参考下面的QueryWrapper条件参数说明

查询方式	                   方法说明
setSqlSelect	       `设置 SELECT 查询字段`
where	               `WHERE 语句，拼接 + WHERE 条件`
and	                   `AND 语句，拼接 + AND 字段=值`
or	                   `OR 语句，拼接 + OR 字段=值`
eq	                   `等于=`
allEq	               `基于 map 内容等于=`
ne	                   `不等于<>`
gt	                   `大于>`
ge	                   `大于等于>=`
lt	                   `小于<`
le	                   `小于等于<=`
like	               `模糊查询 LIKE`
notLike	               `模糊查询 NOT LIKE`
in	                   `IN 查询`
notIn	               `NOT IN 查询`
isNull	               `NULL 值查询`
isNotNull	           `IS NOT NULL`
groupBy	               `分组 GROUP BY`
having	               `HAVING 关键词`
orderBy	               `排序 ORDER BY`
orderByAsc	           `ASC 排序 ORDER BY`
orderByDesc	           `DESC 排序 ORDER BY`
exists	               `EXISTS 条件语句`
notExists	           `NOT EXISTS 条件语句`
between	               `BETWEEN 条件语句`
notBetween	           `NOT BETWEEN 条件语句`
addFilter	           `自由拼接 SQL`
last	               `拼接在最后，例如：last(“LIMIT 1”)`

