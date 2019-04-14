package com.los.revotask.util;

public enum EventType {

    TRANSFER_SOURCE("TS"), TRANSFER_DESTINATION("TD"), TOP_UP("TU"), WITHDRAW("WD");

    private String value;

    public String getValue() {
        return value;
    }

    private EventType(String value) {
        this.value = value;
    }
}
