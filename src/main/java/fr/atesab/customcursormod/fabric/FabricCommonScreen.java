package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FabricCommonScreen extends CommonScreen {
	public class FabricCommonScreenHandler extends Screen {
		private final ScreenListener listener;
		private DrawContext currentDrawContext;

		FabricCommonScreenHandler(Text title, ScreenListener listener) {
			super(title);
			this.listener = listener;
		}

		/**
		 * Get the CommonScreen instance associated with this handler
		 * @return the CommonScreen instance
		 */
		public CommonScreen getCommonScreen() {
			return listener.getScreen();
		}

		/**
		 * Get the current DrawContext instance for rendering
		 * @return current DrawContext
		 */
		public DrawContext getCurrentDrawContext() {
			return currentDrawContext;
		}

		@Override
		protected void init() {
			super.init();

			listener.getScreen().resize(width, height);

			listener.getScreen().init();

			CommonScreen commonScreen = listener.getScreen();

			for (CommonElement element : commonScreen.childrens) {
				if (element instanceof FabricCommonButton button) {
					this.addDrawableChild(button.handle);
				} else if (element instanceof FabricCommonTextField textField) {
					this.addDrawableChild(textField.handle);
				} else if (element instanceof CommonButtonValue<?> buttonValue) {
					// 使用公开的getter方法，无需反射
					CommonButton handle = buttonValue.getHandle();
					if (handle instanceof FabricCommonButton neoForgeButton) {
						this.addDrawableChild(neoForgeButton.handle);
					}
				}
			}
		}

		@Override
		public void render(DrawContext context, int mouseX, int mouseY, float delta) {
			this.currentDrawContext = context;

			FabricGuiUtils.setCurrentDrawContext(context);

			super.render(context, mouseX, mouseY, delta);

			if (this.client != null) {
				MatrixStack matrixStack = new MatrixStack();
				CommonMatrixStack stack = new FabricCommonMatrixStack(matrixStack);
				listener.render(stack, mouseX, mouseY, delta);
			}
		}

		@Override
		public void resize(MinecraftClient client, int width, int height) {
			super.resize(client, width, height);
			listener.resize(width, height);
		}

		@Override
		public boolean charTyped(char key, int modifier) {
			return listener.charTyped(key, modifier) || super.charTyped(key, modifier);
		}

		@Override
		public boolean keyPressed(int key, int scan, int modifier) {
			return listener.keyPressed(key, scan, modifier) || super.keyPressed(key, scan, modifier);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			boolean superHandled = super.mouseClicked(mouseX, mouseY, button);
			boolean listenerHandled = listener.mouseClicked(mouseX, mouseY, button);
			return superHandled || listenerHandled;
		}

		@Override
		public void tick() {
			super.tick();
			listener.tick();
		}

		@Override
		public TextRenderer getTextRenderer() {
			return textRenderer;
		}
	}

	private CommonScreen parent;
	private ScreenListener listener;
	private final FabricCommonScreenHandler handle;

	public FabricCommonScreen(CommonScreen parent, ScreenListener listener) {
		super(parent, listener);
		this.parent = parent;
		this.listener = listener;
		this.handle = new FabricCommonScreenHandler(getTitle(), listener);
	}

	public FabricCommonScreen(CommonScreenObject obj) {
		super(obj.parent, obj.listener);
		this.parent = obj.parent;
		this.listener = obj.listener;
		this.handle = new FabricCommonScreenHandler(obj.title.getHandle(), obj.listener);
	}

	public FabricCommonScreenHandler getHandle() {
		return handle;
	}

	@Override
	public void displayScreen() {
		MinecraftClient.getInstance().setScreen(handle);
	}

	@Override
	public CommonScreen getParent() {
		return parent;
	}

	public Text getTitle() {
		return Text.literal("Config Screen");
	}

	@Override
	public void renderDefaultBackground(CommonMatrixStack stack) {
		// 确保背景正确渲染
		if (handle.getCurrentDrawContext() != null) {
			if (MinecraftClient.getInstance().world != null) {
				handle.getCurrentDrawContext().fillGradient(0, 0, handle.width, handle.height,
						0xC0101010, 0xD0101010);
			}
		}
	}

	@Override
	public int fontWidth(String text) {
		return handle.getTextRenderer().getWidth(text);
	}

	@Override
	public void drawString(CommonMatrixStack stack, String text, float x, float y, int color) {
		DrawContext context = handle.getCurrentDrawContext();
		if (context != null) {
			context.drawTextWithShadow(handle.getTextRenderer(), text, (int)x, (int)y, color);
		} else {
			DrawContext fallbackContext = new DrawContext(MinecraftClient.getInstance(),
					new GuiRenderState());
			fallbackContext.drawTextWithShadow(handle.getTextRenderer(), text, (int)x, (int)y, color);
		}
	}

	@Override
	public float getBlitOffset() {
		return 0.0F;
	}
}
