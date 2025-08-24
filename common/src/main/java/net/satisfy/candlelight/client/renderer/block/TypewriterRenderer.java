package net.satisfy.candlelight.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.client.model.TypewriterModel;
import net.satisfy.candlelight.core.block.TypewriterBlock;
import net.satisfy.candlelight.core.block.entity.TypewriterEntity;

public class TypewriterRenderer implements BlockEntityRenderer<TypewriterEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("candlelight", "textures/entity/typewriter.png");
    private final ModelPart typewriter;
    private final ModelPart paper;
    private final ModelPart paperWritten;

    public TypewriterRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(TypewriterModel.LAYER_LOCATION);
        this.typewriter = root.getChild("typewriter");
        this.paper = this.typewriter.getChild("paper");
        this.paperWritten = this.typewriter.getChild("paper_written");
    }

    @Override
    public void render(TypewriterEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        Level level = be.getLevel();
        if (level == null) return;
        BlockState state = level.getBlockState(be.getBlockPos());
        if (!(state.getBlock() instanceof TypewriterBlock)) return;

        poseStack.pushPose();

        Direction facing = state.getValue(TypewriterBlock.FACING);
        float rotY = switch (facing) {
            case EAST -> 270f;
            case SOUTH -> 0f;
            case WEST -> 90f;
            default -> 180f;
        };

        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
        poseStack.translate(-0.5, -1.5, 0.5);

        VertexConsumer vc = buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

        int full = state.getValue(TypewriterBlock.FULL);
        this.paper.visible = false;
        this.paperWritten.visible = false;
        if (full == 1) this.paper.visible = true;
        else if (full == 2) this.paperWritten.visible = true;

        this.typewriter.render(poseStack, vc, light, overlay);

        poseStack.popPose();
    }
}
