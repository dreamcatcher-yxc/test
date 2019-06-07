> 这篇文章讲述了如何简单地使用Spring Cloud Gateway，来源于Spring Cloud官方案例，
地址  [https://spring.io/guides/gs/gateway](https://spring.io/guides/gs/gateway) 。

## 一、简介
Spring Cloud Gateway是Spring Cloud官方推出的第二代网关框架，取代Zuul网关。网关作为流量的，
在微服务系统中有着非常作用，网关常见的功能有路由转发、权限校验、限流控制等作用。本文首先用官方
的案例带领大家来体验下Spring Cloud的一些简单的功能，在后续文章我会使用详细的案例和源码解析来
详细讲解Spring Cloud Gateway。

## 二、创建工程
本案例的的源码下载于 [官方案例](https://spring.io/guides/gs/gateway)，也可以在我的Github上下载。
可以参看本工程源码。

## 三、创建一个简单的路由
在 `spring cloud gateway` 中使用 `RouteLocator` 的 `Bean` 进行路由转发，将请求进行处理，最后转发到
目标的下游服务。

在本案例中，会将请求转发到 `http://httpbin.org:80` 这个地址上。代码如下：

```java
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
       return builder.routes()
        .route(p -> p
            .path("/get")
            .filters(f -> f.addRequestHeader("Hello", "World"))
            .uri("http://httpbin.org:80"))
        .build();
    }
    
}
``` 
在上面的 `myRoutes` 方法中，使用了一个 `RouteLocatorBuilder` 的 `bean` 去创建路由，除了创建路由
`RouteLocatorBuilder` 可以让你添加各种 `predicates` 和 `filters`，`predicates` 断言的意思，顾名
思义就是根据具体的请求的规则，由具体的 `route` 去处理，`filters` 是各种过滤器，用来对请求做各种
判断和修改。

上面创建的 `route` 可以让请求 “/get” 请求都转发到 “http://httpbin.org/get”。在route配置上，我们
添加了一个 `filter`，该 `ilter` 会将请求添加一个 `header`, `key` 为 `hello`，`value` 为 `world`。

启动 `springboot` 项目，在浏览器上 `http://localhost:8080/get`，浏览器显示如下:

```json
{
  "args": {}, 
  "headers": {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3", 
    "Accept-Encoding": "gzip, deflate, br", 
    "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8", 
    "Cookie": "Webstorm-a327319a=d098fe0b-9b44-472a-8575-e503a8952182", 
    "Forwarded": "proto=http;host=\"localhost:8080\";for=\"0:0:0:0:0:0:0:1:56575\"", 
    "Hello": "World", 
    "Host": "httpbin.org", 
    "Upgrade-Insecure-Requests": "1", 
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36", 
    "X-Forwarded-Host": "localhost:8080"
  }, 
  "origin": "0:0:0:0:0:0:0:1, 122.245.216.131, ::1", 
  "url": "https://localhost:8080/get"
}
```

可见当我们向 `gateway` 工程请求“/get”, `gateway` 会将工程的请求转发到“http://httpbin.org/get”，并且在转发之前，
加上一个 `filter`，该 `filter` 会将请求添加一个 `header`, `key` 为 `hello`，`value` 为 `world`。

注意 `HTTPBin` 展示了请求的 `header hello` 和值 `world`。

## 四、使用 Hystrix
在 `spring cloud gateway` 中可以使用 `Hystrix`。`Hystrix` 是 `spring cloud` 中一个服务熔断降级的组件，在微服务系统
有着十分重要的作用。 `Hystrix` 是 `spring cloud gateway` 中是以 `filter` 的形式使用的，代码如下：

```java
@Bean
public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    String httpUri = "http://httpbin.org:80";
    return builder.routes()
        .route(p -> p
            .path("/get")
            .filters(f -> f.addRequestHeader("Hello", "World"))
            .uri(httpUri))
        .route(p -> p
            .host("*.hystrix.com")
            .filters(f -> f
                .hystrix(config -> config
                    .setName("mycmd")
                    .setFallbackUri("forward:/fallback")))
            .uri(httpUri))
        .build();
}
``` 
在上面的代码中，我们使用了另外一个 `router`，该 `router` 使用 `host` 去断言请求是否进入该路由，当请求的 `host` 有 “*.hystrix.com”，
都会进入该 `router`，该 `router` 中有一个 `hystrix` 的 `filter`, 该 `filter` 可以配置名称、和指向性 `fallback` 的逻辑的地址，比如
本案例中重定向到了 “/fallback”。

现在写的一个 “/fallback” 的l逻辑：

```java
 @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
```

Mono是一个Reactive stream，对外输出一个“fallback”字符串。

使用curl执行以下命令：

```
 curl --dump-header - --header 'Host: www.hystrix.com' http://localhost:8080/delay/3
```

返回的响应为：

```
fallback
```

可见，带hostwww.hystrix.com的请求执行了hystrix的fallback的逻辑。

## 五、总结
本文通过官方的一个简单的案例，来讲解了 `spring cloud gateway` 的简单用法，在 `spring cloud gateway` 中有2个重要
的概念 `predicates` 和 `filters`。

