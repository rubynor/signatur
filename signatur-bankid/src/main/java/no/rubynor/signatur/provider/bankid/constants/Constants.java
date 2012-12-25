package no.rubynor.signatur.provider.bankid.constants;

public final class Constants {

	/**
	 * This is the full path to this webapp, as seen from the BBS infrastructure
	 * (for example: do not use localhost)
	 */
	private static final String SERVER_PATH = "https://noknudah-laptop:8443";
	
	/**
	 * The is the name of the merchant. This must corresponds to the props file.
	 * example_merchant requires a props file named example_merchant.props
	 */
	public static final String MERCHANT_NAME = "example_merchant";

	/**
	 * This is the password BIDJServer needs to open the .bid file
	 */
	public static final String BID_PASSWORD = "qwer1234";

	/**
	 * The is the name of configuration folder, which must contain the props
	 * file MERCHANT_NAME.props
	 */
	public static final String PROPERTIES_FOLDER = "src/main/resources";

	public static final String FAILURE_URL = SERVER_PATH + "/failure.jsp";
	public static final String INDEX_URL = SERVER_PATH + "/";
	public static final String INFO_URL = SERVER_PATH + "/info";
	public static final String SIGN_URL = SERVER_PATH + "/sign";
	public static final String AUTH_URL = SERVER_PATH + "/auth";
	
	public static final String MERCHANT_REFERENCE_LOCALE = "no_NO";

	private Constants() {
		// avoid external initialization
	}
}
