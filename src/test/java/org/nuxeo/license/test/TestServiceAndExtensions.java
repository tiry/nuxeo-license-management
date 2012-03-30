package org.nuxeo.license.test;

import java.util.List;

import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.NXRuntimeTestCase;

public class TestServiceAndExtensions extends NXRuntimeTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        deployContrib("org.nuxeo.license", "OSGI-INF/license-framework.xml");
    }

    public void testServiceLookup() throws Exception {
        LicenseManager lm = Framework.getService(LicenseManager.class);
        assertNotNull(lm);
    }

    public void testServiceContrib() throws Exception {
        // check default contrib
        LicenseManager lm = Framework.getService(LicenseManager.class);
        assertNotNull(lm);
        List<String> licenses = lm.getLicensesForUser("toto");
        assertEquals(1, licenses.size());
        assertEquals("licenseA", licenses.get(0));

        // no deploy a non overridable contrib
        deployContrib("org.nuxeo.license.tests",
                "OSGI-INF/licensemanager-contrib.xml");
        lm = Framework.getService(LicenseManager.class);
        assertNotNull(lm);
        licenses = lm.getLicensesForUser("toto");
        assertEquals(2, licenses.size());
        assertEquals("licenseC", licenses.get(0));
        assertEquals("licenseD", licenses.get(1));

        // check that override is not permited
        deployContrib("org.nuxeo.license.tests",
                "OSGI-INF/licensemanager-contrib2.xml");

        // cheeck that LM has not been changed
        licenses = lm.getLicensesForUser("toto");
        assertEquals(2, licenses.size());
        assertEquals("licenseC", licenses.get(0));
        assertEquals("licenseD", licenses.get(1));
    }

}
