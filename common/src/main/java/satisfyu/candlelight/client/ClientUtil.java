package satisfyu.candlelight.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.block.entity.TypeWriterEntity;
import satisfyu.candlelight.client.gui.SignedPaperGui;
import satisfyu.candlelight.client.gui.TypeWriterGui;
import satisfyu.candlelight.client.gui.NotePaperGui;

public class ClientUtil {

    public static void setNotePaperScreen(Player user, ItemStack stack, InteractionHand hand){
        Minecraft.getInstance().setScreen(new NotePaperGui(user, stack, hand));
    }


    public static void setTypeWriterScreen(Player user, TypeWriterEntity typeWriterEntity){
        if(user instanceof LocalPlayer)
            Minecraft.getInstance().setScreen(new TypeWriterGui(user, typeWriterEntity));
    }

    public static void setSignedPaperScreen(ItemStack stack){
        Minecraft.getInstance().setScreen(new SignedPaperGui(new SignedPaperGui.WrittenPaperContents(stack)));
    }

    public static <T extends BlockEntity> void renderBlock(BlockState state, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrices, vertexConsumers, ClientUtil.getLightLevel(entity.getLevel(), entity.getBlockPos()), OverlayTexture.NO_OVERLAY);
    }

    public static <T extends BlockEntity> void renderBlockFromItem(BlockItem item, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        renderBlock(item.getBlock().defaultBlockState(), matrices, vertexConsumers, entity);
    }

    public static <T extends BlockEntity> void renderItem(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GUI, ClientUtil.getLightLevel(entity.getLevel(), entity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, 1);
    }

    public static int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
