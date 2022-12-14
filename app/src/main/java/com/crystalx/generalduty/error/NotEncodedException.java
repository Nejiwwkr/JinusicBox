package com.crystalx.generalduty.error;

import androidx.annotation.NonNull;

public class NotEncodedException extends Error{
    private String description;
    public NotEncodedException (String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
