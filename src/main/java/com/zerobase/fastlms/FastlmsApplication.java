package com.zerobase.fastlms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class FastlmsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FastlmsApplication.class, args);
    }
    
}
