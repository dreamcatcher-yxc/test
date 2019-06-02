## Feign 介绍
Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。
简而言之：
- Feign 采用的是基于接口的注解
- Feign 整合了ribbon

## Feign 中使用断路器
`../service-ribbo/README.MD` 中已经对断路器的概念进行了简单的说明, `feign` 不经集成了 `ribbo`, 而且也
自带了断路器, 默认并咩有开启:
```
feign.hystrix.enabled=true
``` 
如上配置就开启了断路功能, 基于service-feign工程进行改造，只需要在FeignClient的SchedualServiceHi接口的注解中加上fallback的指定类就行了。