package com.dewanshu.dropvault.util;

public class AppConstants {

    private AppConstants() {}

    public static final long GUEST_MAX_SIZE = 50L * 1024 * 1024;      // 50 MB

    public static final long USER_MAX_SIZE = 200L * 1024 * 1024;      // 200 MB

    public static final int GUEST_EXPIRY_HOURS = 12;                  // 12 hours

    public static final int USER_EXPIRY_DAYS = 3;                     // 3 days

}