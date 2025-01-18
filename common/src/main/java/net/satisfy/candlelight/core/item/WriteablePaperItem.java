package net.satisfy.candlelight.core.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.candlelight.core.util.CandlelightUtil;
import org.jetbrains.annotations.NotNull;

public class WriteablePaperItem extends Item {
    public WriteablePaperItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (world.isClientSide()) {
            CandlelightUtil.setNotePaperScreen(user, itemStack, hand);
        } else {
            user.containerMenu.broadcastChanges();
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
