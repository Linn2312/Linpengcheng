# undergraduation_finalProject
final graduation design--基于SpringCloud的微信商城系统的设计与实现

本项目是基于Springcloud微服务解决方案的移动端H5工程项目。将工程拆分成服务层和表现层,每个服务层都拥有独立的数据库、独立的redis。<br><br>
如下是采用的技术及组件：<br>
  &nbsp;--服务注册中心eureka（集群 8100 8200）<br>
  &nbsp;--客户端调用工具feign<br>
  &nbsp;--断路器hystrix<br>
  &nbsp;--网关zuul（集群 81 82 一主一备）<br>
  &nbsp;--缓存redis（6380 6381 6382）<br>
  &nbsp;--消息中间件activemq<br>
  &nbsp;--服务器负载均衡nginx<br>
  &nbsp;--客户端负载均衡ribbon<br>
  &nbsp;--搜索引擎elastic search<br>
  &nbsp;--数据搜集处理工具logstash<br>
  &nbsp;--持久层框架mybatis<br>
  &nbsp;--项目管理工具maven<br>
  &nbsp;--页面模板引擎freemarker<br>
  &nbsp;--数据库mysql<br>
<br>
整体架构：<br>
|--wechat-shop-parent (总的父工程,统一定义pom依赖和各种版本信息)<br>
     &emsp;|--wechat-shop-common (工具类)<br>
     &emsp;|--wechat-shop-eureka-server-8100<br>
     &emsp;|--wechat-shop-eureka-server-8200<br>
     &emsp;|--wechat-shop-api (接口父工程)<br>
        &emsp;&emsp;|--wechat-shop-member-api (会员服务接口)<br>
        &emsp;&emsp;|--wechat-shop-commodity-api (商品服务接口)<br>
        &emsp;&emsp;|--wechat-shop-cart-api (购物车服务接口)<br>
        &emsp;&emsp;|--wechat-shop-order-api (订单服务接口)<br>
        &emsp;&emsp;|--wechat-shop-pay-api (支付服务接口)<br>
     &emsp;|--wechat-shop-member (服务层：会员服务的实现       端口号8000  redis 6380)<br>
     &emsp;|--wechat-shop-message (服务层：mq消息的获取与转发  端口号8001)<br>
     &emsp;|--wechat-shop-commodity (服务层：商品服务的实现     端口号8002)<br>
     &emsp;|--wechat-shop-cart(服务层：购物车服务的实现          端口号8003  redis 6381)<br>
     &emsp;|--wechat-shop-order(服务层：订单服务的实现          端口号8004)<br>
     &emsp;|--wechat-shop-pay(服务层：支付服务的实现            端口号8005  redis 6382)<br>
     &emsp;|--wechat-shop-web(表现层父工程)<br>
 	    &emsp;&emsp;|--wechat-shop-mobile-web(表现层：用户登录子系统      端口号8020)<br>
 	    &emsp;&emsp;|--wechat-shop-index-web(表现层：商城门户子系统     端口号8010)<br>
 	    &emsp;&emsp;|--wechat-shop-cart-web(表现层：购物车子系统      端口号8030)<br>
 	    &emsp;&emsp;|--wechat-shop-order-web(表现层：订单子系统      端口号8040)<br>
 	    &emsp;&emsp;|--wechat-shop-pay-web(表现层：支付子系统      端口号8050)<br>
 	    &emsp;&emsp;|--wechat-shop-usercenter-web(表现层：用户中心子系统   端口号8060)<br>
     &emsp;|--wechat-shop-zuul-81(网关    端口号81)<br>
     &emsp;|--wechat-shop-zuul-82(网关    端口号82)<br>
<br>
