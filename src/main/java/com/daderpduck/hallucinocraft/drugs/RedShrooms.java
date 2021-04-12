package com.daderpduck.hallucinocraft.drugs;

public class RedShrooms extends Drug {
    public RedShrooms(DrugProperties properties) {
        super(properties);
    }

    @Override
    public void renderTick(float effect) {
        DrugEffects.BIG_WAVES.addValue(effect*0.3F);
        DrugEffects.SMALL_WAVES.addValue(effect*0.4F);
        DrugEffects.WIGGLE_WAVES.addValue(effect*0.4F);
        DrugEffects.WORLD_DEFORMATION.addValue(effect*1.3F);
        DrugEffects.KALEIDOSCOPE_INTENSITY.addValue(effect*effect*0.2F);
        DrugEffects.SATURATION.addValue(Math.min(effect, 0.5F)*0.5F);
        DrugEffects.HUE_AMPLITUDE.addValue(effect*0.8F);
        DrugEffects.CAMERA_TREMBLE.addValue(effect*0.5F);
        super.renderTick(effect);
    }
}