package ru.geekbrains.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.geekbrains.server") // Добавляет аннотацию по компонентам

public class AppConfig {

    @Bean
    public DataSource dataSource() {
        // Эта аннотация полностью аналогична в файле spring-config.xml
        DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:mysql://localhost:3306/chat","root","powers");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;

        // Здесь можно всё что угодно дописать, в т. ч. создать Connection и др.
    }
}
