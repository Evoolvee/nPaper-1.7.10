package net.minecraft.server;

import org.github.paperspigot.PaperSpigotConfig;

public class ItemEnderPearl extends Item {
    public ItemEnderPearl() {
        this.maxStackSize = 16;
        this.a(CreativeModeTab.f);
    }

    public ItemStack a(ItemStack var1, World var2, EntityHuman var3) {
        //nPaper start - allow ender pearls to be thrown by players in creative mode
        if (!PaperSpigotConfig.enderPearlCreativeThrow && var3.abilities.canInstantlyBuild) {
            return var1;
        } else {
            if (!PaperSpigotConfig.enderPearlCreativeThrow || !var3.abilities.canInstantlyBuild) --var1.count; // nPaper remove ender pearl if the player can't instantly build
            var2.makeSound(var3, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
            if (!var2.isStatic) {
                var2.addEntity(new EntityEnderPearl(var2, var3));
            }

            return var1;
        }
        //nPaper end
    }
}