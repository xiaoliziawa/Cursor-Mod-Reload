package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.common.handler.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

public class FabricGuiUtils extends GuiUtils {
	private static Identifier currentTexture;
	private static DrawContext currentDrawContext;

	private FabricGuiUtils() {
	}

	private static final FabricGuiUtils instance = new FabricGuiUtils();

	public static FabricGuiUtils getFabric() {
		return instance;
	}

	/**
	 * 设置当前的纹理资源位置
	 */
	public static void setCurrentTexture(Identifier texture) {
		currentTexture = texture;
	}

	/**
	 * 设置当前的DrawContext实例
	 */
	public static void setCurrentDrawContext(DrawContext context) {
		currentDrawContext = context;
	}

	@Override
	public int fontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	@Override
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight, int color, boolean useAlpha) {
		if (currentDrawContext != null && currentTexture != null) {
			currentDrawContext.drawTexture(RenderPipelines.GUI_TEXTURED,
					currentTexture, x, y, u, v, width, height, uWidth, vHeight, (int)tileWidth, (int)tileHeight, color);
		}
	}

	@Override
	public void drawGradientRect(CommonMatrixStack stack, int left, int top, int right, int bottom, int rightTopColor,
								 int leftTopColor, int leftBottomColor, int rightBottomColor, float zLevel) {
		float alphaRightTop = (float) (rightTopColor >> 24 & 255) / 255.0F;
		float redRightTop = (float) (rightTopColor >> 16 & 255) / 255.0F;
		float greenRightTop = (float) (rightTopColor >> 8 & 255) / 255.0F;
		float blueRightTop = (float) (rightTopColor & 255) / 255.0F;
		float alphaLeftTop = (float) (leftTopColor >> 24 & 255) / 255.0F;
		float redLeftTop = (float) (leftTopColor >> 16 & 255) / 255.0F;
		float greenLeftTop = (float) (leftTopColor >> 8 & 255) / 255.0F;
		float blueLeftTop = (float) (leftTopColor & 255) / 255.0F;
		float alphaLeftBottom = (float) (leftBottomColor >> 24 & 255) / 255.0F;
		float redLeftBottom = (float) (leftBottomColor >> 16 & 255) / 255.0F;
		float greenLeftBottom = (float) (leftBottomColor >> 8 & 255) / 255.0F;
		float blueLeftBottom = (float) (leftBottomColor & 255) / 255.0F;
		float alphaRightBottom = (float) (rightBottomColor >> 24 & 255) / 255.0F;
		float redRightBottom = (float) (rightBottomColor >> 16 & 255) / 255.0F;
		float greenRightBottom = (float) (rightBottomColor >> 8 & 255) / 255.0F;
		float blueRightBottom = (float) (rightBottomColor & 255) / 255.0F;

		VertexConsumerProvider.Immediate bufferSource = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		VertexConsumer vertexBuffer = bufferSource.getBuffer(RenderLayer.getDebugQuads());

		vertexBuffer.vertex((float) right, (float) top, zLevel)
				.color(redRightTop, greenRightTop, blueRightTop, alphaRightTop);

		vertexBuffer.vertex((float) left, (float) top, zLevel)
				.color(redLeftTop, greenLeftTop, blueLeftTop, alphaLeftTop);

		vertexBuffer.vertex((float) left, (float) bottom, zLevel)
				.color(redLeftBottom, greenLeftBottom, blueLeftBottom, alphaLeftBottom);

		vertexBuffer.vertex((float) right, (float) bottom, zLevel)
				.color(redRightBottom, greenRightBottom, blueRightBottom, alphaRightBottom);

		bufferSource.draw();
	}

	@Override
	public void setShaderColor(float r, float g, float b, float a) {
		// Color is handled directly through vertex data in MC 1.21.6
	}

	/**
	 *
	 * @param context DrawContext实例
	 * @param x 左上角X坐标
	 * @param y 左上角Y坐标
	 * @param width 宽度
	 * @param height 高度
	 * @param radius 圆角半径
	 * @param color 颜色 (ARGB格式)
	 */
	public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
		if (radius <= 0) {
			context.fill(x, y, x + width, y + height, color);
			return;
		}
		radius = Math.min(radius, Math.min(width / 2, height / 2));
		context.fill(x + radius, y, x + width - radius, y + height, color);
		context.fill(x, y + radius, x + radius, y + height - radius, color);
		context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);
		drawQuarterCircle(context, x + radius, y + radius, radius, color, 0); // 左上
		drawQuarterCircle(context, x + width - radius, y + radius, radius, color, 1); // 右上
		drawQuarterCircle(context, x + radius, y + height - radius, radius, color, 2); // 左下
		drawQuarterCircle(context, x + width - radius, y + height - radius, radius, color, 3); // 右下
	}

	/**
	 *
	 * @param context DrawContext实例
	 * @param centerX 圆心X坐标
	 * @param centerY 圆心Y坐标
	 * @param radius 半径
	 * @param color 颜色
	 * @param corner 角落 (0=左上, 1=右上, 2=左下, 3=右下)
	 */
	private static void drawQuarterCircle(DrawContext context, int centerX, int centerY, int radius, int color, int corner) {
		for (int i = 0; i <= radius; i++) {
			for (int j = 0; j <= radius; j++) {
				if (i * i + j * j <= radius * radius) {
					int pixelX, pixelY;
					switch (corner) {
						case 0: // 左上
							pixelX = centerX - i;
							pixelY = centerY - j;
							break;
						case 1: // 右上
							pixelX = centerX + i;
							pixelY = centerY - j;
							break;
						case 2: // 左下
							pixelX = centerX - i;
							pixelY = centerY + j;
							break;
						case 3: // 右下
							pixelX = centerX + i;
							pixelY = centerY + j;
							break;
						default:
							continue;
					}
					context.fill(pixelX, pixelY, pixelX + 1, pixelY + 1, color);
				}
			}
		}
	}

	/**
	 *
	 * @param context DrawContext实例
	 * @param x 按钮X坐标
	 * @param y 按钮Y坐标
	 * @param width 按钮宽度
	 * @param height 按钮高度
	 * @param hovered 是否悬停
	 * @param enabled 是否启用
	 */
	public static void drawRoundedButton(DrawContext context, int x, int y, int width, int height, boolean hovered, boolean enabled) {
		int radius = Math.min(8, Math.min(width / 4, height / 4)); // 增大圆角半径

		int backgroundColor, borderColor, shadowColor;

		if (!enabled) {
			// 禁用状态：更暗的灰色
			backgroundColor = 0x50202020;
			borderColor = 0x80404040;
			shadowColor = 0x00000000;
		} else if (hovered) {
			// 悬停状态：带蓝色调的深色半透明+发光边框
			backgroundColor = 0x90404050;
			borderColor = 0xFFa0a0ff;
			shadowColor = 0x40a0a0ff;
		} else {
			// 正常状态深色半透明
			backgroundColor = 0x80252525;
			borderColor = 0xB0555555;
			shadowColor = 0x30000000; // 轻微阴影
		}
		if (shadowColor != 0x00000000) {
			drawRoundedRect(context, x + 1, y + 1, width, height, radius, shadowColor);
		}
		drawRoundedRect(context, x, y, width, height, radius, backgroundColor);
		drawRoundedRectBorder(context, x, y, width, height, radius, borderColor);
		if (hovered && enabled) {
			int innerGlowColor = 0x20ffffff;
			drawRoundedRect(context, x + 1, y + 1, width - 2, height - 2, radius - 1, innerGlowColor);
		}
	}

	private static void drawRoundedRectBorder(DrawContext context, int x, int y, int width, int height, int radius, int color) {
		context.fill(x + radius, y, x + width - radius, y + 1, color);
		context.fill(x + radius, y + height - 1, x + width - radius, y + height, color);
		context.fill(x, y + radius, x + 1, y + height - radius, color);
		context.fill(x + width - 1, y + radius, x + width, y + height - radius, color);
		drawCornerBorder(context, x + radius, y + radius, radius, color, 0);
		drawCornerBorder(context, x + width - radius, y + radius, radius, color, 1);
		drawCornerBorder(context, x + radius, y + height - radius, radius, color, 2);
		drawCornerBorder(context, x + width - radius, y + height - radius, radius, color, 3);
	}

	private static void drawCornerBorder(DrawContext context, int centerX, int centerY, int radius, int color, int corner) {
		for (int angle = 0; angle <= 90; angle += 2) {
			double rad = Math.toRadians(angle);
			int offsetX = (int) (Math.cos(rad) * radius);
			int offsetY = (int) (Math.sin(rad) * radius);

			int pixelX, pixelY;
			switch (corner) {
				case 0: // 左上
					pixelX = centerX - offsetX;
					pixelY = centerY - offsetY;
					break;
				case 1: // 右上
					pixelX = centerX + offsetX;
					pixelY = centerY - offsetY;
					break;
				case 2: // 左下
					pixelX = centerX - offsetX;
					pixelY = centerY + offsetY;
					break;
				case 3: // 右下
					pixelX = centerX + offsetX;
					pixelY = centerY + offsetY;
					break;
				default:
					continue;
			}
			context.fill(pixelX, pixelY, pixelX + 1, pixelY + 1, color);
		}
	}
}
