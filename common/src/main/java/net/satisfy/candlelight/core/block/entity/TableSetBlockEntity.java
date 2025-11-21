package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TableSetBlockEntity extends StorageBlockEntity {
    private ItemStack effectStack = ItemStack.EMPTY;
    private int effectDuration = 0;

    public TableSetBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    public TableSetBlockEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state, size);
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
        if (this.level != null && compoundTag.contains("EffectStack")) {
            this.effectStack = ItemStack.parseOptional(this.level.registryAccess(), compoundTag.getCompound("EffectStack"));
            this.effectDuration = compoundTag.getInt("EffectDuration");
        } else {
            this.effectStack = ItemStack.EMPTY;
            this.effectDuration = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        if (this.level != null && !this.effectStack.isEmpty()) {
            nbt.put("EffectStack", this.effectStack.save(this.level.registryAccess(), new CompoundTag()));
            nbt.putInt("EffectDuration", this.effectDuration);
        }
    }
}