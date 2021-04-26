package com.daderpduck.hallucinocraft.items;

import com.daderpduck.hallucinocraft.drugs.Drug;
import com.daderpduck.hallucinocraft.drugs.DrugInstance;
import com.daderpduck.hallucinocraft.sounds.ModSounds;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BongItem extends Item implements IVanishable {
    public static final HashMap<Item, ModItems.DrugChain> BONGABLES = new HashMap<>();

    public BongItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0x4287F5;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        if (hand == Hand.MAIN_HAND && !itemStack.isEmpty() && itemStack.getDamageValue() < getMaxDamage(itemStack)) {
            ItemStack offhandItem = playerEntity.getItemInHand(Hand.OFF_HAND);
            if (!offhandItem.isEmpty()) {
                if (BONGABLES.keySet().stream().anyMatch((item -> item.equals(offhandItem.getItem())))) {
                    playerEntity.startUsingItem(hand);
                    return ActionResult.consume(itemStack);
                } else {
                    return ActionResult.fail(itemStack);
                }
            }
        }

        return ActionResult.pass(itemStack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;

            ItemStack offhandItem = playerEntity.getItemInHand(Hand.OFF_HAND);

            ModItems.DrugChain drugChain = BONGABLES.get(offhandItem.getItem());
            for (ModItems.DrugEffectProperty drugEffectProperty : drugChain.list) {
                Drug.addDrug(playerEntity, new DrugInstance(drugEffectProperty.drug.get(), drugEffectProperty.delayTicks, drugEffectProperty.potencyPercentage, drugEffectProperty.duration));
            }

            if (!playerEntity.abilities.instabuild) {
                itemStack.setDamageValue(itemStack.getDamageValue() + 1);
                offhandItem.shrink(1);
            }

            world.playSound(playerEntity, playerEntity.blockPosition(), ModSounds.BONG_HIT.get(), SoundCategory.PLAYERS, 1F, 1F);
        }

        return itemStack;
    }

    @Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 32;
    }

    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> itemStacks) {
        super.fillItemCategory(itemGroup, itemStacks);
        if (allowdedIn(itemGroup)) {
            ItemStack emptyBong = new ItemStack(this);
            emptyBong.setDamageValue(getMaxDamage(emptyBong));
            itemStacks.add(emptyBong);
        }
    }
}
