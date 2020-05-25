package src.main.servlet;

import src.main.model.User;
import src.main.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserService.getInstance();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if (name == null || password == null) {
            resp.getWriter().println("Не введен пароль или логин");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            if (userService.addUser(new User(name, password))) {
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } else {
                resp.getWriter().println("Client not add");
            }
        }
    }
}
