package multiteam.gardenarsenal.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public abstract class WeaponItem extends BowItem {
    public WeaponItem(Item.Properties settings) {
        super(settings);
    }

    public ItemStack getAmmoInInventory(Player playerEntity) {
        Item predicate = this.getAmmoItem();
        Inventory playerInventory = playerEntity.inventory;
        for (int i = 0; i < playerInventory.getContainerSize(); i++) {
            ItemStack stack = playerInventory.getItem(i);
            if (predicate == stack.getItem()) {
                return stack;
            }
        }

        return playerEntity.abilities.instabuild ? new ItemStack(predicate) :ItemStack.EMPTY;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean bl = !this.getAmmoInInventory(player).isEmpty();
        if (!player.abilities.instabuild && !bl) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(itemStack);
        }
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.getItem() == this.getAmmoItem();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player) {
            Player playerEntity = (Player)user;
            boolean bl = playerEntity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemStack = getAmmoInInventory(playerEntity);
            playerEntity.getCooldowns().addCooldown(this, this.getCooldown());
            if ((!itemStack.isEmpty() && this.getAllSupportedProjectiles().test(itemStack)) || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(this.getAmmoItem());
                }

                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = getPullProgress(i);
                if (!((double)f < 0.1D)) {
                    boolean bl2 = bl && itemStack.getItem() == this.getAmmoItem();
                    if (!world.isClientSide) {
                        this.createProjectileEntities(world, playerEntity);

                        stack.hurtAndBreak(1, (LivingEntity)playerEntity, (p) -> {
                            ((Player)p).broadcastBreakEvent(playerEntity.getUsedItemHand());
                        });
                    }

                    world.playSound((Player) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), this.getSoundEvent(), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!bl2 && !playerEntity.abilities.instabuild) {
                        itemStack.shrink(1);
                        if (itemStack.isEmpty()) {
                            playerEntity.inventory.removeItem(itemStack);
                        }
                    }

                    playerEntity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }

    protected abstract int getCooldown();

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public abstract SoundEvent getSoundEvent();

    public abstract void createProjectileEntities(Level world, Player playerEntity);

    public abstract Item getAmmoItem();

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
}
