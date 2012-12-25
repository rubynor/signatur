package no.rubynor.signatur.provider.bankid.handler;

import javax.servlet.ServletException;

import no.bbs.server.exception.BIDException;
import no.bbs.server.vos.BIDSessionData;
import no.rubynor.signatur.provider.bankid.BIDRequest;
import no.rubynor.signatur.provider.bankid.constants.Constants;
import no.rubynor.signatur.provider.bankid.session.Session;
import no.rubynor.signatur.provider.bankid.util.ServletUtil;

public final class ErrorHandler {

	public static String handleError(BIDRequest bidInfo, Session session)
			throws BIDException, ServletException {

		BIDSessionData sessionData = session.getBidSessionData();

		ServletUtil.getBankIDFacade().verifyTransactionRequest(
				bidInfo.getOperation(), bidInfo.getEncKey(),
				bidInfo.getEncData(), bidInfo.getEncAuth(), session.getSid(),
				sessionData);

		// Not all error codes means a technical error. E.g. 9001 means
		// that the user aborted.
		// See "BankID Implementation Guide" for more of these codes.
		String errorCode = sessionData.getErrCode();
		sessionData.setNextURL(Constants.FAILURE_URL + "?sid="
				+ session.getSid());
		session.setErrorCode(errorCode);
		return ServletUtil.getBankIDFacade().verifyTransactionResponse(
				sessionData);
	}

	private ErrorHandler() {
		// to avoid initialization
	}

}
