package com.sathonay.npaper.utils;

import com.sathonay.npaper.ChatVisibility;
import net.minecraft.server.EnumChatVisibility;
import net.minecraft.server.EnumDifficulty;
import org.bukkit.Difficulty;

public class CraftEnum {
    public static EnumDifficulty getDifficulty(Difficulty difficulty) {
        return EnumDifficulty.getById(difficulty.ordinal());
    }

    public static Difficulty getDifficulty(EnumDifficulty difficulty) {
        return Difficulty.getByValue(difficulty.ordinal());
    }

    public static ChatVisibility getChatVisibility(EnumChatVisibility visibility) {
        return ChatVisibility.getByIndex(visibility.a());
    }
    public static EnumChatVisibility getChatVisibility(ChatVisibility visibility) {
        return EnumChatVisibility.a(visibility.ordinal());
    }
}
