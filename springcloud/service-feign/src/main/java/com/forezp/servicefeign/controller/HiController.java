package com.forezp.servicefeign.controller;

import com.forezp.servicefeign.service.ScheduleServiceHi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    private final ScheduleServiceHi scheduleServiceHi;

    public HiController(ScheduleServiceHi scheduleServiceHi) {
        this.scheduleServiceHi = scheduleServiceHi;
    }

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
        return scheduleServiceHi.sayHiFromClientOne(name);
    }
}
