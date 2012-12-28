package no.rubynor.signatur.provider.bankid.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import no.bbs.server.vos.BIDSessionData;
import no.bbs.server.vos.CertificateInfo;
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
		PrintWriter out = response.getWriter();
		out.write("<h1>Operasjon fullført</h1>");
		out.write("<p><a href=\"test.html\">Tilbake</a></h1>");
		
		String sid = ServletUtil.getAndValidateSessionID(request);
		Session s = SessionStore.getSession(sid);
		String user = s.getUser();
		CertificateInfo certInfo = s.getCertificateInfo();
		String errorCode = s.getErrorCode();
		String errorStack = s.getErrorStack();
		BIDSessionData sessionData = s.getBidSessionData();
		//This must match in order to say that the user is logged in
		if (sessionData.getSignedDataBytes() == null) {
			out.print("<p>Takk for at du logget på, " + user + "</p>");
		} else {		
			String signedData = new String(sessionData.getSignedDataBytes(), "ISO-8859-1");		
			out.print("<p>Takk for signaturen din, " + user + "</p>"
					+ "<p>Du signerte over følgende data: </p><p>" + StringEscapeUtils.escapeHtml(signedData)
					+ "</p>");
		}
		if (certInfo != null) {
			out.print("<table border>");
			out.print("<tr><th colspan=2>SertifikatInformasjon:</th></tr>");
			out.print("<tr><td>Bank:</td><td>" + certInfo.getBankName()
					+ "</td></tr>");
			out.print("<tr><td>CommonName:</td><td>"
					+ certInfo.getCommonName() + "</td></tr>");
			out.print("<tr><td>DateOfBirth:</td><td>"
					+ certInfo.getDateOfBirth() + "</td></tr>");
			out.print("<tr><td>PhoneNumber:</td><td>"
					+ certInfo.getPhoneNumber() + "</td></tr>");
			out.print("<tr><td>E-mail:</td><td>"
					+ certInfo.getEmailAddress() + "</td></tr>");
			out.print("<tr><td>KeyAlgorithm:</td><td>"
					+ certInfo.getKeyAlgorithm() + "</td></tr>");
			out.print("<tr><td>KeySize:</td><td>" + certInfo.getKeySize()
					+ "</td></tr>");
			out.print("<tr><td>Originator:</td><td>"
					+ certInfo.getOriginator() + "</td></tr>");
			out.print("<tr><td>PolicyOID:</td><td>"
					+ certInfo.getPolicyOID() + "</td></tr>");
			out.print("<tr><td>ValueLimitAmount:</td><td>"
					+ certInfo.getQcValueLimitAmount() + "</td></tr>");
			out.print("<tr><td>ValueLimitCurrency:</td><td>"
					+ certInfo.getQcValueLimitCurrency() + "</td></tr>");
			out.print("<tr><td>SerialNumber:</td><td>"
					+ certInfo.getSerialNumber() + "</td></tr>");
			out.print("<tr><td>SubjectName:</td><td>"
					+ certInfo.getSubjectName() + "</td></tr>");
			out.print("<tr><td>UniqueId:</td><td>" + certInfo.getUniqueId()
					+ "</td></tr>");
			out.print("<tr><td>ValidFrom:</td><td>"
					+ certInfo.getValidFrom() + "</td></tr>");
			out.print("<tr><td>ValidTo:</td><td>" + certInfo.getValidTo()
					+ "</td></tr>");
			out.print("</table>");
		}

		//Print any errors during login/signing
		if (errorCode != null) {
			out.write("errorCode: <pre>" + errorCode + "</pre><br/>");
		}
		if (errorStack != null) {
			out.write("errorStack: <pre>" + errorStack + "</pre><br/>");
		}

		if (sid == null) {
			sid = (String) request.getAttribute("sid");
		}
		if (sid != null) {
			Session ses = SessionStore.getSession(sid);
			if (ses != null) {
				out.write("<p>" + ses + "</p>");
			}
		}
	}

}
