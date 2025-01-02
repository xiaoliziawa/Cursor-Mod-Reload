package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FabricCommonScreen extends CommonScreen {
	class FabricCommonScreenHandler extends Screen {
		FabricCommonScreen cs;

		FabricCommonScreenHandler(Text title) {
			super(title);
			cs = FabricCommonScreen.this;
		}

		@Override
		protected void init() {
			FabricCommonScreen.this.resize(width, height);
			FabricCommonScreen.this.init();
			super.init();
		}

		@Override
		public void resize(MinecraftClient client, int width, int height) {
			FabricCommonScreen.this.resize(width, height);
			super.resize(client, width, height);
		}

		@Override
		public boolean charTyped(char chr, int modifiers) {
			return FabricCommonScreen.this.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
		}

		@Override
		public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
			return FabricCommonScreen.this.keyPressed(keyCode, scanCode, modifiers)
					|| super.keyPressed(keyCode, scanCode, modifiers);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			return FabricCommonScreen.this.mouseClicked(mouseX, mouseY, button)
					|| super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public void tick() {
			FabricCommonScreen.this.tick();
			super.tick();
		}

		@Override
		public void render(DrawContext context, int mouseX, int mouseY, float delta) {
			MatrixStack matrices = context.getMatrices();
			FabricCommonScreen.this.render(new FabricCommonMatrixStack(matrices), mouseX, mouseY, delta);
			super.render(context, mouseX, mouseY, delta);
		}

		TextRenderer getTextRenderer() {
			return textRenderer;
		}
	}

	private final FabricCommonScreenHandler handle;

	public FabricCommonScreen(CommonScreen.CommonScreenObject obj) {
		super(obj.parent, obj.listener);
		handle = new FabricCommonScreenHandler(obj.title.getHandle());
	}

	@Override
	public void renderDefaultBackground(CommonMatrixStack stack) {
		DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
		context.getMatrices().multiplyPositionMatrix(stack.<MatrixStack>getHandle().peek().getPositionMatrix());
		handle.renderBackground(context);
	}

	@Override
	public void displayScreen() {
		MinecraftClient.getInstance().setScreen(handle);
	}

	@Override
	public int fontWidth(String text) {
		return handle.getTextRenderer().getWidth(text);
	}

	@Override
	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color) {
		DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
		context.getMatrices().multiplyPositionMatrix(stack.<MatrixStack>getHandle().peek().getPositionMatrix());
		context.drawText(handle.getTextRenderer(), text, (int)x, (int)y, color, false);
	}

	@Override
	public float getBlitOffset() {
		return 0; // In 1.20.1, z-offset is handled by DrawContext internally
	}
}
