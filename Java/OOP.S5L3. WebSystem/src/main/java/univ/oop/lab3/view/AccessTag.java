package univ.oop.lab3.view;

import univ.oop.lab3.model.entities.Citizen;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AccessTag extends TagSupport {
	String title;
	String infoMessage;
	Citizen user;
	String requiredUser;
	int number;

	@Override
	public int doStartTag() throws JspException {
		if (user == null) {
			try {
				ServletRequest request = pageContext.getRequest();
				request.setAttribute("title", title);
				request.setAttribute("infoMessage", infoMessage);
				pageContext.forward("/view/info_page.jsp");
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return super.doStartTag();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public Citizen getUser() {
		return user;
	}

	public void setUser(Citizen user) {
		this.user = user;
	}

	public String getRequiredUser() {
		return requiredUser;
	}

	public void setRequiredUser(String requiredUser) {
		this.requiredUser = requiredUser;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
