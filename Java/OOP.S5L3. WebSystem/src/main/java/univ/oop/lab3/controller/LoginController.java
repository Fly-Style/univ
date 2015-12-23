package univ.oop.lab3.controller;

import univ.oop.lab3.model.LoginVerification;
import univ.oop.lab3.model.entities.Citizen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by flystyle on 19.12.15.
 */
public class LoginController extends HttpServlet {
    private static final Logger log = Logger.getLogger(String.valueOf(LoginController.class));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        Citizen user = (Citizen) req.getSession().getAttribute("citizen");

        if (user != null) {
            log.info("Impossible operation");

            req.setAttribute("title", "login");
            req.setAttribute("infoMessage", "impossible_operation");
            req.getRequestDispatcher("/view/info_page.jsp").forward(req, resp);
            return;
        }

        String currentLogin = req.getParameter("login");
        String currentPass = req.getParameter("password");
        user = LoginVerification.verify(currentLogin, currentPass);

        if (user == null) {
            log.info("Login or password are incorrect");
            req.setAttribute("title", "login_failed");
            req.setAttribute("infoMessage", "login_or_password_are_incorrect");
            req.getRequestDispatcher("/view/info_page.jsp").forward(req, resp);
        }
        else {

            // Start session for current user
            req.getSession().setAttribute("citizen", user);

            log.info(user.getLogin() + "authorized");

            // Redirect user to homepage
            resp.sendRedirect("/view/citizen/consumer_home.jsp");
        }
    }
}