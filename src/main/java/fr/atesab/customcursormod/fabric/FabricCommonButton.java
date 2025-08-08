package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonButton;
import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;

public class FabricCommonButton extends CommonButton {
	public static class CustomButton extends ButtonWidget {
		private final FabricCommonButton parent;

		public CustomButton(FabricCommonButton parent, CommonButtonObject obj) {
			super(obj.xPosition, obj.yPosition, obj.width, obj.height,
					obj.message.getHandle(), b -> obj.action.accept(parent), DEFAULT_NARRATION_SUPPLIER);
			this.parent = parent;
		}

		@Override
		public void renderWidget(DrawContext context, int mouseX, int mouseY, float partialTicks) {
			if (!visible) return;

			boolean hovered = mouseX >= getX() && mouseY >= getY() &&
					mouseX < getX() + getWidth() &&
					mouseY < getY() + getHeight();

			FabricGuiUtils.drawRoundedButton(context, getX(), getY(),
					getWidth(), getHeight(),
					hovered, active);
			String text = getMessage().getString();
			MinecraftClient client = MinecraftClient.getInstance();
			int textColor;

			if (!active) {
				textColor = 0xFF404040;
			} else if (hovered) {
				textColor = 0xFFe6e6ff;
			} else {
				textColor = 0xFFe0e0e0;
			}
			int textX = getX() + (getWidth() - client.textRenderer.getWidth(text)) / 2;
			int textY = getY() + (getHeight() - client.textRenderer.fontHeight) / 2;
			if (active) {
				context.drawTextWithShadow(client.textRenderer, text, textX + 1, textY + 1, 0x80000000);
			}
			context.drawTextWithShadow(client.textRenderer, text, textX, textY, textColor);
		}
	}

	public final CustomButton handle;

	public FabricCommonButton(CommonButtonObject obj) {
		handle = new CustomButton(this, obj);
	}

	public FabricCommonButton(ButtonWidget handle) {
		throw new UnsupportedOperationException("Use NeoForgeCommonButton(CommonButtonObject) constructor instead");
	}

	@Override
	public int getXPosition() {
		return handle.getX();
	}

	@Override
	public int getYPosition() {
		return handle.getY();
	}

	@Override
	public int getWidth() {
		return handle.getWidth();
	}

	@Override
	public int getHeight() {
		return handle.getHeight();
	}

	@Override
	public boolean isEnable() {
		return handle.active;
	}

	@Override
	public void setXPosition(int xPosition) {
		handle.setX(xPosition);
	}

	@Override
	public void setYPosition(int yPosition) {
		handle.setY(yPosition);
	}

	@Override
	public void setWidth(int width) {
		handle.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		handle.setHeight(height);
	}

	@Override
	public void setEnable(boolean enable) {
		handle.active = enable;
	}

	@Override
	public void setVisible(boolean visible) {
		handle.visible = visible;
	}

	@Override
	public CommonText getMessage() {
		return new FabricCommonText(handle.getMessage());
	}

	@Override
	public void setMessage(CommonText message) {
		handle.setMessage(message.getHandle());
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return handle.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		// 现在由CustomButton.renderWidget处理渲染，这个方法不再需要
	}

	@Override
	public boolean isVisible() {
		return handle.visible;
	}
}
