package com.forezp.sbasc;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAdminServer
public class SBASApplication {

    public static void main(String[] args) {
        SpringApplication.run(SBASApplication.class, args);
    }
}
