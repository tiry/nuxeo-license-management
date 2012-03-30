package org.nuxeo.license.api;

public interface LicenseManager extends LicensesStore, LicenseSessionManager {

    LicensesValidity checkLicensesValidity(boolean refresh);

}
