package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        UserRepository userRepository = new UserRepository();
        sc.setAttribute("userRepository", userRepository);

        userRepository.insert(new User("Пользователь1"));
        userRepository.insert(new User("Пользователь2"));
        userRepository.insert(new User("Пользователь3"));
        userRepository.insert(new User("Пользователь4"));
        userRepository.insert(new User("Пользователь5"));

        // здесь же соединение с БД и помещение его в репозиторий...

    }
}
