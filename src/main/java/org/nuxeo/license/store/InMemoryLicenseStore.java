package org.nuxeo.license.store;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.nuxeo.license.api.LicensesStore;

/**
 * Very basic implementation of the {@link LicensesStore} interface.
 * <p>
 * LicenseStore is represented by a simple Map.
 * 
 * @author <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
 * 
 */
public class InMemoryLicenseStore implements LicensesStore {

    protected final List<String> licenseTypes;

    protected final String customerName;

    // UserName => [LicenseA, LicenseC]
    protected final Map<String, List<String>> store;

    public InMemoryLicenseStore(String customerName,
            Map<String, List<String>> store) {
        this.customerName = customerName;
        this.store = store;
        this.licenseTypes = new ArrayList<String>();
        for (String user : store.keySet()) {
            for (String license : store.get(user)) {
                if (!licenseTypes.contains(license)) {
                    licenseTypes.add(license);
                }
            }
        }
    }

    public Map<String, List<String>> getStore() {
        return store;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public Calendar getValidityDate() {
        Calendar date = Calendar.getInstance();
        date.add(10, Calendar.DAY_OF_YEAR);
        return date;
    }

    @Override
    public int getDeclaredLicenseCount(String licenseType) {
        // not handled in this implementation
        return 0;
    }

    @Override
    public List<String> getLicenseTypes() {
        return licenseTypes;
    }

    @Override
    public List<String> getLicensedUsers(String licenseType) {
        List<String> users = new ArrayList<String>();
        for (String user : store.keySet()) {
            if (store.get(user).contains(licenseType)) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public List<String> getLicensesForUser(String userName) {
        if (store.containsKey(userName)) {
            return store.get(userName);
        } else {
            return new ArrayList<String>();
        }
    }

}
