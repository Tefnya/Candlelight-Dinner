package net.satisfy.candlelight.core.registry;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;
import org.joml.Vector4i;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialRegistry {
    public static final Holder<ArmorMaterial> COOK_ARMOR =
            createMaterial("cook", new Vector4i(1, 1, 1, 1), Ingredient.of(Items.LEATHER), true);
    public static final Holder<ArmorMaterial> RING_ARMOR =
            createMaterial("gold_ring", new Vector4i(1, 1, 1, 1), Ingredient.of(Items.GOLD_INGOT), true);

    private static Holder<ArmorMaterial> createMaterial(String name, Vector4i values, Ingredient repairIngredient, boolean dyeable) {
        ArmorMaterial m = register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, values.x());
            map.put(ArmorItem.Type.LEGGINGS, values.y());
            map.put(ArmorItem.Type.CHESTPLATE, values.z());
            map.put(ArmorItem.Type.HELMET, values.w());
            map.put(ArmorItem.Type.BODY, 0);
        }), () -> repairIngredient, List.of(
                new ArmorMaterial.Layer(CandlelightIdentifier.identifier(name), "", true),
                new ArmorMaterial.Layer(CandlelightIdentifier.identifier(name), "_overlay", dyeable)
        ));
        return BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(m);
    }

    private static ArmorMaterial register(EnumMap<ArmorItem.Type, Integer> src, Supplier<Ingredient> repair, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            Integer v = src.get(type);
            map.put(type, v != null ? v : 0);
        }
        return new ArmorMaterial(map, 15, SoundEvents.ARMOR_EQUIP_LEATHER, repair, layers, (float) 0.0, (float) 0.0);
    }
}
