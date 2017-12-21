package org.yxc.example.demo01;

import javax.xml.ws.Endpoint;

/**
 * Created by yangxiuchu on 2017/12/20.
 */
public class JavaApiWebService {

    public static void main(String[] args) {
        String address = "http://127.0.0.1:9999/helloworld";
        // 注册并且发布一个服务,arg0: 服务地址 , arg1:要发布的服务对象
        Endpoint endPoint = Endpoint.publish(address, new HelloWorld());
        // 可以停止服务,或者手动停止
        System.out.println("服务启动成功...");
        //endPoint.stop();
    }
}
