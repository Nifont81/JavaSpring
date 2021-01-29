package ru.geekbrains;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/first-servlet")
public class FirstServlet implements Servlet {

    private ServletConfig config;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // servletConfig - главное средство взаимодействия сервлета с сервером приложений

        this.config = servletConfig;

    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    // Каждый раз, когда на наш сервлет будет приходить какой-либо запрос он будет вызываться.
    // 1 парам - можно понять, что за запрос к нам пришел
    // 2 парам - можно с помощью него ответить.
        servletResponse.getWriter().println("<h1>Привет от сервлета !!!</h1>");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
