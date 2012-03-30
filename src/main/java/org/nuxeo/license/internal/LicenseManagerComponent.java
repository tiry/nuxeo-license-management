package org.nuxeo.license.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.management.api.AdministrativeStatusManager;
import org.nuxeo.ecm.platform.web.common.admin.AdministrativeStatusListener;
import org.nuxeo.license.api.LicensesValidity;
import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.license.api.LicenseManagerDescriptor;
import org.nuxeo.license.api.LicenseValidationException;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

public class LicenseManagerComponent extends DefaultComponent {

    protected static final Log log = LogFactory.getLog(LicenseManagerComponent.class);

    public static final String License_MANAGER_EP = "licenseManagerImpl";

    protected LicenseManagerDescriptor currentDescriptor = null;

    protected LicenseManager licenseManager = null;

    protected LicenseManager getLicenseManager() {
        if (licenseManager == null) {
            // fall back to default dummy implementation ...
            licenseManager = new DummyLicenseManager();
        }
        return licenseManager;
    }

    @Override
    public void applicationStarted(ComponentContext context) throws Exception {
        // init License manager
        log.warn("Starting license manager");
        // do explicit validation of the
        LicensesValidity valid = getLicenseManager().checkLicensesValidity(true);
        if (valid != LicensesValidity.AllLicensesOk) {
            // XXX check expected behavior on the provider
            throw new LicenseValidationException(valid,
                    "can not validate licenses at server startup");
        }
        super.applicationStarted(context);
    }

    @Override
    public void registerContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor)
            throws Exception {
        if (License_MANAGER_EP.equals(extensionPoint)) {
            if (currentDescriptor != null) {
                if (!currentDescriptor.isOverridable()) {
                    log.warn("You can not override LicenseManager "
                            + currentDescriptor.getName());
                    return;
                }
            }
            LicenseManagerDescriptor descriptor = (LicenseManagerDescriptor) contribution;
            if (descriptor.getLicenseManagerClass() != null) {
                licenseManager = descriptor.getLicenseManagerClass().newInstance();
                currentDescriptor = descriptor;
            } else {
                log.error("Can not register LicenseManager without a class!");
            }
        } else {
            log.warn("Unhandled extension point " + extensionPoint);
        }
    }

    @Override
    public void unregisterContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor)
            throws Exception {
        if (License_MANAGER_EP.equals(extensionPoint)) {
            log.warn("Unregistring LicenseManager is not supported");
        } else {
            log.warn("Unhandled extension point " + extensionPoint);
        }
    }

    protected void displayExpirationMessage() {
        AdministrativeStatusManager asm = Framework.getLocalService(AdministrativeStatusManager.class);
        if (asm != null) {
            asm.setStatus(AdministrativeStatusListener.ADM_MESSAGE_SERVICE,
                    "active", "Licenses are about to expire", "system");
        }
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        if (adapter.getCanonicalName().equals(
                LicenseManager.class.getCanonicalName())) {
            // delegate Service impl
            return adapter.cast(getLicenseManager());
        }
        return super.getAdapter(adapter);
    }

}
