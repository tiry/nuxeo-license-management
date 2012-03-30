package org.nuxeo.license.api;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("licenseManager")
public class LicenseManagerDescriptor {

    @XNode("@name")
    private String name;

    @XNode("@class")
    protected Class<? extends LicenseManager> klass;

    @XNode("@overridable")
    private boolean overridable = false;

    public String getName() {
        return name;
    }

    public Class<? extends LicenseManager> getLicenseManagerClass() {
        return klass;
    }

    public boolean isOverridable() {
        return overridable;
    }
}
