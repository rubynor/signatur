package no.rubynor.signatur.provider.bankid.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.rubynor.signatur.provider.bankid.session.Session;
import no.rubynor.signatur.provider.bankid.session.SessionStore;
import no.rubynor.signatur.provider.bankid.util.ServletUtil;


@WebServlet("/info")
public class InfoServlet extends HttpServlet {

	private static final long serialVersionUID = -7254243744920056639L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String sid = ServletUtil.getAndValidateSessionID(request);
		Session session = SessionStore.getSession(sid);
		request.setAttribute("session", session);

		// forward to view
		request.getRequestDispatcher("WEB-INF/info.jsp").forward(request,
				response);
	}

}
