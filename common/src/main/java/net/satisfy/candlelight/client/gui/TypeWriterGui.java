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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.CustomData;
import net.satisfy.candlelight.core.block.entity.TypewriterEntity;
import net.satisfy.candlelight.core.networking.packet.SyncTypewriterDataC2SPacket;
import net.satisfy.candlelight.core.registry.SoundEventRegistry;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TypeWriterGui extends NoteGui {
    private final TypewriterEntity typewriterEntity;
    private float lineProgressLocal = 0f;
    private static final int MAX_CHARS_PER_LINE = 19;
    private int charsInLine = 0;

    public TypeWriterGui(Player player, TypewriterEntity typewriterEntity) {
        super(player, typewriterEntity.getPaper());
        this.typewriterEntity = typewriterEntity;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        assert this.minecraft != null;
        this.minecraft.gameRenderer.processBlurEffect(delta);
        this.minecraft.getMainRenderTarget().bindWrite(false);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
    }

    @Override
    public void renderTransparentBackground(GuiGraphics guiGraphics) {
    }

    @Override
    protected void renderBlurredBackground(float f) {
    }

    @Override
    public void removed() {
        super.removed();
        this.typewriterEntity.snapRoller();
        this.typewriterEntity.setLineProgress(0f);
        this.charsInLine = 0;
        this.lineProgressLocal = 0f;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        boolean result = super.charTyped(chr, modifiers);
        if (!result) return false;
        if (chr == '\n' || chr == '\r') return true;
        if (chr == ' ') this.typewriterEntity.triggerSpace(); else this.typewriterEntity.triggerKeyBounce();
        this.charsInLine++;
        if (this.charsInLine >= MAX_CHARS_PER_LINE) {
            this.typewriterEntity.snapRoller();
            this.charsInLine = 0;
            this.lineProgressLocal = 0f;
            this.typewriterEntity.setLineProgress(0f);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_BELL, 1.0F));
            super.charTyped('\n', 0);
        } else {
            this.lineProgressLocal = this.charsInLine / (float) MAX_CHARS_PER_LINE;
            this.typewriterEntity.setLineProgress(this.lineProgressLocal);
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
        BlockPos pos = typewriterEntity.getBlockPos();
        NetworkManager.sendToServer(new SyncTypewriterDataC2SPacket(nbt, pos, signNote));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean result = super.keyPressed(keyCode, scanCode, modifiers);
        if (!result) return false;
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            this.typewriterEntity.triggerEnter();
            this.typewriterEntity.snapRoller();
            this.charsInLine = 0;
            this.lineProgressLocal = 0f;
            this.typewriterEntity.setLineProgress(0f);
            playEnterSound();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (this.charsInLine > 0) {
                this.charsInLine--;
                this.lineProgressLocal = this.charsInLine / (float) MAX_CHARS_PER_LINE;
                this.typewriterEntity.setLineProgress(this.lineProgressLocal);
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
