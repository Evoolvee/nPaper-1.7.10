package net.minecraft.server;

import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemPotion extends Item {
    private HashMap a = new HashMap();
    private static final Map b = new LinkedHashMap();

    public ItemPotion() {
        this.e(1);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.k);
    }

    public List g(ItemStack var1) {
        if (var1.hasTag() && var1.getTag().hasKeyOfType("CustomPotionEffects", 9)) {
            ArrayList var7 = new ArrayList();
            NBTTagList var3 = var1.getTag().getList("CustomPotionEffects", 10);

            for(int var4 = 0; var4 < var3.size(); ++var4) {
                NBTTagCompound var5 = var3.get(var4);
                MobEffect var6 = MobEffect.b(var5);
                if (var6 != null) {
                    var7.add(var6);
                }
            }

            return var7;
        } else {
            List var2 = (List)this.a.get(var1.getData());
            if (var2 == null) {
                var2 = PotionBrewer.getEffects(var1.getData(), false);
                this.a.put(var1.getData(), var2);
            }

            return var2;
        }
    }

    public List c(int var1) {
        List var2 = (List)this.a.get(var1);
        if (var2 == null) {
            var2 = PotionBrewer.getEffects(var1, false);
            this.a.put(var1, var2);
        }

        return var2;
    }

    public ItemStack b(ItemStack var1, World var2, EntityHuman var3) {
        if (!var3.abilities.canInstantlyBuild) {
            --var1.count;
        }

        if (!var2.isStatic) {
            List var4 = this.g(var1);
            if (var4 != null) {
                Iterator var5 = var4.iterator();

                while(var5.hasNext()) {
                    MobEffect var6 = (MobEffect)var5.next();
                    var3.addEffect(new MobEffect(var6), EntityPotionEffectEvent.Cause.POTION_DRINK);
                }
            }
        }

        if (!var3.abilities.canInstantlyBuild) {
            if (var1.count <= 0) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            var3.inventory.pickup(new ItemStack(Items.GLASS_BOTTLE));
        }

        return var1;
    }

    public int d_(ItemStack var1) {
        return 32;
    }

    public EnumAnimation d(ItemStack var1) {
        return EnumAnimation.DRINK;
    }

    public ItemStack a(ItemStack var1, World var2, EntityHuman var3) {
        if (g(var1.getData())) {
            if (!var3.abilities.canInstantlyBuild) {
                --var1.count;
            }

            var2.makeSound(var3, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
            if (!var2.isStatic) {
                var2.addEntity(new EntityPotion(var2, var3, var1));
            }

            return var1;
        } else {
            var3.a(var1, this.d_(var1));
            return var1;
        }
    }

    public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10) {
        return false;
    }

    public static boolean g(int var0) {
        return (var0 & 16384) != 0;
    }

    public String n(ItemStack var1) {
        if (var1.getData() == 0) {
            return LocaleI18n.get("item.emptyPotion.name").trim();
        } else {
            String var2 = "";
            if (g(var1.getData())) {
                var2 = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
            }

            List var3 = Items.POTION.g(var1);
            String var4;
            if (var3 != null && !var3.isEmpty()) {
                var4 = ((MobEffect)var3.get(0)).f();
                var4 = var4 + ".postfix";
                return var2 + LocaleI18n.get(var4).trim();
            } else {
                var4 = PotionBrewer.c(var1.getData());
                return LocaleI18n.get(var4).trim() + " " + super.n(var1);
            }
        }
    }
}
