package net.satisfy.candlelight.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.farm_and_charm.FarmAndCharm;

public class TypewriterModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Candlelight.MOD_ID, "typewriter"), "main");
    private final ModelPart typewriter;
    private final ModelPart keyboard;
    private final ModelPart space;
    private final ModelPart enter;
    private final ModelPart body;
    private final ModelPart roller;
    private final ModelPart paper;
    private final ModelPart paper_written;

    public TypewriterModel(ModelPart root) {
        this.typewriter = root.getChild("typewriter");
        this.keyboard = this.typewriter.getChild("keyboard");
        this.space = this.keyboard.getChild("space");
        this.enter = this.keyboard.getChild("enter");
        this.body = this.typewriter.getChild("body");
        this.roller = this.typewriter.getChild("roller");
        this.paper = this.typewriter.getChild("paper");
        this.paper_written = this.typewriter.getChild("paper_written");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition typewriter = partdefinition.addOrReplaceChild("typewriter", CubeListBuilder.create(), PartPose.offset(3.0F, 22.0F, -6.0F));

        PartDefinition keyboard = typewriter.addOrReplaceChild("keyboard", CubeListBuilder.create().texOffs(8, 38).addBox(0.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 38).addBox(-9.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(0.5F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-2.5F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-8.5F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-5.5F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 38).addBox(-6.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 38).addBox(-3.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition space = keyboard.addOrReplaceChild("space", CubeListBuilder.create().texOffs(0, 29).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition enter = keyboard.addOrReplaceChild("enter", CubeListBuilder.create().texOffs(9, 38).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 2.0F));

        PartDefinition body = typewriter.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -2.0F, -1.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-13.0F, -7.0F, 5.0F, 14.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-13.0F, -11.0F, 11.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 32).addBox(-1.0F, -11.0F, 11.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 29).addBox(-11.0F, -11.0F, 12.1F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.0F, 0.0F));

        PartDefinition roller = typewriter.addOrReplaceChild("roller", CubeListBuilder.create().texOffs(16, 36).addBox(-4.0F, 1.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -7.0F, 10.0F));

        PartDefinition paper = typewriter.addOrReplaceChild("paper", CubeListBuilder.create().texOffs(20, 31).addBox(-4.0F, 1.0F, 6.0F, 8.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -11.0F, 6.0F));
        PartDefinition paper_written = typewriter.addOrReplaceChild("paper_written", CubeListBuilder.create().texOffs(20, 31).addBox(-4.0F, 1.0F, 6.0F, 8.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -11.0F, 6.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        typewriter.render(poseStack, vertexConsumer, i, j, k);
    }
}