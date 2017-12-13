package org.yxc.testapi.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangxiuchu
 * @DATE 2017/12/10 13:14
 */
@RestController
@RequestMapping("/test")
public class ApiController {

    @ApiOperation(value="一个测试API",notes = "第一个测试api")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello() {
        return "hello world";
    }
}
