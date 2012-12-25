package no.rubynor.signatur.provider.bankid.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public final class SessionStore {

	private static final int PURGE_PENDING_SESSIONS_INTERVAL = 60000;

	private static final int PURGE_DELAY = 10000;

	private static final int SESSION_PENDING_TIMEOUT_SECONDS = 400;

	private static final Logger LOGGER = Logger.getLogger(SessionStore.class);

	private static Map<String, Session> sessions = new HashMap<String, Session>();

	public static Session createSession() {
		Session session = new Session();
		addSession(session);
		LOGGER.debug("Session added (sid=" + session.getSid() + ")");
		return session;
	}

	public static boolean isMsisdnActiveWithDifferentSid(String msisdn,
			String sid) {
		for (Session session : sessions.values()) {
			if (msisdn.equalsIgnoreCase(session.getMsisdn())
					&& session.getState().equals(
							SessionState.AUTHENTICATION_IN_PROGRESS)
					&& !session.getSid().equalsIgnoreCase(sid)) {
				LOGGER.debug("The session " + session.getSid()
						+ " is in progress, and uses the same number ("
						+ msisdn + ") as the new session (" + sid + ").");
				return true;
			}
		}
		return false;
	}

	private static void addSession(Session session) {
		if (sessions.containsKey(session.getSid())) {
			LOGGER.debug("Tried to add existing session (sid="
					+ session.getSid() + ").");
		} else {
			sessions.put(session.getSid(), session);
		}
	}

	public static Session getSession(String sid) {
		return sessions.get(sid);
	}

	public static void invalidate(String sid) {
		if (sessions.containsKey(sid)) {
			sessions.remove(sid);
		} else {
			LOGGER.debug("Tried to invalidate non-existing session (sid=" + sid
					+ ").");
		}

	}

	static {
		Timer timer = new Timer();

		TimerTask purgePendingSessionsTask = new TimerTask() {
			public void run() {
				purgePendingSessions();
			}
		};
		timer.schedule(purgePendingSessionsTask, PURGE_DELAY,
				PURGE_PENDING_SESSIONS_INTERVAL);
	}

	private SessionStore() {
		// to avoid initialization
	}

	private static void purgePendingSessions() {
		Calendar limit = new GregorianCalendar();
		limit.add(Calendar.SECOND, -SESSION_PENDING_TIMEOUT_SECONDS);

		List<String> sidsToRemove = new ArrayList<String>();
		for (Session session : sessions.values()) {
			if (session.getState().equals(
					SessionState.AUTHENTICATION_IN_PROGRESS)
					&& session.getCreated().before(limit)) {
				sidsToRemove.add(session.getSid());
			}
		}
		for (String sid : sidsToRemove) {
			LOGGER.debug("Session removed in purgePendingSessions() (sid="
					+ sid + ")");
			sessions.remove(sid);
		}
	}
}
