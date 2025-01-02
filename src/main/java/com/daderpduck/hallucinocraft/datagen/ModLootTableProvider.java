package com.daderpduck.hallucinocraft.datagen;

import com.daderpduck.hallucinocraft.datagen.loot.ModBlockLootTables;
import com.google.common.collect.ImmutableList;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput pGenerator) {
        super(pGenerator, Collections.emptySet(), ImmutableList.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)));
    }
}
