package com.daderpduck.hallucinocraft.blocks;

import com.daderpduck.hallucinocraft.items.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TallCropsBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height",0,2);
    public static final BooleanProperty CROP = BooleanProperty.create("crop");
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public TallCropsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.COCA_SEEDS.get();
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !isMaxAge(blockState) || !isMaxHeight(blockState);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel world, BlockPos blockPos, RandomSource random) {
        if (!world.isAreaLoaded(blockPos, 1)) return;
        if (world.getRawBrightness(blockPos, 0) >= 9) {
            int age = getAge(blockState);
            int height = getHeight(blockState);
            if (age < getMaxAge()) {
                float f = getGrowthSpeed(this, world, blockPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, blockPos, blockState, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    world.setBlock(blockPos, blockState.setValue(getAgeProperty(), age + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, blockPos, blockState);
                }
            } else if (height < getMaxHeight() && world.isEmptyBlock(blockPos.above())) {
                float f = getGrowthSpeed(this, world, blockPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, blockPos, blockState, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    world.setBlockAndUpdate(blockPos.above(), blockState.setValue(getAgeProperty(), 0).setValue(getHeightProperty(), getHeight(blockState) + 1));
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, blockPos, blockState);
                }
            }
        }

    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxHeight() {
        return 2;
    }

    public IntegerProperty getHeightProperty() {
        return HEIGHT;
    }

    public int getHeight(BlockState blockState) {
        return blockState.getValue(getHeightProperty());
    }

    public boolean isMaxHeight(BlockState blockState) {
        return blockState.getValue(getHeightProperty()) >= getMaxHeight();
    }

    public BooleanProperty getCropProperty() {
        return CROP;
    }

    public boolean isCrop(BlockState blockState) {
        return blockState.getValue(getCropProperty());
    }

    @Override
    protected int getBonemealAgeIncrease(Level world) {
        return super.getBonemealAgeIncrease(world)/3;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader blockReader, BlockPos blockPos, BlockState blockState, boolean isClient) {
        return !isMaxAge(blockState) || (!isMaxHeight(blockState) && !blockReader.getBlockState(blockPos.above()).getBlock().equals(this));
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader worldReader, BlockPos blockPos) {
        if (getHeight(blockState) > 0) {
            BlockState belowState = worldReader.getBlockState(blockPos.below());
            return belowState.getBlock().equals(this) && getHeight(belowState) < getHeight(blockState);
        } else if (!isCrop(blockState)) {
            return worldReader.getBlockState(blockPos.below()).is(Blocks.GRASS_BLOCK);
        } else {
            return super.canSurvive(blockState, worldReader, blockPos);
        }
    }

    @Override
    public void growCrops(Level world, BlockPos blockPos, BlockState blockState) {
        if (getAge(blockState) < getMaxAge()) {
            int i = getAge(blockState) + getBonemealAgeIncrease(world);
            int j = getMaxAge();
            if (i > j) {
                i = j;
            }

            world.setBlock(blockPos, blockState.setValue(getAgeProperty(), i), 2);
        } else if (getHeight(blockState) < getMaxHeight() && world.isEmptyBlock(blockPos.above())) {
            world.setBlockAndUpdate(blockPos.above(), blockState.setValue(getAgeProperty(), 0).setValue(getHeightProperty(), getHeight(blockState) + 1));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(HEIGHT);
        builder.add(CROP);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockReader, BlockPos blockPos, CollisionContext context) {
        return SHAPE_BY_AGE[blockState.getValue(getAgeProperty())];
    }
}
