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
}
