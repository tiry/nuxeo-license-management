package org.nuxeo.license.session;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.license.api.LicenseSessionManager;
import org.nuxeo.license.api.UserSessionValidity;

/**
 * This is a very simple implementation of the Session management system.
 * <p>
 * Storage is pure memory, so it's not cluster aware.
 * <p>
 * There is no limit in the number of current sessions, but each user has only
 * access to one session, all previous sessions created with the same login will
 * be blacklisted
 * 
 * @author <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
 * 
 */
public class InMemorySessionManager implements LicenseSessionManager {

    protected final static Log log = LogFactory.getLog(InMemorySessionManager.class);

    protected static CopyOnWriteArrayList<String> blackListedSessionIds = new CopyOnWriteArrayList<String>();

    // key = user, value = sessionid
    protected static ConcurrentHashMap<String, String> currentLicenseSession = new ConcurrentHashMap<String, String>();

    @Override
    public UserSessionValidity validateUserSession(NuxeoPrincipal principal,
            String sid) {

        if (blackListedSessionIds.contains(sid)) {
            log.debug("Session " + sid + " has been black listed");
            return UserSessionValidity.BlackListed;
        }

        String oldSid = currentLicenseSession.putIfAbsent(principal.getName(),
                sid);
        if (oldSid != null && !oldSid.equals(sid)) {
            log.debug("BlackListing session " + sid
                    + " because an other session from user "
                    + principal.getName() + " is active");
            blackListedSessionIds.add(oldSid);
        }

        return UserSessionValidity.AcquiredOk;
    }

    public void releaseSession(String sid) {
        Iterator<Map.Entry<String, String>> it = currentLicenseSession.entrySet().iterator();
        while (it.hasNext()) {
            if (sid.equals(it.next().getValue())) {
                it.remove();
                break;
            }
        }
    }

    public Set<String> getActiveLogins() {
        return currentLicenseSession.keySet();
    }
}
