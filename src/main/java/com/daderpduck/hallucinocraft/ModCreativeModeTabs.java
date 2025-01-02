package com.daderpduck.hallucinocraft;

import com.daderpduck.hallucinocraft.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hallucinocraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.RED_SHROOMS.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.creativetab"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.SHROOM_STEW.get());
                output.accept(ModItems.BROWN_SHROOMS.get());
                output.accept(ModItems.DRIED_BROWN_MUSHROOM.get());
                output.accept(ModItems.RED_SHROOMS.get());
                output.accept(ModItems.DRIED_RED_MUSHROOM.get());

                output.accept(ModItems.COKE_CAKE.get());
                output.accept(ModItems.COCAINE_ROCK.get());
                output.accept(ModItems.COCAINE_POWDER.get());
                output.accept(ModItems.COCAINE_DUST.get());
                output.accept(ModItems.COCA_MULCH.get());
                output.accept(ModItems.COCA_LEAF.get());
                output.accept(ModItems.COCA_SEEDS.get());

                output.accept(ModItems.HASH_MUFFIN.get());
                output.accept(ModItems.CANNABIS_TEA.get());
                output.accept(ModItems.UNBREWED_CANNABIS_TEA.get());
                output.accept(ModItems.CANNABIS_JOINT.get());
                output.accept(ModItems.DRIED_CANNABIS_LEAF.get());
                output.accept(ModItems.CANNABIS_LEAF.get());
                output.accept(ModItems.CANNABIS_SEEDS.get());
                output.accept(ModItems.CANNABIS_BUD.get());

                output.accept(ModItems.CIGARETTE.get());

                output.accept(ModItems.MORPHINE_BOTTLE.get());
                output.accept(ModItems.OPIUM_BOTTLE_3.get());
                output.accept(ModItems.OPIUM_BOTTLE_2.get());
                output.accept(ModItems.OPIUM_BOTTLE_1.get());
                output.accept(ModItems.OPIUM_BOTTLE_0.get());

                output.accept(ModItems.SOUL_WRENCHER_BOTTLE.get());
                output.accept(ModItems.SOUL_RESTER_BOTTLE.get());
                output.accept(ModItems.SOUL_CONCENTRATE.get());

                output.accept(ModItems.COCAINE_SYRINGE.get());
                output.accept(ModItems.MORPHINE_SYRINGE.get());
                output.accept(ModItems.SOUL_RESTER_SYRINGE.get());
                output.accept(ModItems.SOUL_WRENCHER_SYRINGE.get());

                output.accept(ModItems.EMPTY_SYRINGE.get());
                output.accept(ModItems.BONG.get());
            })
            .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
