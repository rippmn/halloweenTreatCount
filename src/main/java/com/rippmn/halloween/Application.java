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
    	DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/ad_35086bd71dd4966", "bd6b54686d3726", "d018a378");
    	return dataSource;
    }
}
