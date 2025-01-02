package com.daderpduck.hallucinocraft.commands;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCommandArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Hallucinocraft.MOD_ID);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> DRUG_ARGUMENT_TYPE = COMMAND_ARGUMENT_TYPES.register("drug", () -> ArgumentTypeInfos.registerByClass(DrugArgument.class, SingletonArgumentInfo.contextFree(DrugArgument::drug)));

    public static void register(IEventBus event) {
        COMMAND_ARGUMENT_TYPES.register(event);
    }
}
