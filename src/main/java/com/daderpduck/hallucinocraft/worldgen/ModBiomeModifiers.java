package com.daderpduck.hallucinocraft.worldgen;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_COCA_PLANTS = registerKey("add_coca_plants");
    public static final ResourceKey<BiomeModifier> ADD_CANNABIS_PLANTS = registerKey("add_cannabis_plants");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_COCA_PLANTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_SAVANNA),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.COCA_PLACED)),
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_CANNABIS_PLANTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_JUNGLE),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CANNABIS_PLACED)),
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Hallucinocraft.MOD_ID, name));
    }
}
