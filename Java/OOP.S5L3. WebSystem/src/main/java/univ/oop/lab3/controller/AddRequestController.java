package univ.oop.lab3.controller;

import univ.oop.lab3.model.CitizenManager;
import univ.oop.lab3.model.RequestManager;
import univ.oop.lab3.model.entities.Citizen;
import univ.oop.lab3.model.entities.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Created by flystyle on 20.12.15.
 */
public class AddRequestController extends HttpServlet {

    private static final Logger log = Logger.getLogger(String.valueOf(AddRequestController.class));

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        Citizen user = (Citizen) req.getSession().getAttribute("citizen");

        String infoPageTitle = "add_task_status";
        String infoMessage = null;

        if (user == null) {
            infoPageTitle = "access";
            infoMessage = "access_denied";
        } else {
            int citizenId = Integer.valueOf(((Citizen) req.getSession().getAttribute("citizen")).getId());
            System.out.println(citizenId);
            String workObject = req.getParameter("work_object");
            String scale = req.getParameter("scale");
            Timestamp timestamp = Timestamp.valueOf(req.getParameter("work_time"));

                Request task = new Request(workObject, timestamp, scale, citizenId);
                boolean addTaskResult = false;
                try {
                    addTaskResult = Request.AddRequest(task);

                    if (addTaskResult) {
                        infoMessage = "task_was_successfully_added";

                        log.info("Task was successfully added");
                    } else {
                        infoMessage = "error_adding_task_to_database";

                        log.info("Error occurred while adding task to database");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            //}

            req.setAttribute("title", infoPageTitle);
            req.setAttribute("infoMessage", infoMessage);
            req.getRequestDispatcher("/view/info_page.jsp").forward(req, resp);
        }
    }
}
