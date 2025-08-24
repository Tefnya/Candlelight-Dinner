package net.satisfy.candlelight.client.gui;

import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.CustomData;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.block.entity.TypewriterEntity;
import net.satisfy.candlelight.core.networking.packet.SyncTypewriterDataC2SPacket;
import net.satisfy.candlelight.core.registry.SoundEventRegistry;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TypeWriterGui extends NoteGui {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Candlelight.MOD_ID, "textures/gui/typewriter_gui.png");
    private final TypewriterEntity typeWriterEntity;
    private float lineProgressLocal = 0f;
    private static final int MAX_CHARS_PER_LINE = 19;
    private int charsInLine = 0;
    private boolean savedOnClose = false;

    public TypeWriterGui(Player player, TypewriterEntity typeWriterEntity) {
        super(player, typeWriterEntity.getPaper());
        this.typeWriterEntity = typeWriterEntity;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        int w = 256;
        int h = 256;
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, w, h, w, h);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        if (!this.savedOnClose) {
            this.finalizeNote(false);
            this.savedOnClose = true;
        }
        super.onClose();
    }

    @Override
    public void removed() {
        if (!this.savedOnClose) {
            this.finalizeNote(false);
            this.savedOnClose = true;
        }
        super.removed();
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        boolean result = super.charTyped(chr, modifiers);
        if (!result) return false;
        if (chr == '\n' || chr == '\r') return true;
        if (chr == ' ') this.typeWriterEntity.triggerSpace(); else this.typeWriterEntity.triggerKeyBounce();
        this.charsInLine++;
        if (this.charsInLine >= MAX_CHARS_PER_LINE) {
            this.typeWriterEntity.snapRoller();
            this.charsInLine = 0;
            this.lineProgressLocal = 0f;
            this.typeWriterEntity.setLineProgress(0f);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_BELL, 1.0F));
            super.charTyped('\n', 0);
        } else {
            this.lineProgressLocal = this.charsInLine / (float) MAX_CHARS_PER_LINE;
            this.typeWriterEntity.setLineProgress(this.lineProgressLocal);
        }
        playTypingSound();
        return true;
    }

    @Override
    protected void finalizeNote(boolean signNote) {
        if (this.dirty) {
            this.removeEmptyPages();
            this.writeNbtData(signNote);
        }
        CompoundTag nbt = this.itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        BlockPos pos = typeWriterEntity.getBlockPos();
        NetworkManager.sendToServer(new SyncTypewriterDataC2SPacket(nbt, pos, signNote));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean result = super.keyPressed(keyCode, scanCode, modifiers);
        if (!result) return false;
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            this.typeWriterEntity.triggerEnter();
            this.typeWriterEntity.snapRoller();
            this.charsInLine = 0;
            this.lineProgressLocal = 0f;
            this.typeWriterEntity.setLineProgress(0f);
            playEnterSound();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (this.charsInLine > 0) {
                this.charsInLine--;
                this.lineProgressLocal = this.charsInLine / (float) MAX_CHARS_PER_LINE;
                this.typeWriterEntity.setLineProgress(this.lineProgressLocal);
            }
            playTypingSound();
            return true;
        }
        playTypingSound();
        return true;
    }

    private void playEnterSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_BELL, 1.0F));
    }

    private void playTypingSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEventRegistry.TYPEWRITER.get(), 2.0F));
    }
}
