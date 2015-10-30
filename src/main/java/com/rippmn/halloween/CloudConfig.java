package com.rippmn.halloween;

import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

	@Bean
    public DataSource getDataCloudSource(){    	
		return connectionFactory().dataSource("halloween-mysql-UPSDB");
    }
}