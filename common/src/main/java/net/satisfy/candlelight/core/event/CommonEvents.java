package net.satisfy.candlelight.core.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CommonEvents {
    private static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    public static void init() {
        PlayerEvent.ATTACK_ENTITY.register(CommonEvents::attack);

        TickEvent.PLAYER_POST.register((player) -> {
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            boolean holdingLoveLetter = main.is(ObjectRegistry.LOVE_LETTER_CLOSED.get()) || off.is(ObjectRegistry.LOVE_LETTER_CLOSED.get());

            if (holdingLoveLetter && player.level().isClientSide() && player.tickCount % 5 == 0) {
                double dx = player.getX() - player.xOld;
                double dz = player.getZ() - player.zOld;
                boolean isMoving = dx * dx + dz * dz > 0.001;

                if (isMoving && !player.isShiftKeyDown()) {
                    double yaw = Math.toRadians(player.getYRot());
                    double behindX = player.getX() - (Math.sin(yaw) * 0.5);
                    double behindZ = player.getZ() + (Math.cos(yaw) * 0.5);
                    double y = player.getY() + 0.1;

                    player.level().addParticle(ParticleTypes.HEART, behindX, y, behindZ, 0, 0.02, 0);
                }
            }
        });
    }

    public static EventResult attack(Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ObjectRegistry.COOKING_PAN_ITEM.get())) {
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEventRegistry.COOKING_POT_HIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            target.hurt(level.damageSources().generic(), 5.0F);
            itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));

            itemStack.addAttributeModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.0, AttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);

            if (target instanceof Mob mob) {
                mob.setTarget(player);
            }

            return EventResult.interruptTrue();
        }
        return EventResult.pass();
    }
}
