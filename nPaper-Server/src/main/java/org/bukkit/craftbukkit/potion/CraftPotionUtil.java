package org.bukkit.craftbukkit.potion;

import net.minecraft.server.MobEffect;
import net.minecraft.server.MobEffectList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftPotionUtil {

    public static MobEffect fromBukkit(PotionEffect effect) {
        return new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient());
    }

    public static PotionEffect toBukkit(MobEffect effect) {
        PotionEffectType type = PotionEffectType.getById(effect.getEffectId());
        int amp = effect.getAmplifier();
        int duration = effect.getDuration();
        boolean ambient = effect.isAmbient();
        return new PotionEffect(type, duration, amp, ambient);
    }
}
