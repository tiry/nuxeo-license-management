package org.nuxeo.license.api;

public enum UserSessionValidity {

    AcquiredOk("ok"),

    BlackListed("blackListed"),

    NoMoreSessions("noMoreSessions");

    private final String value;

    UserSessionValidity(String value) {
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
