package com.daderpduck.hallucinocraft.drugs;

import net.minecraft.entity.player.PlayerEntity;

public class CocainePowder extends Drug {
    public CocainePowder(DrugProperties properties) {
        super(properties);
    }

    @Override
    public void renderTick(float effect) {
        DrugEffects.BRIGHTNESS.addValue(effect*0.3F);
        DrugEffects.SATURATION.addValue(effect*-0.1F);
        DrugEffects.CAMERA_TREMBLE.addValue(effect);
    }

    @Override
    public void effectTick(PlayerEntity player, float effect) {
        DrugEffects.MOVEMENT_SPEED.addValue(effect*0.3F);
        DrugEffects.DIG_SPEED.addValue(effect*0.3F);
    }
}
