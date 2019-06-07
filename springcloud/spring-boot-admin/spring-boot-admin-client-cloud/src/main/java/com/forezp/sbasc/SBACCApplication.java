package com.forezp.sbasc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class SBACCApplication {

    public static void main(String[] args) {
        SpringApplication.run(SBACCApplication.class, args);
    }
}
