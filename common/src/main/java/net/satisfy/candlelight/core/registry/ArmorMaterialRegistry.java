package net.satisfy.candlelight.core.registry;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialRegistry {

    private static final int ENCHANTMENT_VALUE = 15;
    private static final Holder<SoundEvent> EQUIP_SOUND = SoundEvents.ARMOR_EQUIP_LEATHER;
    private static final float TOUGHNESS = 0.0F;
    private static final float KNOCKBACK_RESISTANCE = 0.0F;

    public static final Holder<ArmorMaterial> COOK_ARMOR =
            createMaterial("cook", Ingredient.of(Items.LEATHER), true);
    public static final Holder<ArmorMaterial> RING_ARMOR =
            createMaterial("gold_ring", Ingredient.of(Items.GOLD_INGOT), true);

    private static Holder<ArmorMaterial> createMaterial(String name, Ingredient repairIngredient, boolean dyeable) {
        ArmorMaterial armorMaterial = register(name, Util.make(new EnumMap(ArmorItem.Type.class), (enumMap) -> {
            enumMap.put(ArmorItem.Type.BOOTS, 112);
            enumMap.put(ArmorItem.Type.LEGGINGS, 136);
            enumMap.put(ArmorItem.Type.CHESTPLATE, 144);
            enumMap.put(ArmorItem.Type.HELMET, 128);
            enumMap.put(ArmorItem.Type.BODY, 3);
        }), ENCHANTMENT_VALUE, EQUIP_SOUND, TOUGHNESS, KNOCKBACK_RESISTANCE, () -> {
            return repairIngredient;
        }, List.of(new ArmorMaterial.Layer(CandlelightIdentifier.identifier(name), "", true), new ArmorMaterial.Layer(CandlelightIdentifier.identifier(name), "_overlay", dyeable)));
        return BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
    }

    private static ArmorMaterial register(String string, EnumMap<ArmorItem.Type, Integer> enumMap, int i, Holder<SoundEvent> arg, float f, float g, Supplier<Ingredient> supplier, List<ArmorMaterial.Layer> list) {
        EnumMap<ArmorItem.Type, Integer> enumMap2 = new EnumMap<>(ArmorItem.Type.class);
        ArmorItem.Type[] var9 = ArmorItem.Type.values();

        for (ArmorItem.Type type : var9) {
            enumMap2.put(type, enumMap.get(type));
        }

        return new ArmorMaterial(enumMap2, i, arg, supplier, list, f, g);
    }
}
