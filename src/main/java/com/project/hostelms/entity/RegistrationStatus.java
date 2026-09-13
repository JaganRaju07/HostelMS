package com.project.hostelms.entity;

public enum RegistrationStatus {
    REJECTED(0),
    PENDING(1),
    APPROVED(2);

    private final int code;

    RegistrationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
