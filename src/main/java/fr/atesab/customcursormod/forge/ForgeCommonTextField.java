package fr.atesab.customcursormod.forge;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.CommonTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ForgeCommonTextField extends CommonTextField {
	public final EditBox handle;

	public ForgeCommonTextField(EditBox handle) {
		this.handle = handle;
	}

	public ForgeCommonTextField(CommonTextFieldObject obj) {
		this.handle = new EditBox(Minecraft.getInstance().font, obj.xPosition, obj.yPosition, obj.width, obj.height,
				Component.literal(""));
		this.handle.visible = true;
		this.handle.setEditable(true);
		this.handle.active = true;
		this.handle.setBordered(true);
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
		return handle.visible;
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
		handle.visible = enable;
		handle.setEditable(enable);
		handle.active = enable;
		handle.setBordered(enable);
	}

	@Override
	public void setValue(String value) {
		handle.setValue(value);
	}

	@Override
	public String getValue() {
		return handle.getValue();
	}

	@Override
	public void setMaxLength(int length) {
		handle.setMaxLength(length);
	}

	@Override
	public void setTextColor(int color) {
		handle.setTextColor(color);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return handle.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
		guiGraphics.pose().pushPose();
		handle.render(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.pose().popPose();
		Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
	}

	@Override
	public boolean charTyped(char key, int modifier) {
		return handle.charTyped(key, modifier);
	}

	@Override
	public boolean keyPressed(int key, int scan, int modifier) {
		return handle.keyPressed(key, scan, modifier);
	}

	@Override
	public void tick() {
		handle.tick();
	}
}
