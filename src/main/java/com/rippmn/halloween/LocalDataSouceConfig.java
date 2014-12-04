package com.rippmn.halloween;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("default")
public class LocalDataSouceConfig {
    @Bean
    public DataSource getDataSource(){
    	DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://mysql01:3306/halloween", "halloween", "passw0rd");
    	return dataSource;
    }

}
