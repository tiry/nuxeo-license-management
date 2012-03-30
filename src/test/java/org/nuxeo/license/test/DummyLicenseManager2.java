package org.nuxeo.license.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nuxeo.license.internal.DummyLicenseManager;

public class DummyLicenseManager2 extends DummyLicenseManager {

    public DummyLicenseManager2() {
        super();
        store.getStore().clear();
        Map<String, List<String>> licensesData = new HashMap<String, List<String>>();
        licensesData.put("toto",
                Arrays.asList(new String[] { "licenseC", "licenseD" }));
        licensesData.put("titi",
                Arrays.asList(new String[] { "licenseC", "licenseD" }));
        licensesData.put("tutu", Arrays.asList(new String[] { "licenseD" }));
        store.getStore().putAll(licensesData);
    }
}
