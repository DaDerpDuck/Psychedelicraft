package com.daderpduck.hallucinocraft.datagen.loot;

import com.daderpduck.hallucinocraft.blocks.ModBlocks;
import com.daderpduck.hallucinocraft.items.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        LootItemBlockStatePropertyCondition.Builder cocaGrownCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.COCA_BLOCK.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlocks.COCA_BLOCK.get().getAgeProperty(), 3));
        add(ModBlocks.COCA_BLOCK.get(), applyExplosionDecay(ModBlocks.COCA_BLOCK.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.COCA_LEAF.get())
                                .when(cocaGrownCondition)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2))
                                .otherwise(LootItem.lootTableItem(ModItems.COCA_SEEDS.get()))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.COCA_SEEDS.get())
                                .when(cocaGrownCondition)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));
        LootItemBlockStatePropertyCondition.Builder cannabisGrownCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CANNABIS_BLOCK.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlocks.CANNABIS_BLOCK.get().getAgeProperty(), 3));
        add(ModBlocks.CANNABIS_BLOCK.get(), applyExplosionDecay(ModBlocks.CANNABIS_BLOCK.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.CANNABIS_BUD.get())
                                .when(cannabisGrownCondition)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1))
                                .otherwise(LootItem.lootTableItem(ModItems.CANNABIS_SEEDS.get()))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.CANNABIS_SEEDS.get())
                                .when(cannabisGrownCondition)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.CANNABIS_LEAF.get())
                                .when(cannabisGrownCondition)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));
        add(ModBlocks.COKE_CAKE_BLOCK.get(), noDrop());
        add(ModBlocks.CUT_POPPY_BLOCK.get(), noDrop());
//        dropSelf(ModBlocks.FERMENTING_BOTTLE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
