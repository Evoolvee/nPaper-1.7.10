package net.minecraft.server;

import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class EntityVillager extends EntityAgeable implements IMerchant, NPC {
    private int profession;
    private boolean br;
    private boolean bs;
    Village village;
    private EntityHuman tradingPlayer;
    private MerchantRecipeList bu;
    private int bv;
    private boolean bw;
    private int riches;
    private String by;
    private boolean bz;
    private float bA;
    private static final Map bB = new HashMap();
    private static final Map bC = new HashMap();

    public EntityVillager(World var1) {
        this(var1, 0);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return b(entityageable);
    }

    public EntityVillager(World var1, int var2) {
        super(var1);
        this.setProfession(var2);
        this.a(0.6F, 1.8F);
        this.getNavigation().b(true);
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalAvoidPlayer(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
        this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
        this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
        this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
        this.goalSelector.a(6, new PathfinderGoalMakeLove(this));
        this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));
        this.goalSelector.a(8, new PathfinderGoalPlay(this, 0.32D));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityVillager.class, 5.0F, 0.02F));
        this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.d).setValue(0.5D);
    }

    public boolean bk() {
        return true;
    }

    protected void bp() {
        if (--this.profession <= 0) {
            this.world.villages.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
            this.profession = 70 + this.random.nextInt(50);
            this.village = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
            if (this.village == null) {
                this.bX();
            } else {
                ChunkCoordinates var1 = this.village.getCenter();
                this.a(var1.x, var1.y, var1.z, (int)((float)this.village.getSize() * 0.6F));
                if (this.bz) {
                    this.bz = false;
                    this.village.b(5);
                }
            }
        }

        if (!this.cc() && this.bv > 0) {
            --this.bv;
            if (this.bv <= 0) {
                if (this.bw) {
                    if (this.bu.size() > 1) {
                        Iterator var3 = this.bu.iterator();

                        while(var3.hasNext()) {
                            MerchantRecipe var2 = (MerchantRecipe)var3.next();
                            if (var2.g()) {
                                var2.a(this.random.nextInt(6) + this.random.nextInt(6) + 2);
                            }
                        }
                    }

                    this.t(1);
                    this.bw = false;
                    if (this.village != null && this.by != null) {
                        this.world.broadcastEntityEffect(this, (byte)14);
                        this.village.a(this.by, 1);
                    }
                }

                this.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 200, 0), EntityPotionEffectEvent.Cause.VILLAGER_TRADE);
            }
        }

        super.bp();
    }

    public boolean a(EntityHuman var1) {
        ItemStack var2 = var1.inventory.getItemInHand();
        boolean var3 = var2 != null && var2.getItem() == Items.MONSTER_EGG;
        if (!var3 && this.isAlive() && !this.cc() && !this.isBaby()) {
            if (!this.world.isStatic) {
                this.a_(var1);
                var1.openTrade(this, this.getCustomName());
            }

            return true;
        } else {
            return super.a(var1);
        }
    }

    protected void c() {
        super.c();
        this.datawatcher.a(16, 0);
    }

    public void b(NBTTagCompound var1) {
        super.b(var1);
        var1.setInt("Profession", this.getProfession());
        var1.setInt("Riches", this.riches);
        if (this.bu != null) {
            var1.set("Offers", this.bu.a());
        }

    }

    public void a(NBTTagCompound var1) {
        super.a(var1);
        this.setProfession(var1.getInt("Profession"));
        this.riches = var1.getInt("Riches");
        if (var1.hasKeyOfType("Offers", 10)) {
            NBTTagCompound var2 = var1.getCompound("Offers");
            this.bu = new MerchantRecipeList(var2);
        }

    }

    protected boolean isTypeNotPersistent() {
        return false;
    }

    protected String t() {
        return this.cc() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    protected String aT() {
        return "mob.villager.hit";
    }

    protected String aU() {
        return "mob.villager.death";
    }

    public void setProfession(int var1) {
        this.datawatcher.watch(16, var1);
    }

    public int getProfession() {
        return this.datawatcher.getInt(16);
    }

    public boolean ca() {
        return this.br;
    }

    public void i(boolean var1) {
        this.br = var1;
    }

    public void j(boolean var1) {
        this.bs = var1;
    }

    public boolean cb() {
        return this.bs;
    }

    public void b(EntityLiving var1) {
        super.b(var1);
        if (this.village != null && var1 != null) {
            this.village.a(var1);
            if (var1 instanceof EntityHuman) {
                byte var2 = -1;
                if (this.isBaby()) {
                    var2 = -3;
                }

                this.village.a(var1.getName(), var2);
                if (this.isAlive()) {
                    this.world.broadcastEntityEffect(this, (byte)13);
                }
            }
        }

    }

    public void die(DamageSource var1) {
        if (this.village != null) {
            Entity var2 = var1.getEntity();
            if (var2 != null) {
                if (var2 instanceof EntityHuman) {
                    this.village.a(var2.getName(), -2);
                } else if (var2 instanceof IMonster) {
                    this.village.h();
                }
            } else if (var2 == null) {
                EntityHuman var3 = this.world.findNearbyPlayer(this, 16.0D);
                if (var3 != null) {
                    this.village.h();
                }
            }
        }

        super.die(var1);
    }

    public void a_(EntityHuman var1) {
        this.tradingPlayer = var1;
    }

    public EntityHuman b() {
        return this.tradingPlayer;
    }

    public boolean cc() {
        return this.tradingPlayer != null;
    }

    public void a(MerchantRecipe var1) {
        var1.f();
        this.a_ = -this.q();
        this.makeSound("mob.villager.yes", this.bf(), this.bg());
        if (var1.a((MerchantRecipe)this.bu.get(this.bu.size() - 1))) {
            this.bv = 40;
            this.bw = true;
            if (this.tradingPlayer != null) {
                this.by = this.tradingPlayer.getName();
            } else {
                this.by = null;
            }
        }

        if (var1.getBuyItem1().getItem() == Items.EMERALD) {
            this.riches += var1.getBuyItem1().count;
        }

    }

    public void a_(ItemStack var1) {
        if (!this.world.isStatic && this.a_ > -this.q() + 20) {
            this.a_ = -this.q();
            if (var1 != null) {
                this.makeSound("mob.villager.yes", this.bf(), this.bg());
            } else {
                this.makeSound("mob.villager.no", this.bf(), this.bg());
            }
        }

    }

    public MerchantRecipeList getOffers(EntityHuman var1) {
        if (this.bu == null) {
            this.t(1);
        }

        return this.bu;
    }

    private float p(float var1) {
        float var2 = var1 + this.bA;
        return var2 > 0.9F ? 0.9F - (var2 - 0.9F) : var2;
    }

    private void t(int var1) {
        if (this.bu != null) {
            this.bA = MathHelper.c((float)this.bu.size()) * 0.2F;
        } else {
            this.bA = 0.0F;
        }

        MerchantRecipeList var2;
        var2 = new MerchantRecipeList();
        int var6;
        label50:
        switch(this.getProfession()) {
            case 0:
                a(var2, Items.WHEAT, this.random, this.p(0.9F));
                a(var2, Item.getItemOf(Blocks.WOOL), this.random, this.p(0.5F));
                a(var2, Items.RAW_CHICKEN, this.random, this.p(0.5F));
                a(var2, Items.COOKED_FISH, this.random, this.p(0.4F));
                b(var2, Items.BREAD, this.random, this.p(0.9F));
                b(var2, Items.MELON, this.random, this.p(0.3F));
                b(var2, Items.APPLE, this.random, this.p(0.3F));
                b(var2, Items.COOKIE, this.random, this.p(0.3F));
                b(var2, Items.SHEARS, this.random, this.p(0.3F));
                b(var2, Items.FLINT_AND_STEEL, this.random, this.p(0.3F));
                b(var2, Items.COOKED_CHICKEN, this.random, this.p(0.3F));
                b(var2, Items.ARROW, this.random, this.p(0.5F));
                if (this.random.nextFloat() < this.p(0.5F)) {
                    var2.add(new MerchantRecipe(new ItemStack(Blocks.GRAVEL, 10), new ItemStack(Items.EMERALD), new ItemStack(Items.FLINT, 4 + this.random.nextInt(2), 0)));
                }
                break;
            case 1:
                a(var2, Items.PAPER, this.random, this.p(0.8F));
                a(var2, Items.BOOK, this.random, this.p(0.8F));
                a(var2, Items.WRITTEN_BOOK, this.random, this.p(0.3F));
                b(var2, Item.getItemOf(Blocks.BOOKSHELF), this.random, this.p(0.8F));
                b(var2, Item.getItemOf(Blocks.GLASS), this.random, this.p(0.2F));
                b(var2, Items.COMPASS, this.random, this.p(0.2F));
                b(var2, Items.WATCH, this.random, this.p(0.2F));
                if (this.random.nextFloat() < this.p(0.07F)) {
                    Enchantment var8 = Enchantment.c[this.random.nextInt(Enchantment.c.length)];
                    int var10 = MathHelper.nextInt(this.random, var8.getStartLevel(), var8.getMaxLevel());
                    ItemStack var11 = Items.ENCHANTED_BOOK.a(new EnchantmentInstance(var8, var10));
                    var6 = 2 + this.random.nextInt(5 + var10 * 10) + 3 * var10;
                    var2.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(Items.EMERALD, var6), var11));
                }
                break;
            case 2:
                b(var2, Items.EYE_OF_ENDER, this.random, this.p(0.3F));
                b(var2, Items.EXP_BOTTLE, this.random, this.p(0.2F));
                b(var2, Items.REDSTONE, this.random, this.p(0.4F));
                b(var2, Item.getItemOf(Blocks.GLOWSTONE), this.random, this.p(0.3F));
                Item[] var3 = new Item[]{Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE};
                Item[] var4 = var3;
                int var5 = var3.length;
                var6 = 0;

                while(true) {
                    if (var6 >= var5) {
                        break label50;
                    }

                    Item var7 = var4[var6];
                    if (this.random.nextFloat() < this.p(0.05F)) {
                        var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Items.EMERALD, 2 + this.random.nextInt(3), 0), EnchantmentManager.a(this.random, new ItemStack(var7, 1, 0), 5 + this.random.nextInt(15))));
                    }

                    ++var6;
                }
            case 3:
                a(var2, Items.COAL, this.random, this.p(0.7F));
                a(var2, Items.IRON_INGOT, this.random, this.p(0.5F));
                a(var2, Items.GOLD_INGOT, this.random, this.p(0.5F));
                a(var2, Items.DIAMOND, this.random, this.p(0.5F));
                b(var2, Items.IRON_SWORD, this.random, this.p(0.5F));
                b(var2, Items.DIAMOND_SWORD, this.random, this.p(0.5F));
                b(var2, Items.IRON_AXE, this.random, this.p(0.3F));
                b(var2, Items.DIAMOND_AXE, this.random, this.p(0.3F));
                b(var2, Items.IRON_PICKAXE, this.random, this.p(0.5F));
                b(var2, Items.DIAMOND_PICKAXE, this.random, this.p(0.5F));
                b(var2, Items.IRON_SPADE, this.random, this.p(0.2F));
                b(var2, Items.DIAMOND_SPADE, this.random, this.p(0.2F));
                b(var2, Items.IRON_HOE, this.random, this.p(0.2F));
                b(var2, Items.DIAMOND_HOE, this.random, this.p(0.2F));
                b(var2, Items.IRON_BOOTS, this.random, this.p(0.2F));
                b(var2, Items.DIAMOND_BOOTS, this.random, this.p(0.2F));
                b(var2, Items.IRON_HELMET, this.random, this.p(0.2F));
                b(var2, Items.DIAMOND_HELMET, this.random, this.p(0.2F));
                b(var2, Items.IRON_CHESTPLATE, this.random, this.p(0.2F));
                b(var2, Items.DIAMOND_CHESTPLATE, this.random, this.p(0.2F));
                b(var2, Items.IRON_LEGGINGS, this.random, this.p(0.2F));
                b(var2, Items.DIAMOND_LEGGINGS, this.random, this.p(0.2F));
                b(var2, Items.CHAINMAIL_BOOTS, this.random, this.p(0.1F));
                b(var2, Items.CHAINMAIL_HELMET, this.random, this.p(0.1F));
                b(var2, Items.CHAINMAIL_CHESTPLATE, this.random, this.p(0.1F));
                b(var2, Items.CHAINMAIL_LEGGINGS, this.random, this.p(0.1F));
                break;
            case 4:
                a(var2, Items.COAL, this.random, this.p(0.7F));
                a(var2, Items.PORK, this.random, this.p(0.5F));
                a(var2, Items.RAW_BEEF, this.random, this.p(0.5F));
                b(var2, Items.SADDLE, this.random, this.p(0.1F));
                b(var2, Items.LEATHER_CHESTPLATE, this.random, this.p(0.3F));
                b(var2, Items.LEATHER_BOOTS, this.random, this.p(0.3F));
                b(var2, Items.LEATHER_HELMET, this.random, this.p(0.3F));
                b(var2, Items.LEATHER_LEGGINGS, this.random, this.p(0.3F));
                b(var2, Items.GRILLED_PORK, this.random, this.p(0.3F));
                b(var2, Items.COOKED_BEEF, this.random, this.p(0.3F));
        }

        if (var2.isEmpty()) {
            a(var2, Items.GOLD_INGOT, this.random, 1.0F);
        }

        Collections.shuffle(var2);
        if (this.bu == null) {
            this.bu = new MerchantRecipeList();
        }

        for(int var9 = 0; var9 < var1 && var9 < var2.size(); ++var9) {
            this.bu.a((MerchantRecipe)var2.get(var9));
        }

    }

    private static void a(MerchantRecipeList var0, Item var1, Random var2, float var3) {
        if (var2.nextFloat() < var3) {
            var0.add(new MerchantRecipe(a(var1, var2), Items.EMERALD));
        }

    }

    private static ItemStack a(Item var0, Random var1) {
        return new ItemStack(var0, b(var0, var1), 0);
    }

    private static int b(Item var0, Random var1) {
        Tuple var2 = (Tuple)bB.get(var0);
        if (var2 == null) {
            return 1;
        } else {
            return (Integer)var2.a() >= (Integer)var2.b() ? (Integer)var2.a() : (Integer)var2.a() + var1.nextInt((Integer)var2.b() - (Integer)var2.a());
        }
    }

    private static void b(MerchantRecipeList var0, Item var1, Random var2, float var3) {
        if (var2.nextFloat() < var3) {
            int var4 = c(var1, var2);
            ItemStack var5;
            ItemStack var6;
            if (var4 < 0) {
                var5 = new ItemStack(Items.EMERALD, 1, 0);
                var6 = new ItemStack(var1, -var4, 0);
            } else {
                var5 = new ItemStack(Items.EMERALD, var4, 0);
                var6 = new ItemStack(var1, 1, 0);
            }

            var0.add(new MerchantRecipe(var5, var6));
        }

    }

    private static int c(Item var0, Random var1) {
        Tuple var2 = (Tuple)bC.get(var0);
        if (var2 == null) {
            return 1;
        } else {
            return (Integer)var2.a() >= (Integer)var2.b() ? (Integer)var2.a() : (Integer)var2.a() + var1.nextInt((Integer)var2.b() - (Integer)var2.a());
        }
    }

    public GroupDataEntity prepare(GroupDataEntity var1) {
        var1 = super.prepare(var1);
        this.setProfession(this.world.random.nextInt(5));
        return var1;
    }

    public void cd() {
        this.bz = true;
    }

    public EntityVillager b(EntityAgeable var1) {
        EntityVillager var2 = new EntityVillager(this.world);
        var2.prepare((GroupDataEntity)null);
        return var2;
    }

    public boolean bM() {
        return false;
    }

    static {
        bB.put(Items.COAL, new Tuple(16, 24));
        bB.put(Items.IRON_INGOT, new Tuple(8, 10));
        bB.put(Items.GOLD_INGOT, new Tuple(8, 10));
        bB.put(Items.DIAMOND, new Tuple(4, 6));
        bB.put(Items.PAPER, new Tuple(24, 36));
        bB.put(Items.BOOK, new Tuple(11, 13));
        bB.put(Items.WRITTEN_BOOK, new Tuple(1, 1));
        bB.put(Items.ENDER_PEARL, new Tuple(3, 4));
        bB.put(Items.EYE_OF_ENDER, new Tuple(2, 3));
        bB.put(Items.PORK, new Tuple(14, 18));
        bB.put(Items.RAW_BEEF, new Tuple(14, 18));
        bB.put(Items.RAW_CHICKEN, new Tuple(14, 18));
        bB.put(Items.COOKED_FISH, new Tuple(9, 13));
        bB.put(Items.SEEDS, new Tuple(34, 48));
        bB.put(Items.MELON_SEEDS, new Tuple(30, 38));
        bB.put(Items.PUMPKIN_SEEDS, new Tuple(30, 38));
        bB.put(Items.WHEAT, new Tuple(18, 22));
        bB.put(Item.getItemOf(Blocks.WOOL), new Tuple(14, 22));
        bB.put(Items.ROTTEN_FLESH, new Tuple(36, 64));
        bC.put(Items.FLINT_AND_STEEL, new Tuple(3, 4));
        bC.put(Items.SHEARS, new Tuple(3, 4));
        bC.put(Items.IRON_SWORD, new Tuple(7, 11));
        bC.put(Items.DIAMOND_SWORD, new Tuple(12, 14));
        bC.put(Items.IRON_AXE, new Tuple(6, 8));
        bC.put(Items.DIAMOND_AXE, new Tuple(9, 12));
        bC.put(Items.IRON_PICKAXE, new Tuple(7, 9));
        bC.put(Items.DIAMOND_PICKAXE, new Tuple(10, 12));
        bC.put(Items.IRON_SPADE, new Tuple(4, 6));
        bC.put(Items.DIAMOND_SPADE, new Tuple(7, 8));
        bC.put(Items.IRON_HOE, new Tuple(4, 6));
        bC.put(Items.DIAMOND_HOE, new Tuple(7, 8));
        bC.put(Items.IRON_BOOTS, new Tuple(4, 6));
        bC.put(Items.DIAMOND_BOOTS, new Tuple(7, 8));
        bC.put(Items.IRON_HELMET, new Tuple(4, 6));
        bC.put(Items.DIAMOND_HELMET, new Tuple(7, 8));
        bC.put(Items.IRON_CHESTPLATE, new Tuple(10, 14));
        bC.put(Items.DIAMOND_CHESTPLATE, new Tuple(16, 19));
        bC.put(Items.IRON_LEGGINGS, new Tuple(8, 10));
        bC.put(Items.DIAMOND_LEGGINGS, new Tuple(11, 14));
        bC.put(Items.CHAINMAIL_BOOTS, new Tuple(5, 7));
        bC.put(Items.CHAINMAIL_HELMET, new Tuple(5, 7));
        bC.put(Items.CHAINMAIL_CHESTPLATE, new Tuple(11, 15));
        bC.put(Items.CHAINMAIL_LEGGINGS, new Tuple(9, 11));
        bC.put(Items.BREAD, new Tuple(-4, -2));
        bC.put(Items.MELON, new Tuple(-8, -4));
        bC.put(Items.APPLE, new Tuple(-8, -4));
        bC.put(Items.COOKIE, new Tuple(-10, -7));
        bC.put(Item.getItemOf(Blocks.GLASS), new Tuple(-5, -3));
        bC.put(Item.getItemOf(Blocks.BOOKSHELF), new Tuple(3, 4));
        bC.put(Items.LEATHER_CHESTPLATE, new Tuple(4, 5));
        bC.put(Items.LEATHER_BOOTS, new Tuple(2, 4));
        bC.put(Items.LEATHER_HELMET, new Tuple(2, 4));
        bC.put(Items.LEATHER_LEGGINGS, new Tuple(2, 4));
        bC.put(Items.SADDLE, new Tuple(6, 8));
        bC.put(Items.EXP_BOTTLE, new Tuple(-4, -1));
        bC.put(Items.REDSTONE, new Tuple(-4, -1));
        bC.put(Items.COMPASS, new Tuple(10, 12));
        bC.put(Items.WATCH, new Tuple(10, 12));
        bC.put(Item.getItemOf(Blocks.GLOWSTONE), new Tuple(-3, -1));
        bC.put(Items.GRILLED_PORK, new Tuple(-7, -5));
        bC.put(Items.COOKED_BEEF, new Tuple(-7, -5));
        bC.put(Items.COOKED_CHICKEN, new Tuple(-8, -6));
        bC.put(Items.EYE_OF_ENDER, new Tuple(7, 11));
        bC.put(Items.ARROW, new Tuple(-12, -8));
    }
}
