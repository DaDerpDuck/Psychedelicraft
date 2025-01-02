package com.daderpduck.hallucinocraft.worldgen;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import com.daderpduck.hallucinocraft.blocks.ModBlocks;
import com.daderpduck.hallucinocraft.blocks.TallCropsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> COCA_PATCH = FeatureUtils.createKey(Hallucinocraft.MOD_ID + ":coca_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CANNABIS_PATCH = FeatureUtils.createKey(Hallucinocraft.MOD_ID + ":cannabis_patch");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, COCA_PATCH, Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.inlinePlaced(
                Feature.BLOCK_COLUMN,
                BlockColumnConfiguration.simple(
                        BiasedToBottomInt.of(1,3),
                        BlockStateProvider.simple(ModBlocks.COCA_BLOCK.get().defaultBlockState().setValue(TallCropsBlock.AGE, 3).setValue(TallCropsBlock.CROP, false))),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.wouldSurvive(ModBlocks.COCA_BLOCK.get().defaultBlockState().setValue(TallCropsBlock.CROP, false), BlockPos.ZERO))))));
        FeatureUtils.register(context, CANNABIS_PATCH, Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.inlinePlaced(
                Feature.BLOCK_COLUMN,
                BlockColumnConfiguration.simple(
                        BiasedToBottomInt.of(1,3),
                        BlockStateProvider.simple(ModBlocks.CANNABIS_BLOCK.get().defaultBlockState().setValue(TallCropsBlock.AGE, 3).setValue(TallCropsBlock.CROP, false))),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.wouldSurvive(ModBlocks.CANNABIS_BLOCK.get().defaultBlockState().setValue(TallCropsBlock.CROP, false), BlockPos.ZERO))))));
    }
}
