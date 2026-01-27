package net.satisfy.candlelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.gui.handler.LetterGuiHandler;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class LetterGui extends AbstractContainerScreen<LetterGuiHandler> {
    private static final ResourceLocation TEXTURE = Candlelight.identifier("textures/gui/letter_gui.png");
    private EditBox nameField;

    public LetterGui(LetterGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics g, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.leftPos;
        int y = this.topPos;
        g.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.nameField = new EditBox(this.font, i + 92, j + 24, 50, 12, Component.empty());
        this.nameField.setCanLoseFocus(false);
        this.nameField.setTextColor(-1);
        this.nameField.setTextColorUneditable(-1);
        this.nameField.setBordered(false);
        this.nameField.setMaxLength(50);
        this.nameField.setResponder(this::onRenamed);
        this.nameField.setValue("");
        this.addWidget(this.nameField);
        this.setInitialFocus(this.nameField);
        this.nameField.setEditable(true);
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.nameField.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawString(this.font, Component.translatable("block.candlelight.letter.translatable.text"), (int) (this.width / 1.95F - 1), (int) (this.height / 4.65F), 0x5A5A5A);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void onRenamed(String name) {
        LetterGuiHandler.name = name;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            assert Objects.requireNonNull(this.minecraft).player != null;
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
        }
        return this.nameField.keyPressed(keyCode, scanCode, modifiers) || this.nameField.canConsumeInput() || super.keyPressed(keyCode, scanCode, modifiers);
    }
}