package net.satisfy.candlelight.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    private final ModelPart keyboard;
    private final ModelPart space;
    private final ModelPart enter;
    private final ModelPart roller;
    private final ModelPart paper;
    private final ModelPart paperWritten;
    private final List<ModelPart> keyParts;

    public TypewriterRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(TypewriterModel.LAYER_LOCATION);
        this.typewriter = root.getChild("typewriter");
        this.keyboard = this.typewriter.getChild("keyboard");
        this.space = this.keyboard.getChild("space");
        this.enter = this.keyboard.getChild("enter");
        this.roller = this.typewriter.getChild("roller");
        this.paper = this.typewriter.getChild("paper");
        this.paperWritten = this.typewriter.getChild("paper_written");
        this.keyParts = IntStream.range(0, 8).mapToObj(i -> this.keyboard.getChild("key" + i)).collect(Collectors.toList());
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

        float prog = be.getRollerSnapTicks() > 0 ? 0f : be.getLineProgress();
        if (prog < 0f) prog = 0f;
        if (prog > 1f) prog = 1f;
        float rx = 0.15f + prog * 4.5f;

        float sy = be.getSpaceTicks() > 0 ? 0.5f : 0f;
        float ey = be.getEnterTicks() > 0 ? 0.5f : 0f;

        this.space.y += sy;
        this.enter.y += ey;

        int idx = be.getBouncingKeyIndex();
        if (be.getKeyBounceTicks() > 0 && !this.keyParts.isEmpty()) {
            if (idx < 0) {
                be.setBouncingKeyIndex(level.random.nextInt(this.keyParts.size()));
                idx = be.getBouncingKeyIndex();
            }
            if (idx >= 0 && idx < this.keyParts.size()) {
                this.keyParts.get(idx).y += 0.5f;
            }
        }

        this.roller.x += rx;

        int full = state.getValue(TypewriterBlock.FULL);
        this.paper.visible = false;
        this.paperWritten.visible = false;
        if (full == 1) this.paper.visible = true;
        else if (full == 2) this.paperWritten.visible = true;

        this.typewriter.render(poseStack, vc, light, overlay);

        this.space.y -= sy;
        this.enter.y -= ey;
        if (idx >= 0 && idx < this.keyParts.size()) {
            this.keyParts.get(idx).y -= 0.5f;
        }
        this.roller.x -= rx;

        if (level.isClientSide()) {
            be.tickAnimations();
        }

        poseStack.popPose();
    }
}
