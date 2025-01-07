package fr.atesab.customcursormod.client.fabric;

import fr.atesab.customcursormod.client.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.client.common.handler.CommonTextField;
import fr.atesab.customcursormod.client.fabric.mixin.AbstractButtonWidgetMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FabricCommonTextField extends CommonTextField {
	private final TextFieldWidget handle;
	private boolean enabled = true;

	public FabricCommonTextField(TextFieldWidget handle) {
		this.handle = handle;
		this.handle.setEditable(true);
		this.handle.setVisible(true);
		this.handle.setFocusUnlocked(true);
	}

	public FabricCommonTextField(CommonTextFieldObject obj) {
		this.handle = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, obj.xPosition, obj.yPosition,
				obj.width, obj.height, Text.literal(""));
		this.handle.setEditable(true);
		this.handle.setVisible(true);
		this.handle.setFocusUnlocked(true);
		this.enabled = true;
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
		return enabled && handle.isVisible();
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
		((AbstractButtonWidgetMixin) handle).setHeight(height);
	}

	@Override
	public void setEnable(boolean enable) {
		this.enabled = enable;
		handle.setEditable(enable);
		handle.setVisible(enable);
	}

	@Override
	public void setValue(String value) {
		handle.setText(value);
	}

	@Override
	public String getValue() {
		return handle.getText();
	}

	@Override
	public void setMaxLength(int length) {
		handle.setMaxLength(length);
	}

	@Override
	public void setTextColor(int color) {
		handle.setEditableColor(color);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (isEnable() && mouseX >= getXPosition() && mouseX < getXPosition() + getWidth()
				&& mouseY >= getYPosition() && mouseY < getYPosition() + getHeight()) {
			handle.setFocused(true);
			if (mouseButton == 0) {
				int i = (int)(mouseX - getXPosition());
				if (enabled) {
					String text = handle.getText();
					i = MinecraftClient.getInstance().textRenderer.trimToWidth(text, i).length();
					handle.setCursor(i, false);
					handle.setSelectionStart(i);
					handle.setSelectionEnd(i);
				}
			}
			return true;
		}
		handle.setFocused(false);
		return false;
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
		context.getMatrices().multiplyPositionMatrix(stack.<MatrixStack>getHandle().peek().getPositionMatrix());
		handle.render(context, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		if (handle.isFocused() && enabled) {
			return handle.charTyped(key, modifier);
		}
		return false;
	}

	@Override
	public boolean keyPressed(int key, int scan, int modifier) {
		if (handle.isFocused() && enabled) {
			return handle.keyPressed(key, scan, modifier);
		}
		return false;
	}

	@Override
	public void tick() {
		// 在1.21.1中，TextFieldWidget不再需要tick方法
		// 光标闪烁等功能现在由渲染系统自动处理
	}
}
