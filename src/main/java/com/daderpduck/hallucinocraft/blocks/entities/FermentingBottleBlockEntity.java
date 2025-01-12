//package com.daderpduck.hallucinocraft.blocks.entities;
//
//import com.daderpduck.hallucinocraft.blocks.FermentingBottleBlock;
//import com.daderpduck.hallucinocraft.recipe.FermentingBottleContainer;
//import com.daderpduck.hallucinocraft.recipe.FermentingBottleRecipe;
//import net.minecraft.MethodsReturnNonnullByDefault;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.protocol.Packet;
//import net.minecraft.network.protocol.game.ClientGamePacketListener;
//import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
//import net.minecraft.world.Containers;
//import net.minecraft.world.WorldlyContainer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.fluids.FluidAttributes;
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
//import net.minecraftforge.fluids.capability.IFluidHandler;
//import net.minecraftforge.fluids.capability.templates.FluidTank;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@MethodsReturnNonnullByDefault
//@ParametersAreNonnullByDefault
//public class FermentingBottleBlockEntity extends BlockEntity implements WorldlyContainer {
//    private static final int SIZE = 5;
//    private static final int[] SLOTS = IntStream.range(0, SIZE).toArray();
//    private int progress = 0;
//    private final ItemStackHandler itemHandler = new ItemStackHandler(SIZE) {
//        @Override
//        protected void onContentsChanged(int slot) {
//            inventoryChanged();
//        }
//
//        @Override
//        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
//            return 1;
//        }
//
//        @Override
//        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
//            return slot == 0 ? FermentingBottleBlock.canUseAsLid(stack) : super.isItemValid(slot, stack);
//        }
//    };
//    private final FluidTank fluidTank = new FluidTank(FluidAttributes.BUCKET_VOLUME) {
//        @Override
//        protected void onContentsChanged() {
//            inventoryChanged();
//        }
//    };
//
//    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
//    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
//
//    public FermentingBottleBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
//        super(ModBlockEntityTypes.FERMENTING_BOTTLE, pWorldPosition, pBlockState);
//    }
//
//    public static void tick(Level level, BlockPos pos, BlockState state, FermentingBottleBlockEntity blockEntity) {
//        Optional<FermentingBottleRecipe> recipeOptional = getRecipe(blockEntity);
//
//        if (recipeOptional.isPresent()) {
//            FermentingBottleRecipe recipe = recipeOptional.get();
//
//            if (blockEntity.progress++ > recipe.getDuration()) {
//                blockEntity.progress = 0;
//
//                ItemStack[] resultItems = recipe.assembleItems();
//                for (int i = 0; i < resultItems.length; i++) {
//                    blockEntity.setItem(i + 1, resultItems[i]);
//                }
//                blockEntity.setFluid(recipe.assembleFluid());
//                level.sendBlockUpdated(pos, state, state, 3);
//                level.levelEvent(1035, pos, 0); // brewing stand sound
//            }
//
//            setChanged(level, pos, state);
//        } else if (blockEntity.progress != 0) {
//            blockEntity.progress = 0;
//            setChanged(level, pos, state);
//        }
//    }
//
//    private static Optional<FermentingBottleRecipe> getRecipe(FermentingBottleBlockEntity blockEntity) {
//        if (blockEntity.level == null) return Optional.empty();
//
//        boolean hasAnyItem = blockEntity.lazyItemHandler.map(iItemHandler -> {
//            for (int i = 1; i < iItemHandler.getSlots(); i++) {
//                if (!iItemHandler.getStackInSlot(i).isEmpty()) return true;
//            }
//            return false;
//        }).orElse(false);
//        if (!hasAnyItem) return Optional.empty();
//
//        boolean hasFluid = blockEntity.lazyFluidHandler.map(iFluidHandler -> !iFluidHandler.getFluidInTank(0).isEmpty()).orElse(false);
//        if (!hasFluid) return Optional.empty();
//
//        FermentingBottleContainer inventory = new FermentingBottleContainer(blockEntity.itemHandler.getSlots() - 1, 1);
//        inventory.setLid(FermentingBottleBlock.getLidFromItem(blockEntity.getLidItem()));
//        inventory.setFluid(0, blockEntity.getFluid());
//        blockEntity.lazyItemHandler.ifPresent(iItemHandler -> {
//            for (int i = 1; i < iItemHandler.getSlots(); i++) {
//                inventory.setItem(i - 1, iItemHandler.getStackInSlot(i));
//            }
//        });
//
//        return blockEntity.level.getRecipeManager().getRecipeFor(FermentingBottleRecipe.Type.INSTANCE, inventory, blockEntity.level);
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//            return lazyItemHandler.cast();
//        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
//            return lazyFluidHandler.cast();
//        }
//
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void onLoad() {
//        super.onLoad();
//        lazyItemHandler = LazyOptional.of(() -> itemHandler);
//        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        lazyItemHandler.invalidate();
//        lazyFluidHandler.invalidate();
//    }
//
//    private void inventoryChanged() {
//        if (level == null) return;
//        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
//
//        setChanged();
//    }
//
//    @Override
//    public void setChanged() {
//        super.setChanged();
//
//        if (level != null && lazyItemHandler.isPresent()) {
//            FermentingBottleBlock.Lid lid = getBlockState().getValue(FermentingBottleBlock.LID);
//            FermentingBottleBlock.Lid expectedLid = FermentingBottleBlock.getLidFromItem(getItem(0));
//            if (lid != expectedLid) {
//                BlockState blockState = getBlockState().setValue(FermentingBottleBlock.LID, expectedLid);
//                level.setBlockAndUpdate(getBlockPos(), blockState);
//            }
//        }
//    }
//
//    @Override
//    public void load(CompoundTag pTag) {
//        itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
//        fluidTank.readFromNBT(pTag.getCompound("Fluid"));
//        progress = pTag.getInt("Progress");
//        super.load(pTag);
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag pTag) {
//        super.saveAdditional(pTag);
//        pTag.put("Inventory", itemHandler.serializeNBT());
//        pTag.put("Fluid", fluidTank.writeToNBT(new CompoundTag()));
//        pTag.putInt("Progress", progress);
//    }
//
//    @Override
//    public CompoundTag getUpdateTag() {
//        return serializeNBT();
//    }
//
//    @Nullable
//    @Override
//    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    public ItemStack getLidItem() {
//        return lazyItemHandler.map(iItemHandler -> iItemHandler.getStackInSlot(0)).orElse(ItemStack.EMPTY);
//    }
//
//    public ItemStack insertLidItem(ItemStack itemStack) {
//        return lazyItemHandler.map(iItemHandler -> itemHandler.insertItem(0, itemStack, false)).orElse(itemStack);
//    }
//
//    public void emptyLidItem() {
//        lazyItemHandler.ifPresent(iItemHandler -> ((ItemStackHandler)iItemHandler).setStackInSlot(0, ItemStack.EMPTY));
//    }
//
//    public FluidStack getFluid() {
//        return lazyFluidHandler.map(iFluidHandler -> iFluidHandler.getFluidInTank(0)).orElse(FluidStack.EMPTY);
//    }
//
//    public void setFluid(FluidStack fluidStack) {
//        lazyFluidHandler.ifPresent(iFluidHandler -> ((FluidTank)iFluidHandler).setFluid(fluidStack));
//    }
//
//    public ItemStack pushIngredient(ItemStack itemStack) {
//        for (int i = 1; i < SIZE; i++) {
//            if (getItem(i).isEmpty()) {
//                int finalI = i;
//                return lazyItemHandler.map(iItemHandler -> iItemHandler.insertItem(finalI, itemStack, false)).orElse(itemStack);
//            }
//        }
//        return itemStack;
//    }
//
//    public ItemStack popIngredient() {
//        for (int i = SIZE - 1; i >= 1; i--) {
//            if (!getItem(i).isEmpty()) {
//                int finalI = i;
//                return lazyItemHandler.map(iItemHandler -> iItemHandler.extractItem(finalI, 1, false)).orElse(ItemStack.EMPTY);
//            }
//        }
//        return ItemStack.EMPTY;
//    }
//
//    public void drops() {
//        assert level != null;
//        Containers.dropContents(level, worldPosition, this);
//    }
//
//    // TODO: Fix automation bugs
//
//    @Override
//    public int getContainerSize() {
//        return SIZE;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        for (int i = 0; i < SIZE; i++) {
//            if (!getItem(i).isEmpty()) return false;
//        }
//        return true;
//    }
//
//    @Override
//    public ItemStack getItem(int pSlot) {
//        return lazyItemHandler.map(iItemHandler -> iItemHandler.getStackInSlot(pSlot)).orElse(ItemStack.EMPTY);
//    }
//
//    @Override
//    public ItemStack removeItem(int pSlot, int pAmount) {
//        return lazyItemHandler.map(iItemHandler -> iItemHandler.extractItem(pSlot, pAmount, false)).orElse(ItemStack.EMPTY);
//    }
//
//    @Override
//    public ItemStack removeItemNoUpdate(int pSlot) {
//        int count = getItem(pSlot).getCount();
//        return removeItem(pSlot, count);
//    }
//
//    @Override
//    public void setItem(int pSlot, ItemStack pStack) {
//        lazyItemHandler.ifPresent(iItemHandler -> ((ItemStackHandler)iItemHandler).setStackInSlot(pSlot, pStack));
//    }
//
//    @Override
//    public boolean stillValid(Player pPlayer) {
//        assert this.level != null;
//        if (this.level.getBlockEntity(this.worldPosition) != this) {
//            return false;
//        } else {
//            return !(pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
//        }
//    }
//
//    @Override
//    public void clearContent() {
//        lazyItemHandler.ifPresent(iItemHandler -> {
//            for (int i = 0; i < SIZE; i++) {
//                ((ItemStackHandler)iItemHandler).setStackInSlot(i, ItemStack.EMPTY);
//            }
//        });
//    }
//
//    @Override
//    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
//        return itemHandler.isItemValid(pIndex, pStack);
//    }
//
//    @Override
//    public int[] getSlotsForFace(Direction pSide) {
//        return SLOTS;
//    }
//
//    @Override
//    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
//        return canPlaceItem(pIndex, pItemStack);
//    }
//
//    @Override
//    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
//        return pDirection == Direction.DOWN && pIndex != 0;
//    }
//}
