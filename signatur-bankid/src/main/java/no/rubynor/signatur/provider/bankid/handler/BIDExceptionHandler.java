package no.rubynor.signatur.provider.bankid.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;

import no.bbs.server.exception.BIDException;
import no.bbs.server.vos.BIDSessionData;
import no.rubynor.signatur.provider.bankid.BIDRequest;
import no.rubynor.signatur.provider.bankid.constants.Constants;
import no.rubynor.signatur.provider.bankid.session.Session;
import no.rubynor.signatur.provider.bankid.session.SessionState;
import no.rubynor.signatur.provider.bankid.util.ServletUtil;

import org.apache.log4j.Logger;

public final class BIDExceptionHandler {

	private static final Logger LOGGER = Logger
			.getLogger(BIDExceptionHandler.class);

	public static String handleException(BIDRequest bidInfo, Session session,
			BIDException ex, String remoteIP) throws ServletException {
		LOGGER.warn("Received exception from " + remoteIP);
		// If a BidException occurs an error message must be sent to the
		// client
		BIDSessionData sessionData = session.getBidSessionData();
		sessionData.setErrCode("" + ex.getErrorCode());

		// If the key can not be found, a response must be sent, unencrypted
		SecretKey key = sessionData.getSessionKey();
		if (key == null) {
			// Unencrypted response
			return "errCode=" + ex.getErrorCode() + "&nextURL="
					+ Constants.FAILURE_URL + "?sid=" + session.getSid();
		} else {
			// Encrypted response
			try {
				Writer errorStack = new StringWriter();
				PrintWriter printWriter = new PrintWriter(errorStack);
				ex.printStackTrace(printWriter);
				session.setErrorCode("" + ex.getErrorCode());
				session.setErrorStack(errorStack.toString());

				sessionData.setNextURL(Constants.FAILURE_URL + "?sid="
						+ session.getSid());
				return ServletUtil.getBankIDFacade().verifyTransactionResponse(
						sessionData);
			} catch (BIDException ex2) {
				LOGGER.warn("Error verifying transaction.", ex2);
			}
		}
		return null;
	}

	public static void handleMobileException(Session session, BIDException ex) {
		session.setState(SessionState.NOT_AUTHENTICATED);
		LOGGER.info("Error handling mobile request", ex);
		Writer errorStack = new StringWriter();
		PrintWriter printWriter = new PrintWriter(errorStack);
		ex.printStackTrace(printWriter);
		session.setErrorCode("" + ex.getErrorCode());
		session.setErrorStack(errorStack.toString());
	}

	private BIDExceptionHandler() {
		// to avoid initialization
	}

}
