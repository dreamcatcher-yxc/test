## 概述
上一篇文章讲述了一个服务如何从配置中心读取文件，配置中心如何从远程git读取配置文件，
当服务实例很多时，都从配置中心读取文件，这时可以考虑将配置中心做成一个微服务，将其
集群化，从而达到高可用，架构图如下：

![1](./1.png)

## 一、准备工作 
使用 eureka-server 作为服务注册中心, 配置略:

## 二、改造 config-server
在其pom.xml文件加上EurekaClient的起步依赖spring-cloud-starter-netflix-eureka-client，
代码如下: 
```xml
<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
</dependencies>
```
配置文件application.yml，指定服务注册地址为http://localhost:8889/eureka/，其他配置同上一篇文章，
完整的配置如下： 

```properties
spring.application.name=config-server
server.port=8888

spring.cloud.config.server.git.uri=https://github.com/forezp/SpringcloudConfig/
spring.cloud.config.server.git.searchPaths=respo
spring.cloud.config.label=master
spring.cloud.config.server.git.username=
spring.cloud.config.server.git.password=
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
 ```
 
 启动类:
 
 ```java
  package com.forezp.cbcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@EnableEurekaClient
public class ClusterConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClusterConfigServerApplication.class, args);
	}
}
 ```

> 该工程配合 cluster-bus-config-client 演示使用
 
 
 
 
 
