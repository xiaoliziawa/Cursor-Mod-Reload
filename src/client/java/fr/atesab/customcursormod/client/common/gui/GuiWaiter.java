package fr.atesab.customcursormod.client.common.gui;


import fr.atesab.customcursormod.client.common.handler.CommonScreen;

public class GuiWaiter {
	private int tick = 0;
	private CommonScreen screen;

	public void register(CommonScreen screen) {
		tick = 10;
		this.screen = screen;
	}

	public void tick() {
		tick--;
		if (tick == 0 && screen != null)
			screen.displayScreen();
	}
}
