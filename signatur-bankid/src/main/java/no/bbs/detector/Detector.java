package no.bbs.detector;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This applet reads the javaversion and the type of the operating system. The
 * information is then sent to the merchant server application to be used when
 * calling BankID Server to get the proper applet tag.
 * 
 * The serverURL parameter value must include the URL of the merchant server
 * application.
 * 
 * The sid parameter is an optional parameter that if present is added to the
 * request sent to the server application. It may be used to keep track of the
 * user session.
 * 
 * @version 1.0
 */
public class Detector extends Applet {

	private static final long serialVersionUID = 7247778983695909701L;

	public void init() {

		System.err.println("New Servlet");
		System.err.println(System.getProperty("os.name"));
		System.err.println(System.getProperty("java.version"));
		// requires extra permissions:
		// System.err.println(System.getProperty("User-Agent"));

		String osName = System.getProperty("os.name");
		StringBuffer strURL = new StringBuffer(getParameter("serverURL"));
		strURL.append("?javaversion=").append(
				System.getProperty("java.version"));

		String action = getParameter("action");
		if (action != null && action.length() > 0) {
			strURL.append("&action=").append(action);
		}

		String signType = getParameter("signType");
		if (signType != null && signType.length() > 0) {
			strURL.append("&signType=").append(signType);
		}

		osName = osName.toLowerCase();
		if (osName.indexOf("linux") >= 0) {
			strURL.append("&os=").append("LIN");

		} else if (osName.indexOf("solaris") >= 0) {
			strURL.append("&os=").append("UN");

		} else if (osName.indexOf("win") >= 0) {
			strURL.append("&os=").append("WIN");

		} else if (osName.indexOf("mac") >= 0) {
			strURL.append("&os=").append("MAC");
		}

		try {
			getAppletContext().showDocument(new URL(strURL.toString()));
		} catch (MalformedURLException e) {
			System.out
					.println("Error in serverURL, could not send response to server.");
		}
	}
}
