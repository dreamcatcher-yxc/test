package com.example.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yangxiuchu
 * @DATE 2017/12/8 19:13
 */
@Controller
@RequestMapping("/test")
public class ApiController {

    @ApiOperation(value="一个测试API",notes = "第一个测试api")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello()
    {
        return "hello";
    }
}
