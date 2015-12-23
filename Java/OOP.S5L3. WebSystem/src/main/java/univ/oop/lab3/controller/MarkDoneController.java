package univ.oop.lab3.controller;

import univ.oop.lab3.model.RequestManager;
import univ.oop.lab3.model.entities.Citizen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class MarkDoneController extends HttpServlet {
	private static final Logger log = Logger.getLogger(String.valueOf(MarkDoneController.class));

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Citizen user = (Citizen) req.getSession().getAttribute("citizen");

		if(!Citizen.UserIsNull(user, req)) {

			String prevPageUrl = req.getHeader("Referer");
			String id = req.getParameter("id");
			String action = req.getParameter("action");


			if (req.getParameter("check_button") != null) {
				RequestManager.CheckList();
			}

			resp.sendRedirect(prevPageUrl);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
