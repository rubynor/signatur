package no.rubynor.signatur.provider.bankid.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import no.bbs.server.exception.BIDException;
import no.bbs.server.implementation.BIDFacade;
import no.bbs.server.implementation.BIDFactory;
import no.rubynor.signatur.provider.bankid.constants.Constants;
import no.rubynor.signatur.provider.bankid.session.SessionStore;

import org.apache.log4j.Logger;


public final class ServletUtil {

	private static final Logger LOGGER = Logger.getLogger(ServletUtil.class);

	private static BIDFacade bankIDFacade = null;

	static {
		if (bankIDFacade == null) {
			try {
				BIDFactory.getInstance().loadBankIDContext(
						Constants.PROPERTIES_FOLDER, Constants.MERCHANT_NAME,
						Constants.BID_PASSWORD);
				bankIDFacade = BIDFactory.getInstance().getFacade(
						Constants.MERCHANT_NAME);
			} catch (BIDException e) {
				throw new RuntimeException("Could not load BankID context", e);
			}
		}
	}

	public static String getAndValidateActionParameter(
			HttpServletRequest request) throws ServletException {
		String action = request.getParameter("action");
		if (action == null) {
			throw new ServletException("Missing required parameter 'action'");
		}
		if (!"sign".equalsIgnoreCase(action)
				&& !"auth".equalsIgnoreCase(action)) {
			throw new ServletException(
					"Invalid 'action' parameter. Valid values are 'sign' and 'auth'.");
		}
		return action;
	}

	public static BIDFacade getBankIDFacade() {
		return bankIDFacade;
	}

	public static String getAndValidateSessionID(HttpServletRequest request)
			throws ServletException {
		String sid = request.getParameter("sid");
		if (sid == null || sid.equalsIgnoreCase("")) {
			throw new ServletException("Parameter 'sid' is required.");
		}
		if (SessionStore.getSession(sid) == null) {
			LOGGER
					.debug("Parameter 'sid' is not connected to a valid session (sid="
							+ sid + ").");
			throw new ServletException(
					"Parameter 'sid' is not connected to a valid session.");
		}
		return sid;
	}

	public static String getAndValidateOperationParameter(
			HttpServletRequest request) throws ServletException {
		String operation = request.getParameter("operation");
		if (operation == null) {
			throw new ServletException("Missing required parameter 'operation'");
		}
		if (!"initAuth".equalsIgnoreCase(operation)
				&& !"initSign".equalsIgnoreCase(operation)
				&& !"verifyAuth".equalsIgnoreCase(operation)
				&& !"verifySign".equalsIgnoreCase(operation)
				&& !"getNextURL".equalsIgnoreCase(operation)
				&& !"handleError".equalsIgnoreCase(operation)) {
			throw new ServletException(
					"Invalid 'action' parameter. Valid values are 'initAuth', 'initSign', 'verifyAuth', 'verifySign',"
							+ " 'getNextURL' and 'handleError'.");
		}
		return operation;
	}

	private ServletUtil() {
		// avoid external initialization
	}
}
