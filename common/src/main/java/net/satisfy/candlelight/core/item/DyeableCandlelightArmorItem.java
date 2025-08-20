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
import org.jetbrains.annotations.NotNull;

public class DyeableCandlelightArmorItem extends ArmorItem {
    private final ResourceLocation getTexture;
    private final int defaultColor;

    public DyeableCandlelightArmorItem(Holder<ArmorMaterial> armorMaterial, Type type, int color, Properties properties, ResourceLocation getTexture) {
        super(armorMaterial, type, properties);
        this.defaultColor = color;
        this.getTexture = getTexture;
    }

    public int getColor(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getCompound("display");
        return (compoundTag != null && compoundTag.contains("color", 99)) ? compoundTag.getInt("color") : this.defaultColor;
    }

    public ResourceLocation getTexture() {
        return getTexture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }
}
