package com.daderpduck.hallucinocraft.sounds;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModSounds {
    public static final RegistryObject<SoundEvent> BONG_HIT = register("item.bong.hit");
    public static final RegistryObject<SoundEvent> AMBIENT_SOUL_WRENCHER_LOOP = register("ambience.soul_wrencher.loop");
    public static final RegistryObject<SoundEvent> AMBIENT_SOUL_WRENCHER_ADDITIONS = register("ambience.soul_wrencher.additions");


    private static RegistryObject<SoundEvent> register(String key) {
        return register(key, () -> new SoundEvent(new ResourceLocation(Hallucinocraft.MOD_ID, key)));
    }

    private static RegistryObject<SoundEvent> register(String name, Supplier<SoundEvent> supplier) {
        return Hallucinocraft.SOUNDS.register(name, supplier);
    }

    public static void init() {
    }
}
