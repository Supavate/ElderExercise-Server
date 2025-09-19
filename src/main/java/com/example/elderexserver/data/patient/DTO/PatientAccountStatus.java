package com.example.elderexserver.data.patient.DTO;

public class PatientAccountStatus {
    private final boolean enabled;
    private final boolean accountLocked;
    private final boolean accountExpired;
    private final boolean credentialsExpired;
    private final String statusReason;

    public PatientAccountStatus(boolean enabled, boolean accountLocked, boolean accountExpired, boolean credentialsExpired) {
        this.enabled = enabled;
        this.accountLocked = accountLocked;
        this.accountExpired = accountExpired;
        this.credentialsExpired = credentialsExpired;
        this.statusReason = determineStatusReason();
    }

    public PatientAccountStatus(boolean enabled, boolean accountLocked, boolean accountExpired, boolean credentialsExpired, String statusReason) {
        this.enabled = enabled;
        this.accountLocked = accountLocked;
        this.accountExpired = accountExpired;
        this.credentialsExpired = credentialsExpired;
        this.statusReason = statusReason;
    }

    private String determineStatusReason() {
        if (!enabled) return "Account is disabled";
        if (accountLocked) return "Account is locked";
        if (accountExpired) return "Account has expired";
        if (credentialsExpired) return "Password has expired";
        return "Account is active";
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public boolean isActive() {
        return enabled && !accountLocked && !accountExpired && !credentialsExpired;
    }
}