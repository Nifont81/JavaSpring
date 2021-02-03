package ru.geekbrains.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ChatServerRunner {
    public static void main(String[] args) {
        // Создается полный набор классов для работы приложения с нужными связями:

        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ChatServer chatServer = context.getBean("chatServer", ChatServer.class);
        chatServer.start(7777);
    }
}
