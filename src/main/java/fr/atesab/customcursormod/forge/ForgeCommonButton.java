package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonButton;
import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

public class ForgeCommonButton extends CommonButton {
	public final Button handle;

	public ForgeCommonButton(CommonButtonObject obj) {
		handle = Button.builder(obj.message.getHandle(), b -> obj.action.accept(this))
			.pos(obj.xPosition, obj.yPosition)
			.size(obj.width, obj.height)
			.build();
	}

	public ForgeCommonButton(Button handle) {
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
		return new ForgeCommonText(handle.getMessage());
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
		GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
		handle.render(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean isVisible() {
		return handle.visible;
	}
}
