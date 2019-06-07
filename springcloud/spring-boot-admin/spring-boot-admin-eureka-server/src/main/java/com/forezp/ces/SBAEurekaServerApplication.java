package com.forezp.ces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SBAEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( SBAEurekaServerApplication.class, args );
    }
}
