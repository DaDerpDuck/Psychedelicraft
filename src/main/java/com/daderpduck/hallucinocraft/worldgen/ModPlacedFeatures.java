package com.daderpduck.hallucinocraft.worldgen;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> COCA_PLACED = PlacementUtils.createKey(Hallucinocraft.MOD_ID + ":coca_placed");
    public static final ResourceKey<PlacedFeature> CANNABIS_PLACED = PlacementUtils.createKey(Hallucinocraft.MOD_ID + ":cannabis_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, COCA_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.COCA_PATCH),
                RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, CANNABIS_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.CANNABIS_PATCH),
                RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }
}
