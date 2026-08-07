package com.dewanshu.dropvault.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StorageResponse {

    private long usedStorage;
    private long totalStorage;
    private long remainingStorage;
}