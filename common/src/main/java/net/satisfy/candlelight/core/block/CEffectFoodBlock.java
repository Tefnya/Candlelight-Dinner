package net.satisfy.candlelight.core.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.block.entity.CEffectFoodBlockEntity;
import net.satisfy.farm_and_charm.core.block.EffectFoodBlock;
import net.satisfy.farm_and_charm.core.item.food.EffectFoodHelper;

public class CEffectFoodBlock extends EffectFoodBlock {
    public CEffectFoodBlock(Properties properties) {
        super(properties);
    }

    public CEffectFoodBlock(Properties properties, int maxBites, FoodProperties food) {
        super(properties, maxBites, food);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CEffectFoodBlockEntity e) {
            e.addEffects(EffectFoodHelper.getEffects(stack));
        } else {
            super.setPlacedBy(level, pos, state, placer, stack);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CEffectFoodBlockEntity(pos, state);
    }
}
