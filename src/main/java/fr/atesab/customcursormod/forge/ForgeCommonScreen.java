package fr.atesab.customcursormod.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ForgeCommonScreen extends CommonScreen {
	class ForgeCommonScreenHandler extends Screen {
		ForgeCommonScreen cs;

		ForgeCommonScreenHandler(Component title) {
			super(title);
			cs = ForgeCommonScreen.this;
		}

		@Override
		protected void init() {
			ForgeCommonScreen.this.resize(width, height);
			ForgeCommonScreen.this.init();
			super.init();
		}

		@Override
		public void resize(@NotNull Minecraft client, int width, int height) {
			ForgeCommonScreen.this.resize(width, height);
			super.resize(client, width, height);
		}

		@Override
		public boolean charTyped(char chr, int modifiers) {
			return ForgeCommonScreen.this.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
		}

		@Override
		public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
			return ForgeCommonScreen.this.keyPressed(keyCode, scanCode, modifiers)
					|| super.keyPressed(keyCode, scanCode, modifiers);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			return ForgeCommonScreen.this.mouseClicked(mouseX, mouseY, button)
					|| super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public void tick() {
			ForgeCommonScreen.this.tick();
			super.tick();
		}

		@Override
		public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
			ForgeCommonScreen.this.render(new ForgeCommonMatrixStack(guiGraphics.pose()), mouseX, mouseY, partialTicks);
			super.render(guiGraphics, mouseX, mouseY, partialTicks);
		}

		Font getTextRenderer() {
			return font;
		}
	}

	private final ForgeCommonScreenHandler handle;

	public ForgeCommonScreen(CommonScreen.CommonScreenObject obj) {
		super(obj.parent, obj.listener);
		handle = new ForgeCommonScreenHandler(obj.title.getHandle());
	}

	/**
	 * @return the handle
	 */
	public ForgeCommonScreenHandler getHandle() {
		return handle;
	}

	@Override
	public void renderDefaultBackground(CommonMatrixStack stack) {
		if (Minecraft.getInstance().screen == handle) {
			handle.renderBackground(new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource()));
		}
	}

	@Override
	public void displayScreen() {
		Minecraft.getInstance().setScreen(handle);
	}

	@Override
	public int fontWidth(String text) {
		return handle.getTextRenderer().width(text);
	}

	@Override
	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color) {
		GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
		guiGraphics.drawString(handle.getTextRenderer(), text, (int)x, (int)y, color);
		Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
	}

	@Override
	public float getBlitOffset() {
		return 0.0F;
	}
}
