package ru.geekbrains.HomeWork1;
/*

4. Создать ещё один сервлет, который будет обрабатывать префиксы вида /user/{id},
где {id} - идентификатор пользователя. При переходе по ссылке /user/1 должна
отображаться информация о пользователе с идентификатором 1.

 */

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class UserInfoServlet extends HttpServlet { // Полная поддержка протокола HTTP

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<p>Информация о пользователе</p>");

        long id;
        try {
            id = Long.parseLong(req.getPathInfo().substring(1));
            //resp.getWriter().println("<p>" + id + "</p>");
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            resp.getWriter().println("<p>Неверный формат идентификатора пользователя!</p>");
            return;
        }

        User user = userRepository.findById(id);
        if (user == null) {
            resp.getWriter().println("<p>Пользователь не найден!</p>");
        } else {
            resp.getWriter().println("<p>Имя : " + user.getUsername() + "</p>");
            resp.getWriter().println("<p>ID  : " + id + "</p>");
        }


    }
}
