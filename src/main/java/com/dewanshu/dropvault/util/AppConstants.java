package com.dewanshu.dropvault.util;

public class AppConstants {

    private AppConstants() {}

    public static final long GUEST_MAX_SIZE = 50L * 1024 * 1024;              // 50MB

    public static final long USER_MAX_SIZE = 200L * 1024 * 1024;               // 200MB

    public static final long USER_TOTAL_STORAGE = 2L * 1024 * 1024 * 1024;     // 2GB

    public static final int GUEST_EXPIRY_HOURS = 24;                           // 24 hours

    public static final int USER_EXPIRY_DAYS = 7;                              // 7 days

}