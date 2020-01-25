package net.minecraft.server;

import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntityWitch extends EntityMonster implements IRangedEntity {
    private static final UUID bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier bq;
    private static final Item[] br;
    private int bs;

    public EntityWitch(World var1) {
        super(var1);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 60, 10.0F));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
    }

    protected void c() {
        super.c();
        this.getDataWatcher().a(21, (byte)0);
    }

    protected String t() {
        return "mob.witch.idle";
    }

    protected String aT() {
        return "mob.witch.hurt";
    }

    protected String aU() {
        return "mob.witch.death";
    }

    public void a(boolean var1) {
        this.getDataWatcher().watch(21, Byte.valueOf((byte)(var1 ? 1 : 0)));
    }

    public boolean bZ() {
        return this.getDataWatcher().getByte(21) == 1;
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(26.0D);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.25D);
    }

    public boolean bk() {
        return true;
    }

    public void e() {
        if (!this.world.isStatic) {
            if (this.bZ()) {
                if (this.bs-- <= 0) {
                    this.a(false);
                    ItemStack var6 = this.be();
                    this.setEquipment(0, (ItemStack)null);
                    if (var6 != null && var6.getItem() == Items.POTION) {
                        List var5 = Items.POTION.g(var6);
                        if (var5 != null) {
                            Iterator var3 = var5.iterator();

                            while(var3.hasNext()) {
                                MobEffect var4 = (MobEffect)var3.next();
                                this.addEffect(new MobEffect(var4), EntityPotionEffectEvent.Cause.ATTACK);
                            }
                        }
                    }

                    this.getAttributeInstance(GenericAttributes.d).b(bq);
                }
            } else {
                short var1 = -1;
                if (this.random.nextFloat() < 0.15F && this.a(Material.WATER) && !this.hasEffect(MobEffectList.WATER_BREATHING)) {
                    var1 = 8237;
                } else if (this.random.nextFloat() < 0.15F && this.isBurning() && !this.hasEffect(MobEffectList.FIRE_RESISTANCE)) {
                    var1 = 16307;
                } else if (this.random.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
                    var1 = 16341;
                } else if (this.random.nextFloat() < 0.25F && this.getGoalTarget() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.getGoalTarget().f(this) > 121.0D) {
                    var1 = 16274;
                } else if (this.random.nextFloat() < 0.25F && this.getGoalTarget() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.getGoalTarget().f(this) > 121.0D) {
                    var1 = 16274;
                }

                if (var1 > -1) {
                    this.setEquipment(0, new ItemStack(Items.POTION, 1, var1));
                    this.bs = this.be().n();
                    this.a(true);
                    AttributeInstance var2 = this.getAttributeInstance(GenericAttributes.d);
                    var2.b(bq);
                    var2.a(bq);
                }
            }

            if (this.random.nextFloat() < 7.5E-4F) {
                this.world.broadcastEntityEffect(this, (byte)15);
            }
        }

        super.e();
    }

    protected float applyMagicModifier(DamageSource var1, float var2) {
        var2 = super.applyMagicModifier(var1, var2);
        if (var1.getEntity() == this) {
            var2 = 0.0F;
        }

        if (var1.isMagic()) {
            var2 = (float)((double)var2 * 0.15D);
        }

        return var2;
    }

    protected void dropDeathLoot(boolean var1, int var2) {
        int var3 = this.random.nextInt(3) + 1;

        for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = this.random.nextInt(3);
            Item var6 = br[this.random.nextInt(br.length)];
            if (var2 > 0) {
                var5 += this.random.nextInt(var2 + 1);
            }

            for(int var7 = 0; var7 < var5; ++var7) {
                this.a(var6, 1);
            }
        }

    }

    public void a(EntityLiving var1, float var2) {
        if (!this.bZ()) {
            EntityPotion var3 = new EntityPotion(this.world, this, 32732);
            var3.pitch -= -20.0F;
            double var4 = var1.locX + var1.motX - this.locX;
            double var6 = var1.locY + (double)var1.getHeadHeight() - 1.100000023841858D - this.locY;
            double var8 = var1.locZ + var1.motZ - this.locZ;
            float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8);
            if (var10 >= 8.0F && !var1.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
                var3.setPotionValue(32698);
            } else if (var1.getHealth() >= 8.0F && !var1.hasEffect(MobEffectList.POISON)) {
                var3.setPotionValue(32660);
            } else if (var10 <= 3.0F && !var1.hasEffect(MobEffectList.WEAKNESS) && this.random.nextFloat() < 0.25F) {
                var3.setPotionValue(32696);
            }

            var3.shoot(var4, var6 + (double)(var10 * 0.2F), var8, 0.75F, 8.0F);
            this.world.addEntity(var3);
        }
    }

    static {
        bq = (new AttributeModifier(bp, "Drinking speed penalty", -0.25D, 0)).a(false);
        br = new Item[]{Items.GLOWSTONE_DUST, Items.SUGAR, Items.REDSTONE, Items.SPIDER_EYE, Items.GLASS_BOTTLE, Items.SULPHUR, Items.STICK, Items.STICK};
    }
}
