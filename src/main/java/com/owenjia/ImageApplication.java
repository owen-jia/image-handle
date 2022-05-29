package com.owenjia;

import com.owenjia.service.ImageHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ImageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImageApplication.class,args);
        context.getBean(ImageHandle.class).start();
    }
}
