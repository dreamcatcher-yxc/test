## 一、Spring Cloud gateway工作流程
在之前的文章的Spring Cloud Gateway初体验中，大家已经对Spring Cloud Gateway的功能有一个初步的认识，
网关作为一个系统的流量的入口，有着举足轻重的作用，通常的作用如下：

- 协议转换，路由转发
- 流量聚合，对流量进行监控，日志输出
- 作为整个系统的前端工程，对流量进行控制，有限流的作用
- 作为系统的前端边界，外部流量只能通过网关才能访问系统
- 可以在网关层做权限的判断
- 可以在网关层做缓存

`Spring Cloud Gateway` 作为 `Spring Cloud` 框架的第二代网关，在功能上要比 `Zuul` 更加的强大，
性能也更好。随着 `Spring Cloud` 的版本迭代，`Spring Cloud` 官方有打算弃用 `Zuul` 的意思。
`Spring Cloud Gateway` 在使用和功能上，`Spring Cloud Gateway` 替换掉Zuul的成本上是非常低的，
几乎可以无缝切换。Spring Cloud Gateway几乎包含了zuul的所有功能。

![1](./imgs/1.png)

如上图所示，客户端向 `Spring Cloud Gateway` 发出请求。 如果 `Gateway Handler Mapping` 确定请求
与路由匹配（这个时候就用到 `predicate` ），则将其发送到 `Gateway web handler` 处理。 
`Gateway web handler` 处理请求时会经过一系列的过滤器链。 过滤器链被虚线划分的原因是过滤器链可以在
发送代理请求之前或之后执行过滤逻辑。 先执行所有“pre”过滤器逻辑，然后进行代理请求。 在发出代理请求之后，
收到代理服务的响应之后执行“post” 过滤器逻辑。这跟 `zuul` 的处理过程很类似。在执行所有“pre”过滤器逻辑时，
往往进行了鉴权、限流、日志输出等功能，以及请求头的更改、协议的转换；转发之后收到响应之后，会执行所有
“post”过滤器的逻辑，在这里可以响应数据进行了修改，比如响应头、协议的转换等。

在上面的处理过程中，有一个重要的点就是讲请求和路由进行匹配，这时候就需要用到 `predicate`，它是决定了一个
请求走哪一个路由。

## 二、predicate简介
> `Predicate` 来自于 `java8` 的接口。`Predicate` 接受一个输入参数，返回一个布尔值结果。该接口包含多种
默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）。可以用于接口请求参数校验、判断新老数据是否
有变化需要进行更新操作。`add--与`、`or--或`、`negate--非`。

`Spring Cloud Gateway` 内置了许多 `Predict`, 这些 `Predict` 的源码在
`org.springframework.cloud.gateway.handler.predicate` 包中，如果读者有兴趣可以阅读一下。
现在列举各种 `Predicate` 如下图：

![2](./imgs/2.png)

在上图中，有很多类型的 `Predicate`, 比如说时间类型的
`Predicated（AfterRoutePredicateFactory BeforeRoutePredicateFactory BetweenRoutePredicateFactory）`，
当只有满足特定时间要求的请求会进入到此`predicate `中，并交由 `router` 处理；`cookie` 类型的
`CookieRoutePredicateFactory`，指定的 `cookie` 满足正则匹配，才会进入此 `router`; 以及 `host`、`method`、
`path`、`queryparam`、`remoteaddr` 类型的 `predicate`，每一种 `predicate` 都会对当前的客户端请求进行判断，
是否满足当前的要求，如果满足则交给当前请求处理。如果有很多个 `Predicate`，并且一个请求满足多个 `Predicate`，
则按照配置的顺序第一个生效。

## 三、predicate实战
现在以案例的形式来讲解predicate，本文中的案例基本来源于官方文档，
官方文档地址：[http://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html](http://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html)。

`pom.xml` 参看项目中相应文件内容

### 1、After Route Predicate Factory

`AfterRoutePredicateFactory`，可配置一个时间，当请求的时间在配置时间之后，才交给 `router` 去处理。否则则报错，
不通过路由。

在工程的 `application.yml` 配置如下：

```yaml
server:
  port: 8081
spring:
  profiles:
    active: after_route

---
spring:
  cloud:
    gateway:
      routes:
      - id: after_route
        uri: http://httpbin.org:80/get
        predicates:
        - After=2017-01-20T17:42:47.789-07:00[America/Denver]
  profiles: after_route
```

