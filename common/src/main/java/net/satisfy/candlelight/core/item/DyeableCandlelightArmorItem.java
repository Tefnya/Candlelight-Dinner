package net.satisfy.candlelight.core.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;

public class DyeableCandlelightArmorItem extends ArmorItem {
    private final ResourceLocation texture;
    private final int defaultColor;

    public DyeableCandlelightArmorItem(Holder<ArmorMaterial> armorMaterial, Type type, int color, Properties properties, ResourceLocation texture) {
        super(armorMaterial, type, properties);
        this.defaultColor = color;
        this.texture = texture;
    }

    public int getColor(ItemStack itemStack) {
        DyedItemColor dyed = itemStack.get(DataComponents.DYED_COLOR);
        if (dyed != null) return dyed.rgb();
        CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getCompound("display");
        if (tag.contains("color", 99)) return tag.getInt("color");
        Vector3i rgb = new Vector3i((defaultColor >> 16) & 255, (defaultColor >> 8) & 255, defaultColor & 255);
        return (rgb.x() << 16) | (rgb.y() << 8) | rgb.z();
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }
}
