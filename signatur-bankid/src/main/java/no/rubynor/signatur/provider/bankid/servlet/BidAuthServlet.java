package no.rubynor.signatur.provider.bankid.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.bbs.server.constants.JServerConstants;
import no.bbs.server.exception.BIDException;
import no.bbs.server.implementation.BIDFacade;
import no.bbs.server.vos.BIDSessionData;
import no.bbs.server.vos.CertificateInfo;
import no.bbs.server.vos.CertificateStatus;
import no.rubynor.signatur.provider.bankid.BIDRequest;
import no.rubynor.signatur.provider.bankid.constants.Constants;
import no.rubynor.signatur.provider.bankid.handler.BIDExceptionHandler;
import no.rubynor.signatur.provider.bankid.handler.ErrorHandler;
import no.rubynor.signatur.provider.bankid.session.Session;
import no.rubynor.signatur.provider.bankid.session.SessionState;
import no.rubynor.signatur.provider.bankid.session.SessionStore;
import no.rubynor.signatur.provider.bankid.util.ServletUtil;

import org.apache.log4j.Logger;

/**
 * <BR>
 * ----------------------------------------------<BR>
 * <B>Copyright � 2008<BR>
 * Bankenes BetalingsSentral AS (BBS)<BR>
 * Organisation Number N- 975 946 231<BR>
 * Haavard Martinsens vei 54<BR>
 * N- 0045 OSLO<BR>
 * Norway</B><BR>
 * ----------------------------------------------<BR>
 * Description: Handles the callback communication with the BankID applet. It is
 * here the user is authenticated<BR>
 * 
 * @author tfa
 */
@WebServlet("/auth")
public class BidAuthServlet extends HttpServlet {

	private static final long serialVersionUID = -5438175756387828551L;

	private static final Logger LOGGER = Logger.getLogger(BidAuthServlet.class);

	private final transient BIDFacade bankIDFacade = ServletUtil
			.getBankIDFacade();

	/**
	 * Handles callback's from BankID applet
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BIDRequest bidInfo = new BIDRequest(request);
		LOGGER.debug(bidInfo);
		Session session = SessionStore.getSession(bidInfo.getSid());

		String operation = bidInfo.getOperation();
		String resp2Client = "";
		try {
			if ("initAuth".equals(operation)) {
				resp2Client = initAuth(bidInfo, session);
			} else if ("verifyAuth".equals(operation)) {
				resp2Client = verifyAuth(bidInfo, session);
			} else if ("handleError".equals(operation)) {
				resp2Client = ErrorHandler.handleError(bidInfo, session);
			}
		} catch (BIDException ex) {
			resp2Client = BIDExceptionHandler.handleException(bidInfo, session,
					ex, request.getRemoteAddr());
			session.setState(SessionState.NOT_AUTHENTICATED);
		}
		LOGGER.debug("Writing response to client: " + resp2Client);
		response.getWriter().write(resp2Client);
	}

	private String verifyAuth(BIDRequest bidInfo, Session session)
			throws BIDException {
		BIDSessionData sessionData =  session.getBidSessionData();

		// If additional information is wanted about the client, and an
		// agreement to obtain
		// such information exists with the bank the following infoitems
		// can also be set:
		ArrayList<String> additionalInfos = new ArrayList<String>();
		// Will retrieve the acount number of the client, if it exists
		additionalInfos.add(JServerConstants.LABEL_OID_OCSP_ACCOUNT);
		// Will retrieve the social security number of the client, if it
		// exists
		additionalInfos.add(JServerConstants.LABEL_OID_OCSP_SSN);
		// Will retrieve the organization number of the client, if the
		// client is a merchant and it exists
		additionalInfos.add(JServerConstants.LABEL_OID_OCSP_ORGNO);
		sessionData.setAdditionalInfoList(additionalInfos);

		bankIDFacade.verifyTransactionRequest(bidInfo.getOperation(), bidInfo
				.getEncKey(), bidInfo.getEncData(), bidInfo.getEncAuth(),
				session.getSid(), sessionData);
		// End-user is now successfully authenticated
		session.setState(SessionState.AUTHENTICATED);

		// If the infoitems for AI (additional information) from the VA
		// is set, the following
		// will be available at the CertificateStatus object:
		CertificateStatus certStatus = sessionData.getCertificateStatus();
		if (certStatus.getAddInfoSSNErr() != null) {
			LOGGER.info("User's SSN not found: "
					+ certStatus.getAddInfoSSNErr());
		} else {
			LOGGER.info("Found the user's SSN: " + certStatus.getAddInfoSSN());
		}
		if (certStatus.getAddInfoAccountErr() != null) {
			LOGGER.info("User's account number not found: "
					+ certStatus.getAddInfoAccountErr());
		} else {
			LOGGER.info("Found the user's account number: "
					+ certStatus.getAddInfoAccount());
		}
		if (certStatus.getAddInfoOrgNoErr() != null) {
			LOGGER.info("User's org number not found: "
					+ certStatus.getAddInfoOrgNoErr());
		} else {
			LOGGER.info("Found the user's org number: "
					+ certStatus.getAddInfoOrgNo());
		}
		// A usage for the client signature is to obtain information
		// about the certificate of the client, e.g. the name:
		CertificateInfo certInfo = bankIDFacade.getCertificateInfo(bankIDFacade
				.getPKCS7Info(sessionData.getClientSignature())
				.getSignerCertificate());
		session.setUser(certInfo.getCommonName());
		session.setCertificateInfo(certInfo);
		// Set the "successfull logged in page" (will be ignored if
		// BankID mobile)
		sessionData.setNextURL(Constants.INFO_URL + "?sid=" + session.getSid());
		// sessionData.setTarget("htmlframe"); //Optionally set a
		// spesific frame to load

		// an optional target for the nextURL, e.g. if the frames are
		// used
		// sessionData.setTarget();
		return bankIDFacade.verifyTransactionResponse(sessionData);
	}

	private String initAuth(BIDRequest bidInfo, Session session)
			throws BIDException {
		// The merchant application will store this object between
		// callback's from the
		// BankID applet. It will hold info for keeping a secure
		// comminication with the applet.
		BIDSessionData sessionData = new BIDSessionData();
		session.setBidSessionData(sessionData);

		// If user profile is to be used
		// sessionData.setUserID(""); //The user ID of the end user
		// sessionData.setOtpServiceName(""); //The name of the OTP
		// service
		// sessionData.setBank(""); //The issuer bank name

		// If session keep ALL is set in the applet tag, this option
		// lets you
		// override this and force an OTP entry in every operation
		// SecureChannelInfo sci = new SecureChannelInfo();
		// sci.addSecureChannelInfo("c_optsession", "N");
		// sessionData.setSecureChannelInfo(sci);
		String resp2Client = bankIDFacade.initTransaction(bidInfo.getOperation(),
				bidInfo.getEncKey(), bidInfo.getEncData(),
				bidInfo.getEncAuth(), session.getSid(), sessionData);
		session.setBidSessionData(sessionData);

		// Check man-in-the-middle
		// String clientIp = sessionData.getClientIP();
		// if(request.getRemoteAddr().equalsIgnoreCase(clientIp)) {
		// throw new BIDException(999,
		// "Merchant and COI don't see the same IP: " + clientIp +
		// " != " + request.getRemoteAddr());
		// }
		return resp2Client;
	}
}
