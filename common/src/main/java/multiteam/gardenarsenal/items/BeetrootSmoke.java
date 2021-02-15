package multiteam.gardenarsenal.items;

import multiteam.gardenarsenal.entities.BeetrootSmokeProjectile;
import multiteam.gardenarsenal.registries.GardenArsenalItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BeetrootSmoke extends WeaponItem {
    public BeetrootSmoke(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        tooltip.add(new TranslatableComponent("tooltip.gardenarsenal.beetroot_smoke_desc").copy().withStyle(ChatFormatting.DARK_RED));

        CompoundTag nbtTagCompound = itemStack.getTag();

        if (nbtTagCompound == null){
            nbtTagCompound = new CompoundTag();
            itemStack.setTag(nbtTagCompound);
        }

        nbtTagCompound.putString("skinType", "Default");

//        tooltip.add(new TextComponent(nbtTagCompound.getString("skinType")).copy().withStyle(ChatFormatting.DARK_GREEN));

        tooltip.add(new TextComponent(nbtTagCompound.getString("skinType")).copy().setStyle(Style.EMPTY.withColor(TextColor.fromRgb(2909434))));

        //tooltip.add(new TextComponent(nbtTagCompound.getString("skinType"    )).copy().modifyStyle(new TextFormatting("UNCOMMON", 'á', 25, 2066191)));
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.FIREWORK_ROCKET_SHOOT;
    }

    @Override
    public void createProjectileEntities(Level world, Player playerEntity) {
        BeetrootSmokeProjectile projectile = new BeetrootSmokeProjectile(world, playerEntity);
        projectile.bulletDamage = 0;
        projectile.setItem(new ItemStack(this::getAmmoItem));
        projectile.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, 2.0F, 1.0F);

        world.addFreshEntity(projectile);
    }

    @Override
    public Item getAmmoItem() {
        return GardenArsenalItems.BEETROOT_SMOKE.get();
    }
}
