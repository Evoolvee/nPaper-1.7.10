package com.sathonay.npaper;

public enum ChatVisibility {
    FULL,
    SYSTEM,
    HIDDEN;


    private static final ChatVisibility[] CHAT_VISIBILITIES = new ChatVisibility[values().length];

    public static ChatVisibility getByIndex(int index) {
        return CHAT_VISIBILITIES[index];
    }

    static {
        for (ChatVisibility value : values()) CHAT_VISIBILITIES[value.ordinal()] = value;
    }
}
