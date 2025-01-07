package fr.atesab.customcursormod.client.fabric;

import fr.atesab.customcursormod.client.common.handler.CommonButton;
import fr.atesab.customcursormod.client.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.client.common.handler.CommonText;
import fr.atesab.customcursormod.client.fabric.mixin.AbstractButtonWidgetMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FabricCommonButton extends CommonButton {
	public final ButtonWidget handle;

	public FabricCommonButton(CommonButtonObject obj) {
		handle = ButtonWidget.builder(obj.message.<Text>getHandle(), b -> obj.action.accept(this))
			.dimensions(obj.xPosition, obj.yPosition, obj.width, obj.height)
			.build();
	}

	public FabricCommonButton(ButtonWidget handle) {
		this.handle = handle;
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
		((AbstractButtonWidgetMixin) handle).setHeight(height);
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
		handle.setMessage(message.<Text>getHandle());
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return handle.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		MinecraftClient client = MinecraftClient.getInstance();
		DrawContext context = new DrawContext(client, client.getBufferBuilders().getEntityVertexConsumers());
		context.getMatrices().multiplyPositionMatrix(stack.<MatrixStack>getHandle().peek().getPositionMatrix());
		handle.render(context, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean isVisible() {
		return handle.visible;
	}

}
