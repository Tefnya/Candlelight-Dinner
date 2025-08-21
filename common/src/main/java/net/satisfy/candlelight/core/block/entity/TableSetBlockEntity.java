package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;

public class TableSetBlockEntity extends StorageBlockEntity {
    private ItemStack effectStack = ItemStack.EMPTY;
    private int effectDuration = 0;

    public TableSetBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    public TableSetBlockEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state, size);
    }

    @Override
    public BlockEntityType<?> getType() {
        return EntityTypeRegistry.TABLE_SET_BLOCK_ENTITY.get();
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return getType().isValid(blockState);
    }

    public ItemStack getEffectStack() {
        return this.effectStack;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }

    public void setEffectStack(ItemStack stack, int duration) {
        this.effectStack = stack;
        this.effectDuration = duration;
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (!this.effectStack.isEmpty()) {
            if (this.level != null) {
                this.effectStack = ItemStack.parseOptional(this.level.registryAccess(), compoundTag.getCompound("EffectStack"));
                this.effectDuration = compoundTag.getInt("EffectDuration");
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        if (!this.effectStack.isEmpty()) {
            if (this.level != null) {
                nbt.put("EffectStack", this.effectStack.save(this.level.registryAccess(), new CompoundTag()));
                nbt.putInt("EffectDuration", this.effectDuration);
            }
        }
    }
}
