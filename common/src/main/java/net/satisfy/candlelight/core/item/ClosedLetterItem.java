package net.satisfy.candlelight.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClosedLetterItem extends Item {
    public ClosedLetterItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            String title = nbt.getString("letter_title");
            String sender = nbt.getString("letter_sender");

            if (!title.isBlank() && !sender.isBlank()) {
                tooltip.add(Component.translatable("tooltip.candlelight.letter.line", title, sender).withStyle(ChatFormatting.GRAY));
            }
        }

        tooltip.add(Component.translatable("item.candlelight.letter.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack output = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());

        if (stack.hasTag()) {
            assert stack.getTag() != null;
            CompoundTag tag = stack.getTag().copy();
            tag.remove("letter_title");
            tag.remove("letter_sender");
            output.setTag(tag);
        }

        if (this == ObjectRegistry.LOVE_LETTER_CLOSED.get() && level.isClientSide()) {
            for (int i = 0; i < 20; i++) {
                double dx = player.getX() + (level.random.nextDouble() - 0.5) * 0.6;
                double dy = player.getY() + 1.0 + level.random.nextDouble() * 0.3;
                double dz = player.getZ() + (level.random.nextDouble() - 0.5) * 0.6;
                level.addParticle(ParticleTypes.HEART, dx, dy, dz, 0, 0.05, 0);
            }
        }

        player.setItemInHand(hand, output);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
