package fr.atesab.customcursormod.forge.gui;

import fr.atesab.customcursormod.common.cursor.SelectZone;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.ComponentPath;
import javax.annotation.Nullable;

public class ForgeGuiSelectZone extends SelectZone implements GuiEventListener {
	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
	private boolean enable = true;
	private boolean focused = false;

	public ForgeGuiSelectZone(int xPosition, int yPosition, int width, int height) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the xPosition
	 */
	@Override
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * @return the yPosition
	 */
	@Override
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * @return the height
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	@Override
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return false;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		return false;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		return false;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return false;
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		return super.charTyped(codePoint, modifiers);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX >= this.xPosition && mouseX < this.xPosition + this.width &&
			   mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
	}

	@Override
	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	@Override
	public boolean isFocused() {
		return this.focused;
	}

	@Nullable
	@Override
	public ComponentPath nextFocusPath(FocusNavigationEvent event) {
		return null;
	}

	@Override
	public ScreenRectangle getRectangle() {
		return new ScreenRectangle(this.xPosition, this.yPosition, this.width, this.height);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		// 默认实现
	}
}
