package no.rubynor.signatur.provider.bankid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import no.rubynor.signatur.provider.bankid.util.ServletUtil;


public class BIDRequest {

	private String sid;
	private String encKey;
	private String encData;
	private String encAuth;
	private String operation;

	public BIDRequest(HttpServletRequest request) throws ServletException {
		sid = ServletUtil.getAndValidateSessionID(request);
		encKey = request.getParameter("encKey");
		encData = request.getParameter("encData");
		encAuth = request.getParameter("encAuth");
		operation = ServletUtil.getAndValidateOperationParameter(request);
	}

	public String getSid() {
		return sid;
	}

	public String getEncKey() {
		return encKey;
	}

	public String getEncData() {
		return encData;
	}

	public String getEncAuth() {
		return encAuth;
	}

	public String getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BIDRequest [encAuth=");
		builder.append(encAuth);
		builder.append(", encData=");
		builder.append(encData);
		builder.append(", encKey=");
		builder.append(encKey);
		builder.append(", operation=");
		builder.append(operation);
		builder.append(", sid=");
		builder.append(sid);
		builder.append("]");
		return builder.toString();
	}
}
