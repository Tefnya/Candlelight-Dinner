package net.satisfy.candlelight.core.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.satisfy.candlelight.client.model.CookingBootsModel;
import net.satisfy.candlelight.client.model.CookingChestplateModel;
import net.satisfy.candlelight.client.model.CookingHatModel;
import net.satisfy.candlelight.client.model.CookingLeggingsModel;
import net.satisfy.candlelight.client.model.DressChestplateModel;
import net.satisfy.candlelight.client.model.FlowerCrownModel;
import net.satisfy.candlelight.client.model.SuitLeggingsModel;
import net.satisfy.candlelight.client.model.TieModel;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ArmorRegistry {
    private static final Map<Item, TieModel<LivingEntity>> tieModels = new HashMap<>();
    private static final Map<Item, FlowerCrownModel<LivingEntity>> crownModels = new HashMap<>();
    private static final Map<Item, CookingHatModel<LivingEntity>> hatModels = new HashMap<>();
    private static final Map<Item, CookingChestplateModel<LivingEntity>> chestplateModels = new HashMap<>();
    private static final Map<Item, CookingLeggingsModel<LivingEntity>> leggingsModels = new HashMap<>();
    private static final Map<Item, CookingBootsModel<LivingEntity>> bootsModels = new HashMap<>();
    private static final Map<Item, DressChestplateModel<LivingEntity>> dressModels = new HashMap<>();
    private static final Map<Item, SuitLeggingsModel<LivingEntity>> suitModels = new HashMap<>();

    public static Model getHatModel(Item item, ModelPart baseHead, HumanoidModel<?> original) {
        if (item != ObjectRegistry.COOKING_HAT.get()) return original;

        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        CookingHatModel<LivingEntity> model = hatModels.computeIfAbsent(item, key -> new CookingHatModel<>(modelSet.bakeLayer(CookingHatModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyHead(baseHead);

        return model;
    }

    public static Model getCrownModel(Item item, ModelPart baseHead, HumanoidModel<?> original) {
        if (item != ObjectRegistry.FLOWER_CROWN.get()) return original;

        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        FlowerCrownModel<LivingEntity> model = crownModels.computeIfAbsent(item, key -> new FlowerCrownModel<>(modelSet.bakeLayer(FlowerCrownModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyHead(baseHead);

        return model;
    }

    public static Model getTieModel(Item item, ModelPart baseHead, ModelPart baseBody, HumanoidModel<?> original) {
        if (item != ObjectRegistry.NECKTIE.get()) return original;

        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        TieModel<LivingEntity> model = tieModels.computeIfAbsent(item, key -> new TieModel<>(modelSet.bakeLayer(TieModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyHead(baseHead, baseBody);

        return model;
    }

    public static Model getChestplateModel(Item item, ModelPart body, ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg, HumanoidModel<?> original) {
        if (item != ObjectRegistry.CHEFS_JACKET.get() && item != ObjectRegistry.FORMAL_SHIRT.get() && item != ObjectRegistry.SHIRT.get()) return original;

        CookingChestplateModel<LivingEntity> model = chestplateModels.computeIfAbsent(item, key -> new CookingChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CookingChestplateModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyBody(body, leftArm, rightArm, leftLeg, rightLeg);

        return model;
    }

    public static Model getDressModel(Item item, ModelPart body, ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg, HumanoidModel<?> original) {
        if (item != ObjectRegistry.DRESS.get()) return original;

        DressChestplateModel<LivingEntity> model = dressModels.computeIfAbsent(item, key -> new DressChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DressChestplateModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyBody(body, leftArm, rightArm, leftLeg, rightLeg);

        return model;
    }

    public static Model getLeggingsModel(Item item, ModelPart rightLeg, ModelPart leftLeg, HumanoidModel<?> original) {
        if (item != ObjectRegistry.CHEFS_PANTS.get() && item != ObjectRegistry.TROUSERS_AND_VEST.get()) return original;

        CookingLeggingsModel<LivingEntity> model = leggingsModels.computeIfAbsent(item, key -> new CookingLeggingsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CookingLeggingsModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyLegs(rightLeg, leftLeg);

        return model;
    }

    public static Model getSuitModel(Item item, ModelPart rightLeg, ModelPart leftLeg, HumanoidModel<?> original) {
        if (item != ObjectRegistry.CHEFS_PANTS.get() && item != ObjectRegistry.TROUSERS_AND_VEST.get()) return original;

        SuitLeggingsModel<LivingEntity> model = suitModels.computeIfAbsent(item, key -> new SuitLeggingsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SuitLeggingsModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyLegs(rightLeg, leftLeg);

        return model;
    }

    public static Model getBootsModel(Item item, ModelPart rightLeg, ModelPart leftLeg, HumanoidModel<?> original) {
        if (item != ObjectRegistry.CHEFS_BOOTS.get()) return original;

        CookingBootsModel<LivingEntity> model = bootsModels.computeIfAbsent(item, key -> new CookingBootsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CookingBootsModel.LAYER_LOCATION)));

        model.young = original.young;
        model.copyLegs(rightLeg, leftLeg);

        return model;
    }
}