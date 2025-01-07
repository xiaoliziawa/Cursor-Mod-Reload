package fr.atesab.customcursormod.client.common.gui;


import fr.atesab.customcursormod.client.common.CursorMod;
import fr.atesab.customcursormod.client.common.config.Configuration;
import fr.atesab.customcursormod.client.common.config.CursorConfig;
import fr.atesab.customcursormod.client.common.cursor.CursorType;
import fr.atesab.customcursormod.client.common.handler.*;
import fr.atesab.customcursormod.client.common.utils.Color;
import fr.atesab.customcursormod.client.common.utils.I18n;

public class GuiConfig extends CommonScreen.ScreenListener {
	public static CommonScreen create(CommonScreen parent) {
		return CommonScreen.create(parent, TranslationCommonText.create("cursormod.gui.config"), new GuiConfig());
	}

	private GuiConfig() {
	}

	@Override
	public void init() {
		CursorMod mod = CursorMod.getInstance();
		Configuration cfg = mod.getConfig();
		CommonScreen screen = getScreen();
		screen.addChildren(CommonButton.create(TranslationCommonText.create("menu.options"), width / 2 - 100,
				height / 2 + 24, 200, 20, b -> {
					if (cfg.dynamicCursor) {
						GuiConfigCursorMod.create(screen).displayScreen();
					} else {
						CursorConfig ccfg = mod.getCursors().get(CursorType.POINTER);

						GuiCursorConfig
								.create(screen, CursorType.POINTER, ccfg,
										cursorConfig -> mod.replaceCursor(CursorType.POINTER, cursorConfig))
								.displayScreen();
					}
				}));
		screen.addChildren(
				GuiUtils.get().createBooleanButton(TranslationCommonText.create("cursormod.config.dynCursor"),
						width / 2 - 100, height / 2 - 24, 200, 20, cfg::isDynamicCursor, cfg::setDynamicCursor));
		screen.addChildren(
				GuiUtils.get().createBooleanButton(TranslationCommonText.create("cursormod.config.clickAnim"),
						width / 2 - 100, height / 2, 200, 20, cfg::isClickAnimation, cfg::setClickAnimation));

		screen.addChildren(CommonButton.create(TranslationCommonText.create("gui.done"), width / 2 - 100,
				height / 2 + 48, 200, 20, b -> {
					mod.saveConfig();
					screen.getParent().displayScreen();
				}));
		super.init();
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		getScreen().renderDefaultBackground(stack);
		getScreen().drawCenterString(stack, CursorMod.MOD_NAME, width / 2f, height / 2f - 60f, Color.ORANGE, 2.5F);

		getScreen().drawRightString(stack, CursorMod.getInstance().getType().toString() + " - " + CursorMod.MOD_VERSION, width - 5,
				height - GuiUtils.get().fontHeight() * 3 - 9, 0xffffffff);
		getScreen().drawRightString(stack, I18n.get("cursormod.licence", CursorMod.MOD_LICENCE), width - 5,
				height - GuiUtils.get().fontHeight() * 2 - 7, 0xffffffff);
		getScreen().drawRightString(stack, I18n.get("cursormod.authors", CursorMod.MOD_AUTHORS), width - 5,
				height - GuiUtils.get().fontHeight() - 5, 0xffffffff);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
}
