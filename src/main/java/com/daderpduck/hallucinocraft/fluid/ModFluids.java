package com.daderpduck.hallucinocraft.fluid;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import com.daderpduck.hallucinocraft.blocks.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Hallucinocraft.MOD_ID);

    public static final RegistryObject<FlowingFluid> ALCOHOL_FLUID = FLUIDS.register("alcohol_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.ALCOHOL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> ALCOHOL_FLOWING = FLUIDS.register("alcohol_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.ALCOHOL_PROPERTIES));

    public static final ForgeFlowingFluid.Properties ALCOHOL_PROPERTIES = new ForgeFlowingFluid.Properties(
            ALCOHOL_FLUID::get,
            ALCOHOL_FLOWING::get,
            FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
                    .density(15)
                    .luminosity(2)
                    .viscosity(5)
                    .sound(SoundEvents.HONEY_DRINK)
                    .overlay(WATER_OVERLAY_RL)
                    .color(0xBFFCBA03)
    ).slopeFindDistance(2).levelDecreasePerBlock(2).block(() -> ModFluids.ALCOHOL_BLOCK.get());

    public static final RegistryObject<LiquidBlock> ALCOHOL_BLOCK = ModBlocks.BLOCKS.register("alcohol", () -> new LiquidBlock(ModFluids.ALCOHOL_FLUID::get, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));

    public static void register(IEventBus modBus) {
        FLUIDS.register(modBus);
    }

    public static void initRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer(ALCOHOL_BLOCK.get(), RenderType.translucent());
    }
}
