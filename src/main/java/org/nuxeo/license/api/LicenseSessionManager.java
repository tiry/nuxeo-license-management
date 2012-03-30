package org.nuxeo.license.api;

import java.util.Set;

import org.nuxeo.ecm.core.api.NuxeoPrincipal;

public interface LicenseSessionManager {

    void releaseSession(String sid);

    UserSessionValidity validateUserSession(NuxeoPrincipal principal,
            String sessionId);

    Set<String> getActiveLogins();

}
