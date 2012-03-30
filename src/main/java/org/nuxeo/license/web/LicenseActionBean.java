package org.nuxeo.license.web;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.runtime.api.Framework;

@Name("licenseAction")
@Scope(ScopeType.PAGE)
public class LicenseActionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public LicenseManager getLicenseManager() {
        return Framework.getLocalService(LicenseManager.class);
    }

    public String getCustomerName() {
        return getLicenseManager().getCustomerName();
    }
}
