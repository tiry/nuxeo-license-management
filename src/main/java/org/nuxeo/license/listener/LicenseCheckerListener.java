package org.nuxeo.license.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.license.api.LicensesValidity;
import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.runtime.api.Framework;

public class LicenseCheckerListener implements EventListener {

    private static final Log log = LogFactory.getLog(LicenseCheckerListener.class);

    @Override
    public void handleEvent(Event event) throws ClientException {

        LicensesValidity licensesValid = Framework.getLocalService(
                LicenseManager.class).checkLicensesValidity(true);
        log.warn("Checking license status");
        if (licensesValid != LicensesValidity.AllLicensesOk) {
            log.error("Licenses are no longer valid : "
                    + licensesValid.toString());
        }
    }

}
