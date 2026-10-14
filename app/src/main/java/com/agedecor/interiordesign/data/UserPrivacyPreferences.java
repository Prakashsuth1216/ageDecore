package com.agedecor.interiordesign.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPrivacyPreferences {
    private static final String PREF_NAME = "age_decor_privacy_controls";
    private static final String KEY_LOCAL_ONLY = "pref_local_only";
    private static final String KEY_ALLOW_ANALYTICS = "pref_allow_analytics";
    private static final String KEY_AUTO_DELETE = "pref_auto_delete";
    private static final String KEY_ENCRYPTED_VAULT = "pref_encrypted_vault";

    private final SharedPreferences prefs;

    public UserPrivacyPreferences(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLocalOnly() {
        // Defaults to true for complete user data privacy
        return prefs.getBoolean(KEY_LOCAL_ONLY, true);
    }

    public void setLocalOnly(boolean value) {
        prefs.edit().putBoolean(KEY_LOCAL_ONLY, value).apply();
    }

    public boolean isAnalyticsAllowed() {
        return prefs.getBoolean(KEY_ALLOW_ANALYTICS, false);
    }

    public void setAnalyticsAllowed(boolean value) {
        prefs.edit().putBoolean(KEY_ALLOW_ANALYTICS, value).apply();
    }

    public boolean isAutoDeleteEnabled() {
        return prefs.getBoolean(KEY_AUTO_DELETE, false);
    }

    public void setAutoDeleteEnabled(boolean value) {
        prefs.edit().putBoolean(KEY_AUTO_DELETE, value).apply();
    }

    public boolean isEncryptedVaultEnabled() {
        return prefs.getBoolean(KEY_ENCRYPTED_VAULT, true);
    }

    public void setEncryptedVaultEnabled(boolean value) {
        prefs.edit().putBoolean(KEY_ENCRYPTED_VAULT, value).apply();
    }

    public void clearAllPreferences() {
        prefs.edit().clear().apply();
    }
}
