package no.rubynor.signatur.provider.bankid.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.bbs.server.exception.BIDException;
import no.bbs.server.implementation.BIDFacade;
import no.bbs.server.vos.TagInfo;
import no.rubynor.signatur.provider.bankid.constants.Constants;
import no.rubynor.signatur.provider.bankid.session.Session;
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
 * Description: Load's the correct BankID applet<BR>
 * 
 * @author tfa
 */
@WebServlet("/loadApplet")
public class AppletLoaderServlet extends HttpServlet {

	private static final long serialVersionUID = -6229193720682103592L;
	private static final Logger LOGGER = Logger
			.getLogger(AppletLoaderServlet.class);

	private final transient BIDFacade bankIDFacade = ServletUtil
			.getBankIDFacade();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = ServletUtil.getAndValidateActionParameter(request);
		String signType = request.getParameter("signType");
		String javaversion = request.getParameter("javaversion");
		String os = request.getParameter("os");
		String userAgent = request.getHeader("User-Agent");

		Session session = SessionStore.createSession();
		session.setAction(action);
		String sid = session.getSid();

		// If we don't get java version, the detector applet must be used
		if (javaversion == null) {
			String appletString = "<APPLET id='DetectorApplet' height='0' width='0' "
					+ "code='no.bbs.detector.Detector' name='DetectorApplet'>"
					+ "<PARAM NAME='serverurl' VALUE='"
					+ request.getRequestURL()
					+ "'/>"
					+ "<PARAM NAME=action VALUE='"
					+ action
					+ "'/>"
					+ "<PARAM NAME=signType VALUE='"
					+ signType
					+ "'/>"
					+ "<PARAM NAME=sid VALUE='" + sid + "'/></APPLET>";
			LOGGER.debug("Writing applet string to response: " + appletString
					+ " (sid=" + sid + ").");
			response.getWriter().write(
					"<html><head><title>BankID example</title></head><body>"
							+ appletString + "</body></html>");
		} else {
			TagInfo tagInfo = new TagInfo();
			tagInfo.setOs(os);
			tagInfo.setJavaVersion(javaversion);
			tagInfo.setUserAgent(userAgent);
			tagInfo.setClientType("NC");
			tagInfo.setSid(sid);
			tagInfo.setAction(action);
			// Applet will go here if something fails:
			tagInfo.setNextURL(Constants.FAILURE_URL);
			// Applet will remember fnr and OTP between operations:
			// tagInfo.setSessionKeep("all");
			if ("sign".equalsIgnoreCase(action)) {
				session.setSignType(signType);
				// Turn off the checkbox 'Jeg har lest og forstått..'
				tagInfo.setShowUnderStanding("N");
				// Turn off the 'Signering er utført.'
				tagInfo.setShowConfirmation("N");
				// Turn on big popup window when signing XML data
				tagInfo.setShowBigWindow("Y");
				tagInfo.setUrl(Constants.SIGN_URL);

			} else if ("auth".equalsIgnoreCase(action)) {
				tagInfo.setUrl(Constants.AUTH_URL);
			}

			try {
				tagInfo = bankIDFacade.getTag(tagInfo);
			} catch (BIDException e) {
				response.getWriter().write(
						"Failed to get Applet tag: " + e.toString());
			}
			if (tagInfo.getInfoFromServer() != null) {
				// BankID server not supported?
				LOGGER.warn("Message from TagServer: "
						+ tagInfo.getInfoFromServer());
			}
			if (tagInfo.getClientInfoFromServer() != null) {
				if ("19-01".equalsIgnoreCase(tagInfo.getClientInfoFromServer())) {
					LOGGER.warn("Client running unsupported Java version");
				} else if ("19-02".equalsIgnoreCase(tagInfo
						.getClientInfoFromServer())) {
					LOGGER.warn("Client running unsupported browser version");
				} else if ("19-03".equalsIgnoreCase(tagInfo
						.getClientInfoFromServer())) {
					LOGGER.warn("Client running unsupported os version");
				}
				LOGGER.warn("Message from TagServer client info: "
						+ tagInfo.getInfoFromServer());
			}
			LOGGER.debug("Writing tag to response: " + tagInfo.getTag()
					+ " (sid=" + sid + ").");
			response.getWriter().write(
					"<html><head><title>BankID example</title></head><body>"
							+ tagInfo.getTag() + "</body></html>");
		}
	}
}
