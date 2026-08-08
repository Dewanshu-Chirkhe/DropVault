package com.dewanshu.dropvault.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {

    private final long guestMaxSize;
    private final long userMaxSize;
    private final long userTotalStorage;
    private final int guestExpiryHours;
    private final int userExpiryDays;

    public StorageProperties(
            long guestMaxSize,
            long userMaxSize,
            long userTotalStorage,
            int guestExpiryHours,
            int userExpiryDays
    ) {
        this.guestMaxSize = guestMaxSize;
        this.userMaxSize = userMaxSize;
        this.userTotalStorage = userTotalStorage;
        this.guestExpiryHours = guestExpiryHours;
        this.userExpiryDays = userExpiryDays;
    }
}