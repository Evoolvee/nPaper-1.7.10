package com.sathonay.npaper.utils;

public interface StatefulRandomness extends RandomnessSource {
    long getState();

    void setState(final long p0);
}