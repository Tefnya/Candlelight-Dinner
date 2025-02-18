package satisfyu.candlelight.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.registry.BlockEntityRegistry;

public class SideBoardBlockEntity extends ChestBlockEntity {
    public SideBoardBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.SIDEBOARD.get(), blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.sideboard");
    }


}
