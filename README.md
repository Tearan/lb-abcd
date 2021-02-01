# LB-ABCD-HUB 物联网平台

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

PostgreSQL简介
PostGreSQL是一个功能强大的开源对象关系数据库管理系统(ORDBMS)，号称世界上最先进的开源关系型数据库
经过长达15年以上的积极开发和不断改进，PostGreSQL已在可靠性、稳定性、数据一致性等获得了很大的提升。
对比时下最流行的 MySQL 来说，PostGreSQL 拥有更灵活，更高度兼容标准的一些特性。
此外，PostGreSQL基于MIT开源协议，其开放性极高，这也是其成为各个云计算大T 主要的RDS数据库的根本原因。



