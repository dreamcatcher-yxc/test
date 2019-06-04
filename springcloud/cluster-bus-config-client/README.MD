> Spring Cloud Bus 将分布式的节点用轻量的消息代理连接起来。它可以用于
广播配置文件的更改或者服务之间的通讯，也可以用于监控。本文要讲述的是用
Spring Cloud Bus实现通知微服务架构的配置文件的更改。

## 一、准备工作
本文还是基于上一篇文章来实现。按照官方文档，我们只需要在配置文件中配置 
spring-cloud-starter-bus-amqp ；这就是说我们需要装 [rabbitMq](http://www.rabbitmq.com/)，
点击rabbitmq下载。至于怎么使用 rabbitmq，搜索引擎下。
 
## 二、构架工程
本工程参照 `cluster-config-client` 改造, 具体参看项目源码.

## 三、验证
依次启动`eureka-server`、`cluster-bus-config-server`、
`cluster-bus-config-client(启动两个，端口为：8881、8882)`。

访问 http://localhost:8881/hi 或者 http://localhost:8882/hi 浏览器显示：

> foo version 21

这时我们去代码仓库将foo的值改为 `foo version 22`，即改变配置文件foo的值。
如果是传统的做法，需要重启服务，才能达到配置文件的更新。此时，我们只需要
发送post请求：`http://localhost:8881/actuator/bus-refresh`，你会发现
`cluster-bus-config-client` 会重新读取配置文件。

这时我们再访问 `http://localhost:8881/hi` 或者 `http://localhost:8882/hi` 浏览器显示：

> foo version 22

另外，/actuator/bus-refresh接口可以指定服务，即使用”destination”参数，
比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为
customers的所有服务。

## 四、分析
此时的架构图：

![2](./2.png)

当git文件更改的时候，通过pc端用post 向端口为8882的config-client发送请求/bus/refresh／；
此时8882端口会发送一个消息，由消息总线向其他服务传递，从而使整个微服务集群都达到更新配置文件。
 
 
 
 
 
