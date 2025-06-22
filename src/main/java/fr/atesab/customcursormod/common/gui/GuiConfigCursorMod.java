package fr.atesab.customcursormod.common.gui;

import fr.atesab.customcursormod.common.CursorMod;
import fr.atesab.customcursormod.common.config.CursorConfig;
import fr.atesab.customcursormod.common.cursor.CursorType;
import fr.atesab.customcursormod.common.handler.*;
import fr.atesab.customcursormod.common.utils.Color;
import fr.atesab.customcursormod.common.utils.I18n;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


public class GuiConfigCursorMod extends CommonScreen.ScreenListener {

	public static CommonScreen create(CommonScreen parent) {
		return CommonScreen.create(parent, TranslationCommonText.create("cursormod.gui.cursorList"),
				new GuiConfigCursorMod());
	}

	private Map<CursorType, CursorConfig> cursors;
	private List<CommonButtonValue<CursorType>> cursorButtons = new ArrayList<>();
	private CommonButton lastPage;
	private CommonButton nextPage;

	private int page = 0;
	private int elementByPage = 1;

	private GuiConfigCursorMod() {
		this.cursors = CursorMod.getInstance().getCursors();
	}

	private void defineButton() {
		if (lastPage != null)
			lastPage.setEnable(page > 0);
		if (nextPage != null)
			nextPage.setEnable(page < cursors.size() / elementByPage);
		for (int i = 0; i < cursorButtons.size(); i++)
			cursorButtons.get(i).setVisible(i < (page + 1) * elementByPage && i >= (page) * elementByPage);
	}

	private void drawCursor(CommonMatrixStack stack, int posX, int posY, CursorConfig cursorConfig) {
		try {
			BufferedImage image = ImageIO.read(cursorConfig.getResource());
			int imageWidth = image.getWidth();
			int imageHeight = image.getWidth(); // 单帧高度（正方形）
			int totalImageHeight = image.getHeight(); // 图像总高度
			var gutils = GuiUtils.get();
			gutils.drawGradientRect(stack, getScreen().getBlitOffset(), posX, posY, posX + 20, posY + 20, -1072689136,
					-804253680);
			cursorConfig.getResourceLocation().setShaderTexture();
			// 只渲染第一帧，参数：位置(posX,posY), UV(0,0), UV尺寸(imageWidth,imageHeight), 渲染尺寸(20,20), 纹理总尺寸(imageWidth,总高度)
			gutils.drawScaledCustomSizeModalRect(posX, posY, 0, 0, imageWidth, imageHeight, 20, 20, imageWidth,
					totalImageHeight, 0xffffffff, true);
		} catch (Exception e) {
			// hide
		}
	}

	@Override
	public void render(CommonMatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		CommonScreen screen = getScreen();
		screen.renderDefaultBackground(stack);
		screen.drawCenterString(stack, I18n.get("cursormod.gui.cursorList"), width / 2,
				height / 2 - ((elementByPage / 2 + 1) * 21 + 2 + GuiUtils.get().fontHeight()), Color.ORANGE);
		for (int i = page * elementByPage; i < cursorButtons.size() && i < (page + 1) * elementByPage; i++) {
			CommonButtonValue<CursorType> button = cursorButtons.get(i);
			drawCursor(stack, button.getXPosition() + button.getWidth() + 1, button.getYPosition(),
					cursors.get(button.getValue()));
		}
		super.render(stack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void init() {
		CommonScreen screen = getScreen();
		page = 0;
		elementByPage = (height - 63) / 21;
		if (elementByPage > 10)
			elementByPage = 10;
		screen.addChildren(CommonButton.create(TranslationCommonText.create("gui.done"), width / 2 - 79,
				height / 2 + (elementByPage / 2) * 21, 158, 20, b -> screen.getParent().displayScreen()));
		if (elementByPage <= cursors.size()) {
			lastPage = screen.addChildren(CommonButton.create(StringCommonText.create("<"), width / 2 - 100,
					height / 2 + (elementByPage / 2) * 21, 20, 20, b -> {
						page--;
						defineButton();
					}));
			nextPage = screen.addChildren(CommonButton.create(StringCommonText.create(">"), width / 2 + 80,
					height / 2 + (elementByPage / 2) * 21, 20, 20, b -> {
						page++;
						defineButton();
					}));
		} else
			lastPage = nextPage = null;
		int i = -1;
		cursorButtons.clear();
		for (Entry<CursorType, CursorConfig> entry : cursors.entrySet()) {
			CursorType type = entry.getKey();
			CursorConfig cfg = entry.getValue();
			CommonButtonValue<CursorType> button = CommonButtonValue.<CursorType>create(type, type.getTranslation(), width / 2 - 100,
					height / 2 + ((i++) % elementByPage - elementByPage / 2) * 21, 200, 20,
					b -> GuiCursorConfig
							.create(screen, type, cfg,
									cursorConfig -> CursorMod.getInstance().replaceCursor(type, cursorConfig))
							.displayScreen());

			cursorButtons.add(button);
			screen.addChildren(button);
		}
		defineButton();
		super.init();
	}
}
