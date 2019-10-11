package com.boot.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PersistenceConfiguration {
	
	
	@Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.flyway")
    @FlywayDataSource
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.flyway")
    @FlywayDataSource
    public DataSource secondaryDataSource() {
        return secondDataSourceProperties().initializeDataSourceBuilder().build();
    }

}
