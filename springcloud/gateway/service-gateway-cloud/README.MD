> 在之前的文章介绍了Spring Cloud Gateway的Predict（断言）、Filter（过滤器），
大家对Spring Cloud Gateway有初步的认识，其中在对服务路由转发的这一块，在之前的
文章是采用硬编码的方式进行路由转发。这篇文章以案例的形式来讲解Spring Cloud Gateway
如何配合服务注册中心进行路由转发。

## 一、工程介绍
本案例中使用spring boot的版本为2.0.3.RELEASE,spring cloud版本为Finchley.RELEASE。
在中涉及到了三个工程， 分别为注册中心eureka-server、服务提供者service-hi、 
服务网关service-gateway，如下：

工程名 |	端口 |	作用
 --- | --- | ---
eureka-server |	8761 |	注册中心eureka server
service-hi |	8762 |	服务提供者 eurka client
gateway/service-gateway-cloud |	8081 |	路由网关 eureka client

这三个工程中，其中service-hi、service-gateway-cloud向注册中心 eureka-server注册。
用户的请求首先经过service-gateway，根据路径由gateway的predict 去断言进到哪一个
 router， router经过各种过滤器处理后，最后路由到具体的业务服务，比如 service-hi。
 如图：

![1](./imgs/1.png)

## 二、gateway工程详细介绍
在gateway工程中引入项目所需的依赖，包括eureka-client的起步依赖和gateway的起步依赖，
代码如下：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

在工程的配置文件application.yml中 ，指定程序的启动端口为8081，注册地址、gateway
的配置等信息，配置信息如下：

```yaml
server:
  port: 8081

spring:
  application:
    name: sc-gateway-service
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lowerCaseServiceId: true
          
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

其中，spring.cloud.gateway.discovery.locator.enabled为true，表明gateway
开启服务注册和发现的功能，并且spring cloud gateway自动根据服务发现为每一个服务
创建了一个router，这个router将以服务名开头的请求路径转发到对应的服务。
spring.cloud.gateway.discovery.locator.lowerCaseServiceId是将请求路径上的
服务名配置为小写（因为服务注册的时候，向注册中心注册时将服务名转成大写的了），比如
以/service-hi/*的请求路径被路由转发到服务名为service-hi的服务上。

在浏览器上请求输入localhost:8081/service-hi/hi?name=1323，网页获取以下的响应：

```
hi 1323 ,i am from port:8762
```

在上面的例子中，向gateway-service发送的请求时，url必须带上服务名service-hi这个
前缀，才能转发到service-hi上，转发之前会将service-hi去掉。 那么我能不能自定义
请求路径呢，毕竟根据服务名有时过于太长，或者历史的原因不能根据服务名去路由，需要由
自定义路径并转发到具体的服务上。答案是肯定的是可以的，只需要修改工程的配置文件
application.yml，具体配置如下：

```yaml
spring:
  application:
    name: sc-gateway-server
  cloud:
    gateway:
      discovery:
        locator:
          enabled: false
          lowerCaseServiceId: true
      routes:
      - id: service-hi
        uri: lb://SERVICE-HI
        predicates:
          - Path=/demo/**
        filters:
          - StripPrefix=1
```

在上面的配置中，配置了一个Path 的predict,将以/demo/**开头的请求都会转发到uri为
lb://SERVICE-HI的地址上，lb://SERVICE-HI即service-hi服务的负载均衡地址，并用
StripPrefix的filter 在转发之前将/demo去掉。同时将
spring.cloud.gateway.discovery.locator.enabled改为false，如果不改的话，之前
的localhost:8081/service-hi/hi?name=1323这样的请求地址也能正常访问，因为这时为
每个服务创建了2个router。

在浏览器上请求localhost:8081/demo/hi?name=1323，浏览器返回以下的响应：

```
hi 1323 ,i am from port:8762
```

返回的结果跟我们预想的一样。
