package org.yxc.example.demo01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by yangxiuchu on 2017/12/20.
 */
@WebService(targetNamespace = "webservice-example") // 说明这是一个 webservice
public class HelloWorld {

    // 其他类型的方法在默认情况下都是不会发布的。
    public @WebResult(name = "helloMessage") String sayHi(@WebParam(name = "name") String name) {
        return "hi, " + name;
    }

    @WebMethod(exclude = true) // 不发布
    public void excludeMethod() {
    }
}
