package no.rubynor.signatur.provider.bankid.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.bbs.bankid.seid.sdo.components.SEID_SDO;
import no.bbs.server.constants.JServerConstants;
import no.bbs.server.exception.BIDException;
import no.bbs.server.implementation.BIDFacade;
import no.bbs.server.vos.BIDSessionData;
import no.bbs.server.vos.CertificateInfo;
import no.bbs.server.vos.CertificateStatus;
import no.rubynor.signatur.provider.bankid.BIDRequest;
import no.rubynor.signatur.provider.bankid.constants.Constants;
import no.rubynor.signatur.provider.bankid.constants.ExampleData;
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
 * Description: Handles the callback communication with the BankID applet. Its
 * here the the user is authenticated and data to be signed is set<BR>
 * 
 * @author tfa
 */
@WebServlet("/sign")
public class BidSignServlet extends HttpServlet {

	private static final long serialVersionUID = -1001028350079753315L;

	private static final String TEXT_MIMETYPE = "text/plain";
	private static final String BIDXML_MIMETYPE = "text/BIDXML";
	private static final String PDF_MIMETYPE = "application/pdf";
	private static final String SIGN_ENCODING = "ISO-8859-1";
	private static final Logger LOGGER = Logger.getLogger(BidSignServlet.class);
	// This is the description of the data. Used both in the response to
	// initSign and for the data description during SDO creation.
	private static final String DATA_DESCRIPTION = "En beskrivende tekst av dokumentet";

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
			if ("initSign".equals(operation)) {
				resp2Client = initSign(bidInfo, session);
			} else if ("verifySign".equals(operation)) {
				resp2Client = verifySign(bidInfo, session);
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

	private String verifySign(BIDRequest bidInfo, Session session)
			throws BIDException {
		// BIDSessionData sessionData = (BIDSessionData)
		// httpSession.getAttribute("BIDSessionData");
		BIDSessionData sessionData = session.getBidSessionData();
		Boolean mobileSign = session.isMobileSign();
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
				bidInfo.getSid(), sessionData);
		// End-user is now successfully authenticated
		session.setState(SessionState.AUTHENTICATED);

		// If the infoitems for AI (additional information) from the VA
		// is set, the following
		// will be available at the CertificateStatus object:
		CertificateStatus certStatus = sessionData.getCertificateStatus();
		if (certStatus.getAddInfoSSNErr() != null) {
			LOGGER.warn("User's SSN not found: "
					+ certStatus.getAddInfoSSNErr());
		} else {
			LOGGER.warn("Found the user's SSN: " + certStatus.getAddInfoSSN());
		}
		if (certStatus.getAddInfoAccountErr() != null) {
			LOGGER.warn("User's account number not found: "
					+ certStatus.getAddInfoAccountErr());
		} else {
			LOGGER.warn("Found the user's account number: "
					+ certStatus.getAddInfoAccount());
		}
		if (certStatus.getAddInfoOrgNoErr() != null) {
			LOGGER.warn("User's org number not found: "
					+ certStatus.getAddInfoOrgNoErr());
		} else {
			LOGGER.warn("Found the user's org number: "
					+ certStatus.getAddInfoOrgNo());
		}
		// Other application logic can be done before returning a
		// response to the client, e.g. obtaining the name of the signer
		CertificateInfo certInfo = bankIDFacade.getCertificateInfo(bankIDFacade
				.getPKCS7Info(sessionData.getClientSignature())
				.getSignerCertificate());
		LOGGER.warn("CommonName of the user logged in and signed: "
				+ certInfo.getCommonName());
		session.setUser(certInfo.getCommonName());
		session.setCertificateInfo(certInfo);
		// WARNING, a mobile signature should not be wrapped in an SDO
		// as it will not validate
		if (mobileSign == null) {
			// After a successfull signing, an SDO can be created if the
			// signature is to be used in a signed
			// document. To create a signed document between the
			// merchant and the client, the following information
			// is required: merchant signature and certificate status,
			// client signature and certificate status,
			// the data that is signed, the MIME type of the data and a
			// description of the data.
			SEID_SDO sdo = bankIDFacade.createSDO(sessionData
					.getClientSignature().getBytes(), sessionData
					.getMerchantSignature().getBytes(), sessionData
					.getSignedDataBytes(), sessionData
					.getDataToBeSignedMimeType(), sessionData.getMerchantOCSP()
					.getBytes(), sessionData.getClientOCSP().getBytes(),
					sessionData.getDataDescription());

			// The SDO will be transmitted back to the client as a
			// receipt. The SDO set here must not include the
			// actual data that was signed. The client will when
			// receiving this receipt add the data him/herself.
			sessionData.setSdo(sdo);
		}

		// Set the "successfull logged in page" (will be ignored if
		// BankID mobile)
		sessionData.setNextURL(Constants.INFO_URL + "?sid=" + bidInfo.getSid());
		// sessionData.setTarget("htmlframe"); //Optionally set a
		// spesific frame to load

		String resp2Client = bankIDFacade
				.verifyTransactionResponse(sessionData);

		// Do post processing, like calling addSignedData to create a
		// self-contained SDO.
		// Persist the sdo in some contract archive afterwards.
		// SEID_SDO selfContainedSDO =
		// sessionData.getSdo().addB6SignedData(sessionData.getSignedDataBytes());
		return resp2Client;
	}

	private String initSign(BIDRequest bidInfo, Session session)
			throws BIDException, UnsupportedEncodingException, ServletException {
		BIDSessionData sessionData = new BIDSessionData();
		String signType = session.getSignType(); // xml|pdf|text
		Boolean mobileSign = session.isMobileSign();

		// If user profile is to be used
		// sessionData.setUserID(""); //The user ID of the end user
		// sessionData.setOtpServiceName(""); //The name of the OTP service
		// sessionData.setBank(""); //The issuer bank name

		// If session keep ALL is set in the applet tag, this option lets you
		// override this and force an OTP entry in every operation
		// SecureChannelInfo sci = new SecureChannelInfo();
		// sci.addSecureChannelInfo("c_optsession", "N");
		// sessionData.setSecureChannelInfo(sci);

		// Three diffrent types of data that can be signed
		sessionData.setDataDescription(DATA_DESCRIPTION);
		if ("xml".equals(signType)) {
			// The XML and XSL is in plain text
			sessionData.setDataToBeSignedMimeType(BIDXML_MIMETYPE);
			sessionData.setDataToBeSignedXMLFormat(ExampleData.SIGN_XSL);
			sessionData.setDataToBeSigned(ExampleData.XML_TO_SIGN);
		} else if ("pdf".equals(signType)) {
			sessionData.setDataToBeSignedMimeType(PDF_MIMETYPE);
			sessionData.setDataToBeSigned(new String(ExampleData.PDF_TO_SIGN,
					SIGN_ENCODING));
		} else if ("text".equals(signType)) {
			if (mobileSign != null && mobileSign.booleanValue()) {
				String mobileText = new String(bankIDFacade
						.prepareTextForMobile(ExampleData.TEXT_TO_SIGN),
						SIGN_ENCODING);
				sessionData.setDataToBeSigned(mobileText);
			} else {
				sessionData.setDataToBeSigned(ExampleData.TEXT_TO_SIGN);
			}
			LOGGER.debug("textToSign: " + sessionData.getDataToBeSigned());
			sessionData.setDataToBeSignedMimeType(TEXT_MIMETYPE);
		} else {
			throw new ServletException(
					"This BankID signature type is not supported: " + signType);
		}

		session.setBidSessionData(sessionData);
		String resp2Client = bankIDFacade.initTransaction(bidInfo
				.getOperation(), bidInfo.getEncKey(), bidInfo.getEncData(),
				bidInfo.getEncAuth(), bidInfo.getSid(), sessionData);

		session.setBidSessionData(sessionData);
		return resp2Client;
	}
}
