package com.example.demo.rest;

import com.example.demo.entity.User;
import com.example.demo.entity.Views;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * 直接直接实例化 RestTemplate 的方式测试
 */
public class Test01 {

    private TestRestTemplate template = new TestRestTemplate();

    /**
     * GET 方式访问 Rest 风格的 url 连接访问
     * @throws Exception
     */
    @Test
    public void testGet01() throws Exception {
//        String body = this.template.getForEntity(
//                "http://localhost:9096/rest/hello/aaa", String.class).getBody();
        String body = this.template.getForEntity("http://localhost:9096/rest/hello/{name}", String.class, "yangxiuchu").getBody();
        System.out.println(body);
    }

    /**
     * GET 方式访问 Rest 风格的 url 连接访问
     * @throws Exception
     */
    @Test
    public void testGet02() throws Exception {
        Map<String, String> vars = Collections.singletonMap("name", "yangxiuchu");
        String body = this.template.getForObject("http://localhost:9096/rest/hello/{name}", String.class, vars);
        System.out.println(body);
    }

    /**
     * 模拟提交 POST 请求。
     * @throws Exception
     */
    @Test
    public void testPostForLocation() throws Exception {
        String uri = "http://localhost:9096/rest/users/{id}";
        User user = new User();
        user.setUsername("yangxiuchu");
        user.setPassword("xxx");
        user.setGender("male");
        user.setEmail("1905719625@qq.com");
        user.setPhone("18487304527");
        URI location = template.postForLocation(uri, user, "2");
        System.out.println(location);
    }

    /**
     * 直接使用比较底层的 API 发送 HTTP 请求。
     * SPRING MVC 对 REST 风格的 API 支持 application/json;charset=utf-8 格式的 content-type
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
//        public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
//                ResponseExtractor<T> responseExtractor, String... uriVariables)
        template.execute(
                "http://localhost:9096/rest/users/{id}",
                HttpMethod.POST,
                request -> {
                    // 设置请求体
                    String userInfo = "{\"username\": \"yangxiuchu\"}";
                    request.getBody().write(userInfo.getBytes());
                    // 设置请求头的 Content-type
                    request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    },
                response -> {
                    return null;
                    },
                "2");
    }

    /**
     * 自定义请求头和响应头
     * @throws Exception
     */
    @Test
    public void testExchange() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("auth-key", "xxx");
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

        HttpEntity<String> response = template.exchange(
                "http://localhost:9096/rest/headers",
                HttpMethod.GET, requestEntity, String.class);

        String authResult = response.getHeaders().getFirst("auth-result");
        System.out.println(authResult);
    }

    /**
     * RestTemplate 支持使用 JsonView 的方式过滤不想提交的属性
     * @throws Exception
     */
    @Test
    public void testJsonViews() throws Exception {
        User user = new User();
        user.setUsername("yangxiuchu");
        user.setPassword("xxx");
        user.setGender("male");
        user.setEmail("1905719625@qq.com");
        user.setPhone("18487304527");
        MappingJacksonValue value = new MappingJacksonValue(user);
        value.setSerializationView(Views.Detail.class);
        HttpEntity<MappingJacksonValue> entity = new HttpEntity<>(value);
        String s = template.postForObject("http://localhost:9096/rest/add", entity, String.class);
        System.out.println(s);
    }
}
