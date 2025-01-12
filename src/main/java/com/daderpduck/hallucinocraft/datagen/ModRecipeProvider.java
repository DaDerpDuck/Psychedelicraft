package com.daderpduck.hallucinocraft.datagen;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import com.daderpduck.hallucinocraft.datagen.custom.NbtShapedRecipeBuilder;
import com.daderpduck.hallucinocraft.items.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        CompoundTag bongNbt = new CompoundTag();
        bongNbt.putInt("Damage", 8);
        NbtShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONG.get())
                .nbt(bongNbt)
                .define('A', Items.GLASS)
                .define('B', Items.GLASS_BOTTLE)
                .pattern("A")
                .pattern("B")
                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                .unlockedBy(getHasName(Items.GLASS_BOTTLE), has(Items.GLASS_BOTTLE))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMPTY_SYRINGE.get())
                .define('i', Items.IRON_NUGGET)
                .define('g', Items.GLASS)
                .define('I', Items.IRON_INGOT)
                .pattern("igI")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pFinishedRecipeConsumer);
        campfireCooking(pFinishedRecipeConsumer, Items.BROWN_MUSHROOM, ModItems.DRIED_BROWN_MUSHROOM.get());
        campfireCooking(pFinishedRecipeConsumer, Items.RED_MUSHROOM, ModItems.DRIED_RED_MUSHROOM.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BROWN_SHROOMS.get())
                .define('#', ModItems.DRIED_BROWN_MUSHROOM.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(ModItems.DRIED_BROWN_MUSHROOM.get()), has(ModItems.DRIED_BROWN_MUSHROOM.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RED_SHROOMS.get())
                .define('#', ModItems.DRIED_RED_MUSHROOM.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(ModItems.DRIED_RED_MUSHROOM.get()), has(ModItems.DRIED_RED_MUSHROOM.get()))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SHROOM_STEW.get())
                .requires(ModItems.BROWN_SHROOMS.get())
                .requires(ModItems.RED_SHROOMS.get())
                .requires(Items.BOWL)
                .unlockedBy(getHasName(ModItems.BROWN_SHROOMS.get()), has(ModItems.BROWN_SHROOMS.get()))
                .unlockedBy(getHasName(ModItems.RED_SHROOMS.get()), has(ModItems.RED_SHROOMS.get()))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COCA_MULCH.get())
                .requires(ModItems.COCA_LEAF.get(), 8)
                .requires(Items.WATER_BUCKET)
                .unlockedBy(getHasName(ModItems.COCA_LEAF.get()), has(ModItems.COCA_LEAF.get()))
                .save(pFinishedRecipeConsumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.COCA_MULCH.get()), RecipeCategory.MISC, ModItems.COCAINE_DUST.get(), 0.35F, 600)
                .unlockedBy(getHasName(ModItems.COCA_MULCH.get()), has(ModItems.COCA_MULCH.get()))
                .save(pFinishedRecipeConsumer);
        campfireCooking(pFinishedRecipeConsumer, ModItems.COCA_MULCH.get(), ModItems.COCAINE_DUST.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COCAINE_POWDER.get())
                .define('#', ModItems.COCAINE_DUST.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModItems.COCAINE_DUST.get()), has(ModItems.COCAINE_DUST.get()))
                .save(pFinishedRecipeConsumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.COCAINE_POWDER.get()), RecipeCategory.MISC, ModItems.COCAINE_ROCK.get(), 0.35F, 600)
                .unlockedBy(getHasName(ModItems.COCAINE_POWDER.get()), has(ModItems.COCAINE_POWDER.get()))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COCAINE_SYRINGE.get())
                .requires(ModItems.COCAINE_POWDER.get())
                .requires(ModItems.EMPTY_SYRINGE.get())
                .requires(Items.WATER_BUCKET)
                .unlockedBy(getHasName(ModItems.COCAINE_POWDER.get()), has(ModItems.COCAINE_POWDER.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COKE_CAKE.get())
                .define('A', Items.MILK_BUCKET)
                .define('B', ModItems.COCAINE_POWDER.get())
                .define('C', Items.WHEAT)
                .define('E', Items.EGG)
                .pattern("AAA")
                .pattern("BEB")
                .pattern("CCC")
                .unlockedBy(getHasName(ModItems.COCAINE_POWDER.get()), has(ModItems.COCAINE_POWDER.get()))
                .save(pFinishedRecipeConsumer);
        campfireCooking(pFinishedRecipeConsumer, ModItems.CANNABIS_LEAF.get(), ModItems.DRIED_CANNABIS_LEAF.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.UNBREWED_CANNABIS_TEA.get())
                .requires(ModItems.DRIED_CANNABIS_LEAF.get())
                .requires(Items.SUGAR)
                .requires(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)))
                .unlockedBy(getHasName(ModItems.DRIED_CANNABIS_LEAF.get()), has(ModItems.DRIED_CANNABIS_LEAF.get()))
                .save(pFinishedRecipeConsumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.UNBREWED_CANNABIS_TEA.get()), RecipeCategory.MISC, ModItems.CANNABIS_TEA.get(), 0.35F, 600)
                .unlockedBy(getHasName(ModItems.UNBREWED_CANNABIS_TEA.get()), has(ModItems.UNBREWED_CANNABIS_TEA.get()))
                .save(pFinishedRecipeConsumer);
        campfireCooking(pFinishedRecipeConsumer, ModItems.CANNABIS_BUD.get(), ModItems.DRIED_CANNABIS_BUD.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CANNABIS_JOINT.get())
                .define('#', Items.PAPER)
                .define('@', ModItems.DRIED_CANNABIS_BUD.get())
                .pattern("#")
                .pattern("@")
                .pattern("#")
                .unlockedBy(getHasName(ModItems.DRIED_CANNABIS_BUD.get()), has(ModItems.DRIED_CANNABIS_BUD.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HASH_MUFFIN.get())
                .define('A', Items.COCOA_BEANS)
                .define('B', ModItems.DRIED_CANNABIS_BUD.get())
                .define('C', Items.WHEAT)
                .pattern("ABA")
                .pattern("CCC")
                .unlockedBy(getHasName(ModItems.DRIED_CANNABIS_BUD.get()), has(ModItems.DRIED_CANNABIS_BUD.get()))
                .save(pFinishedRecipeConsumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.OPIUM_BOTTLE_3.get()), RecipeCategory.MISC, ModItems.MORPHINE_BOTTLE.get(), 0.35F, 600)
                .unlockedBy(getHasName(ModItems.OPIUM_BOTTLE_3.get()), has(ModItems.OPIUM_BOTTLE_3.get()))
                .save(pFinishedRecipeConsumer);
        syringe(pFinishedRecipeConsumer, ModItems.MORPHINE_SYRINGE.get(), ModItems.MORPHINE_BOTTLE.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SOUL_CONCENTRATE.get())
                .requires(Items.SOUL_SAND, 2)
                .requires(Items.NETHER_WART)
                .requires(Items.GHAST_TEAR)
                .unlockedBy(getHasName(Items.SOUL_SAND), has(Items.SOUL_SAND))
                .unlockedBy(getHasName(Items.NETHER_WART), has(Items.NETHER_WART))
                .unlockedBy(getHasName(Items.GHAST_TEAR), has(Items.GHAST_TEAR))
                .save(pFinishedRecipeConsumer);
        syringe(pFinishedRecipeConsumer, ModItems.SOUL_RESTER_SYRINGE.get(), ModItems.SOUL_RESTER_BOTTLE.get());
        syringe(pFinishedRecipeConsumer, ModItems.SOUL_WRENCHER_SYRINGE.get(), ModItems.SOUL_WRENCHER_BOTTLE.get());
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AIRLOCK.get())
//                .define('#', ItemTags.WOODEN_BUTTONS)
//                .define('@', Tags.Items.GLASS)
//                .pattern("#")
//                .pattern("@")
//                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
//                .save(pFinishedRecipeConsumer);
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FERMENTING_BOTTLE.get())
//                .define('#', Tags.Items.GLASS)
//                .pattern(" # ")
//                .pattern("# #")
//                .pattern("###")
//                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
//                .save(pFinishedRecipeConsumer);
    }

    protected static void syringe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike ingredient) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(ingredient)
                .requires(ModItems.EMPTY_SYRINGE.get())
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(finishedRecipeConsumer);
    }

    protected static void campfireCooking(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredient, Item result) {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.MISC, result, 0.35F, 600)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(finishedRecipeConsumer, new ResourceLocation(Hallucinocraft.MOD_ID,getItemName(result) + "_from_campfire_cooking"));
    }
}
