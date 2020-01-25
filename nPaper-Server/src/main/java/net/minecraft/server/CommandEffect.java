package net.minecraft.server;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandEffect extends CommandAbstract {
    public CommandEffect() {
    }

    public String getCommand() {
        return "effect";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandListener var1) {
        return "commands.effect.usage";
    }

    public void execute(ICommandListener var1, String[] var2) {
        if (var2.length < 2) {
            throw new ExceptionUsage("commands.effect.usage", new Object[0]);
        } else {
            EntityPlayer var3 = d(var1, var2[0]);
            if (var2[1].equals("clear")) {
                if (var3.getEffects().isEmpty()) {
                    throw new CommandException("commands.effect.failure.notActive.all", new Object[]{var3.getName()});
                }

                var3.removeAllEffects(EntityPotionEffectEvent.Cause.COMMAND);
                a(var1, this, "commands.effect.success.removed.all", new Object[]{var3.getName()});
            } else {
                int var4 = a(var1, var2[1], 1);
                int var5 = 600;
                int var6 = 30;
                int var7 = 0;
                if (var4 < 0 || var4 >= MobEffectList.byId.length || MobEffectList.byId[var4] == null) {
                    throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[]{var4});
                }

                if (var2.length >= 3) {
                    var6 = a(var1, var2[2], 0, 1000000);
                    if (MobEffectList.byId[var4].isInstant()) {
                        var5 = var6;
                    } else {
                        var5 = var6 * 20;
                    }
                } else if (MobEffectList.byId[var4].isInstant()) {
                    var5 = 1;
                }

                if (var2.length >= 4) {
                    var7 = a(var1, var2[3], 0, 255);
                }

                if (var6 == 0) {
                    if (!var3.hasEffect(var4)) {
                        throw new CommandException("commands.effect.failure.notActive", new Object[]{new ChatMessage(MobEffectList.byId[var4].a(), new Object[0]), var3.getName()});
                    }

                    var3.removeEffect(var4, EntityPotionEffectEvent.Cause.COMMAND);
                    a(var1, this, "commands.effect.success.removed", new Object[]{new ChatMessage(MobEffectList.byId[var4].a(), new Object[0]), var3.getName()});
                } else {
                    MobEffect var8 = new MobEffect(var4, var5, var7);
                    var3.addEffect(var8, EntityPotionEffectEvent.Cause.COMMAND);
                    a(var1, this, "commands.effect.success", new Object[]{new ChatMessage(var8.f(), new Object[0]), var4, var7, var3.getName(), var6});
                }
            }

        }
    }

    public List tabComplete(ICommandListener var1, String[] var2) {
        return var2.length == 1 ? a(var2, this.d()) : null;
    }

    protected String[] d() {
        return MinecraftServer.getServer().getPlayers();
    }

    public boolean isListStart(String[] var1, int var2) {
        return var2 == 0;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return a();
    }
}
