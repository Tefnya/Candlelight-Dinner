package net.satisfy.candlelight.core.util;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.satisfy.candlelight.client.gui.NotePaperGui;
import net.satisfy.candlelight.client.gui.SignedPaperGui;
import net.satisfy.candlelight.client.gui.TypeWriterGui;
import net.satisfy.candlelight.core.block.entity.TypeWriterEntity;
import org.joml.Vector3i;

public class CandlelightUtil {
    public static void setNotePaperScreen(Player user, ItemStack stack, InteractionHand hand) {
        Minecraft.getInstance().setScreen(new NotePaperGui(user, stack, hand));
    }


    public static void setTypeWriterScreen(Player user, TypeWriterEntity typeWriterEntity) {
        if (user instanceof LocalPlayer)
            Minecraft.getInstance().setScreen(new TypeWriterGui(user, typeWriterEntity));
    }

    public static void setSignedPaperScreen(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SignedPaperGui(new SignedPaperGui.WrittenPaperContents(stack)));
    }

    public static void registerColorArmor(Item item, int defaultColor) {
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> tintIndex == 0 ? getColor(stack, defaultColor) : 0xFFFFFFFF, item);
    }

    static int getColor(ItemStack itemStack, int defaultColor) {
        DyedItemColor dyed = itemStack.get(DataComponents.DYED_COLOR);
        if (dyed != null) return 0xFF000000 | dyed.rgb();
        CompoundTag displayTag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getCompound("display");
        if (displayTag.contains("color", Tag.TAG_ANY_NUMERIC)) return 0xFF000000 | displayTag.getInt("color");
        Vector3i rgb = new Vector3i((defaultColor >> 16) & 255, (defaultColor >> 8) & 255, defaultColor & 255);
        return (255 << 24) | (rgb.x() << 16) | (rgb.y() << 8) | rgb.z();
    }
}
