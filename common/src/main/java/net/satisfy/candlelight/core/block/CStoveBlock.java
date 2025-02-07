package net.satisfy.candlelight.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.block.entity.CStoveBlockEntity;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.farm_and_charm.core.block.StoveBlock;

public class CStoveBlock extends StoveBlock {
    public CStoveBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CStoveBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClientSide && type == EntityTypeRegistry.STOVE_BLOCK_ENTITY.get()) {
            return (lvl, pos, blkState, t) -> {
                if (t instanceof CStoveBlockEntity stoveBlock) {
                    stoveBlock.tick(lvl, pos, blkState, stoveBlock);
                }
            };
        }
        return null;
    }
}