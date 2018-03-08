package com.example.demo.controller;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.yotexs.settle.external.domain.Foo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {
    @Value("${app.mq.exchange.name}")
    private String exchangeName;

    @Value("${app.mq.rk}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping("basic")
    public String basic(Model model, HttpSession session) {
        session.getServletContext().setAttribute("username", "username in servlet context");
        session.setAttribute("username", "username in session");
        model.addAttribute("username", "杨秀初");
        model.addAttribute("msgKey", "home.welcome");

        User user = new User();
        user.setUsername("杨秀初");
        user.setGender("男");
        user.setEmail("1905719625@qq.com");
        user.setPassword("xxxxxx");
        user.setPhone("18487304527");

        Address address = new Address();
        address.setCountry("中国");
        address.setProvince("云南省");
        address.setCity("昭通市");
        user.setAddress(address);
        model.addAttribute("user", user);

        model.addAttribute("url", "/order");
        return "basic";
    }

    @RequestMapping("attr")
    public String attr(Model model) {
        User user = new User();
        user.setUsername("杨秀初");
        user.setGender("男");
        user.setEmail("1905719625@qq.com");
        user.setPassword("xxxxxx");
        user.setPhone("18487304527");

        Address address = new Address();
        address.setCountry("中国");
        address.setProvince("云南省");
        address.setCity("昭通市");
        user.setAddress(address);
        model.addAttribute("user", user);
        return "attr";
    }

    @RequestMapping("frag")
    public String fragment() {
        return "frag";
    }


    @RequestMapping("mq")
    @ResponseBody
    public String sendMQMsg(String fn, String ln) {
        Foo foo = new Foo();
        foo.setFirstName(fn);
        foo.setLastName(ln);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, foo);
        return "ok";
    }
}
