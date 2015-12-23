package univ.oop.lab3.controller;

import univ.oop.lab3.model.RequestManager;
import univ.oop.lab3.model.WorkListManager;
import univ.oop.lab3.model.entities.Citizen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by flystyle on 21.12.15.
 */
public class WorkListController extends HttpServlet {
    private static final Logger log = Logger.getLogger(String.valueOf(WorkListController.class));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        Citizen user = (Citizen) req.getSession().getAttribute("citizen");

        if(!Citizen.UserIsNull(user, req)) {

            String prevPageUrl = req.getHeader("Referer");

            if (req.getParameter("checks_button") != null) {
                WorkListManager.CheckList();
            }

            resp.sendRedirect(prevPageUrl);

        }
    }
}
