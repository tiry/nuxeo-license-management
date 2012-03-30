package org.nuxeo.license.internal;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.license.api.LicenseSessionManager;
import org.nuxeo.license.api.LicensesValidity;
import org.nuxeo.license.api.UserSessionValidity;
import org.nuxeo.license.session.InMemorySessionManager;
import org.nuxeo.license.store.InMemoryLicenseStore;

public class DummyLicenseManager implements LicenseManager {

    protected static LicensesValidity valid = null;

    protected synchronized LicensesValidity computeValidity() {
        return LicensesValidity.AllLicensesOk;
    }

    protected final InMemoryLicenseStore store;

    protected final LicenseSessionManager sessionManager;

    public DummyLicenseManager() {

        Map<String, List<String>> licensesData = new HashMap<String, List<String>>();

        licensesData.put("toto", Arrays.asList(new String[] { "licenseA" }));
        licensesData.put("titi",
                Arrays.asList(new String[] { "licenseA", "licenseB" }));
        licensesData.put("tutu", Arrays.asList(new String[] { "licenseB" }));

        store = new InMemoryLicenseStore("DummyCLient", licensesData);

        sessionManager = new InMemorySessionManager();
    }

    @Override
    public synchronized LicensesValidity checkLicensesValidity(boolean refresh) {

        if (valid == null || refresh) {
            valid = computeValidity();
        }
        return valid;
    }

    @Override
    public String getCustomerName() {
        return store.getCustomerName();
    }

    @Override
    public Calendar getValidityDate() {
        return store.getValidityDate();
    }

    @Override
    public List<String> getLicenseTypes() {
        return store.getLicenseTypes();
    }

    @Override
    public int getDeclaredLicenseCount(String licenseType) {
        return store.getDeclaredLicenseCount(licenseType);
    }

    @Override
    public List<String> getLicensedUsers(String licenseType) {
        return store.getLicensedUsers(licenseType);
    }

    @Override
    public List<String> getLicensesForUser(String userName) {
        return store.getLicensesForUser(userName);
    }

    @Override
    public void releaseSession(String sid) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserSessionValidity validateUserSession(NuxeoPrincipal principal,
            String sessionId) {
        return sessionManager.validateUserSession(principal, sessionId);
    }

    @Override
    public Set<String> getActiveLogins() {
        return sessionManager.getActiveLogins();
    }

}
