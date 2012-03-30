package org.nuxeo.license.api;

import java.util.Calendar;
import java.util.List;

public interface LicensesStore {

    String getCustomerName();

    Calendar getValidityDate();

    List<String> getLicenseTypes();

    int getDeclaredLicenseCount(String licenseType);

    List<String> getLicensedUsers(String licenseType);

    List<String> getLicensesForUser(String userName);

}
