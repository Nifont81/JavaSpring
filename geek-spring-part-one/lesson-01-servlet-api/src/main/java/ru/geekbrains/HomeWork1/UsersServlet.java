package ru.geekbrains.HomeWork1;
/*

3. Доработать приложение, которое начал писать на уроке преподаватель,
чтобы один из сервлетов выводил список пользователей из репозитория.
У этот сервлет должен быть доступен по префиксу /users.

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

@WebServlet("/users")
public class UsersServlet extends HttpServlet { // Полная поддержка протокола HTTP

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userRepository.findAll();

        resp.getWriter().println("<table style=\"width:100%\"");
        resp.getWriter().println("<tr><th>Пользователи</th></tr>");
        for (User user: users) {
            String href = "<a href="+req.getContextPath()+"/user/"+user.getId()+">";
            resp.getWriter().println(href);
            resp.getWriter().println("<tr><td>"+href+user.getUsername()+"</td></tr>");
        }
        resp.getWriter().println("</table>");
    }
}
