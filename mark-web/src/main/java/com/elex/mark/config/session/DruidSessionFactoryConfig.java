package com.elex.mark.config.session;

import com.alibaba.druid.pool.DruidDataSource;
import com.elex.mark.config.session.SessionFactoryConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Druid数据库连接池~开启时需要打开注解注释~关闭其他连接池注解注释
 * Created by sun on 2017/7/3.
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.elex.mark.mapper")
public class DruidSessionFactoryConfig extends SessionFactoryConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

}
