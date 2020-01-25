package net.minecraft.server;

import org.bukkit.event.entity.EntityPotionEffectEvent;

public class EntityCaveSpider extends EntitySpider {
    public EntityCaveSpider(World var1) {
        super(var1);
        this.a(0.7F, 0.5F);
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(12.0D);
    }

    public boolean n(Entity var1) {
        if (super.n(var1)) {
            if (var1 instanceof EntityLiving) {
                byte var2 = 0;
                if (this.world.difficulty == EnumDifficulty.NORMAL) {
                    var2 = 7;
                } else if (this.world.difficulty == EnumDifficulty.HARD) {
                    var2 = 15;
                }

                if (var2 > 0) {
                    ((EntityLiving)var1).addEffect(new MobEffect(MobEffectList.POISON.id, var2 * 20, 0), EntityPotionEffectEvent.Cause.ATTACK);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public GroupDataEntity prepare(GroupDataEntity var1) {
        return var1;
    }
}
