package org.nuxeo.license.api;

public class LicenseValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public LicenseValidationException(LicensesValidity validityCheck,
            String message) {
        super(validityCheck.toString() + " : " + message);
    }
}
