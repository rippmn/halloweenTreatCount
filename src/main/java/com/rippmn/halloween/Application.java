package com.rippmn.halloween;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public DataSource getDataSource(){
    	DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://mysql01:3306/halloween", "halloween", "passw0rd");
    	return dataSource;
    }
}
