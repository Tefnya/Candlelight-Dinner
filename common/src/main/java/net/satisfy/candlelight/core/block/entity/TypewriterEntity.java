package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class TypewriterEntity extends BlockEntity {

    public static final String PAPER_KEY = "paper";

    ItemStack paper = ItemStack.EMPTY;

    public TypewriterEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.TYPE_WRITER_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getPaper() {
        return paper;
    }

    public void addPaper(ItemStack itemStack) {
        paper = itemStack;
        setChanged();
    }

    public void removePaper() {
        paper = ItemStack.EMPTY;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!paper.isEmpty()) {
            writePaper(compoundTag, paper, provider);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        paper = readPaper(compoundTag, provider);
    }

    public void writePaper(CompoundTag nbt, ItemStack stack, HolderLookup.Provider provider) {
        if (stack == null || stack.isEmpty()) return;
        CompoundTag tag = new CompoundTag();
        stack.save(provider, tag);
        nbt.put(PAPER_KEY, tag);
    }

    public ItemStack readPaper(CompoundTag nbt, HolderLookup.Provider provider) {
        if (nbt.contains(PAPER_KEY)) {
            CompoundTag tag = nbt.getCompound(PAPER_KEY);
            if (!tag.isEmpty()) {
                return ItemStack.parseOptional(provider, tag);
            }
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag toInitialChunkDataNbt(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }
}
