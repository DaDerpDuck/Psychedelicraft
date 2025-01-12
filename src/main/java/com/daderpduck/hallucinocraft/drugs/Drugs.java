package com.daderpduck.hallucinocraft.drugs;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Drugs {
    public static final DeferredRegister<Drug> DRUGS = DeferredRegister.create(new ResourceLocation(Hallucinocraft.MOD_ID, "drug"), Hallucinocraft.MOD_ID);
    public static IForgeRegistry<Drug> DrugRegistry;

    public static final RegistryObject<Drug> RED_SHROOMS = register("red_shrooms", () -> new RedShrooms(new Drug.DrugProperties().adsr(2400F, 200F, 0.8F, 2400F)));
    public static final RegistryObject<Drug> BROWN_SHROOMS = register("brown_shrooms", () -> new BrownShrooms(new Drug.DrugProperties().adsr(2400F, 0F, 1F, 2400F)));
    public static final RegistryObject<Drug> COCAINE = register("cocaine", () -> new Cocaine(new Drug.DrugProperties().adsr(800F, 0F, 1F, 1200F).abuse(2)));
    public static final RegistryObject<Drug> CANNABIS = register("cannabis", () -> new Cannabis(new Drug.DrugProperties().adsr(1800F, 0F, 1F, 2400F)));
    public static final RegistryObject<Drug> MORPHINE = register("morphine", () -> new Morphine(new Drug.DrugProperties().adsr(0F, 800F, 0.8F, 200F)));
    public static final RegistryObject<Drug> SOUL_RESTER = register("soul_rester", () -> new SoulRester(new Drug.DrugProperties().adsr(800F, 0F, 1F, 4800F)));
    public static final RegistryObject<Drug> SOUL_WRENCHER = register("soul_wrencher", () -> new SoulWrencher(new Drug.DrugProperties().adsr(2400F, 0F, 1F, 4800F)));

    private static RegistryObject<Drug> register(String name, Supplier<? extends Drug> supplier) {
        return DRUGS.register(name, supplier);
    }

    public static RegistryBuilder<Drug> getRegistryBuilder() {
        return new RegistryBuilder<>();
    }

    public static void register(IEventBus modBus) {
        DRUGS.register(modBus);
    }
}
