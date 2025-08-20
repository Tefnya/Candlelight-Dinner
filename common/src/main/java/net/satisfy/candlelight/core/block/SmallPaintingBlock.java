package net.satisfy.candlelight.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings({"unused", "deprecation"})
public class SmallPaintingBlock extends WallDecorationBlock {
    public static final IntegerProperty PAINTING = IntegerProperty.create("painting", 0, 6);


    public SmallPaintingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(PAINTING, 0));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.isDiscrete()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (world.isClientSide) {
            if (switchPaintings(world, pos, state, player).consumesAction()) {
                return ItemInteractionResult.SUCCESS;
            }
        }
        return switchPaintings(world, pos, state, player);
    }

    private ItemInteractionResult switchPaintings(LevelAccessor world, BlockPos pos, BlockState state, Player player) {
        world.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.5f, world.getRandom().nextFloat() * 0.1f + 0.9f);
        int i = state.getValue(PAINTING);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        int nextStage = i + 1;
        if (nextStage <= 6) {
            world.setBlock(pos, state.setValue(PAINTING, nextStage), Block.UPDATE_ALL);
        } else {
            world.setBlock(pos, state.setValue(PAINTING, 0), Block.UPDATE_ALL);
        }

        return ItemInteractionResult.SUCCESS;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PAINTING);
    }

}