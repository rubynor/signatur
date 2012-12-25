package no.rubynor.signatur.provider.bankid.session;

import java.util.Calendar;
import java.util.GregorianCalendar;

import no.bbs.server.vos.BIDSessionData;
import no.bbs.server.vos.CertificateInfo;

import org.apache.log4j.Logger;

public class Session {

	private static final Logger LOGGER = Logger.getLogger(Session.class);

	private final String sid;
	private String transactionReference;
	private String merchantReference;
	private SessionState state = SessionState.NOT_AUTHENTICATED;
	private String msisdn; // phone number (without country code)
	private String alias; // alias for mobile bankid

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	private String action;
	private String signType;
	private boolean mobileSign;
	private String errorCode;
	private String errorStack;
	private BIDSessionData bidSessionData;
	private String user;
	private CertificateInfo certificateInfo;
	private Calendar created = new GregorianCalendar();

	Session() {
		// this constructor is not public, as only SessionStore should create
		// sessions

		// Generate custom sessionID
		sid = java.util.UUID.randomUUID().toString();
	}

	Session(String msisdn) {
		// this constructor is not public, as only SessionStore should create
		// sessions

		// Generate custom sessionID
		sid = java.util.UUID.randomUUID().toString();
		this.msisdn = msisdn;
	}

	public String getSid() {
		return sid;
	}

	public void setState(SessionState state) {
		this.state = state;
		LOGGER.debug("Session " + sid + " set to state " + state);
	}

	public SessionState getState() {
		return state;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMerchantReference() {
		return merchantReference;
	}

	public void setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public Calendar getCreated() {
		return created;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public boolean isMobileSign() {
		return mobileSign;
	}

	public void setMobileSign(boolean mobileSign) {
		this.mobileSign = mobileSign;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorStack() {
		return errorStack;
	}

	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}

	public BIDSessionData getBidSessionData() {
		return bidSessionData;
	}

	public void setBidSessionData(BIDSessionData bidSessionData) {
		this.bidSessionData = bidSessionData;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public CertificateInfo getCertificateInfo() {
		return certificateInfo;
	}

	public void setCertificateInfo(CertificateInfo certificateInfo) {
		this.certificateInfo = certificateInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Session [created=");
		builder.append(created.getTimeInMillis());
		builder.append(", merchantReference=");
		builder.append(merchantReference);
		builder.append(", msisdn=");
		builder.append(msisdn);
		builder.append(", sid=");
		builder.append(sid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", transactionReference=");
		builder.append(transactionReference);
		builder.append("]");
		return builder.toString();
	}
}
