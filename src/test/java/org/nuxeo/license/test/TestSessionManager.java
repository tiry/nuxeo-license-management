package org.nuxeo.license.test;

import java.util.ArrayList;

import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.api.impl.UserPrincipal;
import org.nuxeo.license.api.UserSessionValidity;
import org.nuxeo.license.session.InMemorySessionManager;
import org.nuxeo.runtime.test.NXRuntimeTestCase;

public class TestSessionManager extends NXRuntimeTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        deployBundle("org.nuxeo.license");
    }

    public void testSessionManagement() throws Exception {
        InMemorySessionManager sm = new InMemorySessionManager();

        NuxeoPrincipal principal = new UserPrincipal("toto",
                new ArrayList<String>(), false, false);

        // check acquire
        UserSessionValidity validity = sm.validateUserSession(principal, "1");
        assertEquals(UserSessionValidity.AcquiredOk, validity);

        // check that we can continue on the same session
        validity = sm.validateUserSession(principal, "1");
        assertEquals(UserSessionValidity.AcquiredOk, validity);

        // check that I can acquire on an other session
        validity = sm.validateUserSession(principal, "2");
        assertEquals(UserSessionValidity.AcquiredOk, validity);
        // check that first session is now blacklisted
        validity = sm.validateUserSession(principal, "1");
        assertEquals(UserSessionValidity.BlackListed, validity);

    }
}
