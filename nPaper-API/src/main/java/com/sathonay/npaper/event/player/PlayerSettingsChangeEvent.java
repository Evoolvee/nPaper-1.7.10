package com.sathonay.npaper.event.player;

import com.sathonay.npaper.ChatVisibility;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSettingsChangeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final String lang; // Both
    private final int viewDistance; // Both
    private final ChatVisibility chatVisibility; // Both
    private final boolean enableColors; // Both
    private final Difficulty difficulty; // 1.7.X
    private final boolean showCape; // 1.7.X
    private final int modelPartFlags; // 1.8.X

    public PlayerSettingsChangeEvent(Player who, String lang, int viewDistance, ChatVisibility chatVisibility, boolean enableColors, Difficulty difficulty, boolean showCape, int modelPartFlags) {
        super(who);
        this.lang = lang;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.enableColors = enableColors;
        this.difficulty = difficulty;
        this.showCape = showCape;
        this.modelPartFlags = modelPartFlags;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getLang() {
        return lang;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public ChatVisibility getChatVisibility() {
        return chatVisibility;
    }

    public boolean isEnableColors() {
        return enableColors;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean isShowCape() {
        return showCape;
    }

    public int getModelPartFlags() {
        return modelPartFlags;
    }
}
