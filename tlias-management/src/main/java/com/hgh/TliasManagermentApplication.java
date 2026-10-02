package com.hgh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

//@ServletComponentScan//开启Servlet组件扫描,扫描filter,listener,init-param等
@SpringBootApplication
public class TliasManagermentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TliasManagermentApplication.class, args);
    }

}
