package univ.oop.lab3.controller;

import univ.oop.lab3.model.entities.Citizen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by flystyle on 20.12.15.
 */
public class LogoutController extends HttpServlet {
    private static final Logger log = Logger.getLogger(String.valueOf(LogoutController.class));

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Citizen user = (Citizen) req.getSession().getAttribute("citizen");
        req.setCharacterEncoding("utf-8");
        if (user == null) {
            req.setAttribute("title", "logout");
            req.setAttribute("infoMessage", "impossible_operation");
            req.getRequestDispatcher("/view/info_page.jsp");
            log.info("Impossible operation");
            return;
        }

        log.info(user.getLogin()+" sign out");
        req.getSession().removeAttribute("citizen");
        resp.sendRedirect("/view/login.jsp");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
