package org.nuxeo.license.api;

public enum LicensesValidity {

    /**
     * All validation checks are ok
     */
    AllLicensesOk("ok"),

    /**
     * Unable to find a License
     */
    NoValidLicenseFound("noValidLicensFound"),

    /**
     * Part of the licenses are expired
     */
    LicensesExpired("expired"),

    /**
     * Some licenses can not be validated (unknown error)
     */
    LicensesNotValid("canNotValidate");

    private final String value;

    LicensesValidity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
