package fr.atesab.customcursormod.client.fabric;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import fr.atesab.customcursormod.client.common.handler.CommonMatrixStack;
import fr.atesab.customcursormod.client.common.handler.CommonShader;
import fr.atesab.customcursormod.client.common.handler.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import java.util.function.Supplier;

public class FabricGuiUtils extends GuiUtils {
	private FabricGuiUtils() {
	}

	private static final FabricGuiUtils instance = new FabricGuiUtils();

	public static FabricGuiUtils getFabric() {
		return instance;
	}

	@Override
	public int fontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	@Override
	public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width,
			int height, float tileWidth, float tileHeight, int color, boolean useAlpha) {
		float scaleX = 1.0F / tileWidth;
		float scaleY = 1.0F / tileHeight;
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;
		int alpha = useAlpha ? (color >> 24) : 0xff;
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
		
		// 左下角
		bufferBuilder.vertex((float)x, (float)(y + height), 0.0F)
				.texture(u * scaleX, (v + (float) vHeight) * scaleY)
				.color(red, green, blue, alpha);

		// 右下角
		bufferBuilder.vertex((float)(x + width), (float)(y + height), 0.0F)
				.texture((u + (float) uWidth) * scaleX, (v + (float) vHeight) * scaleY)
				.color(red, green, blue, alpha);

		// 右上角
		bufferBuilder.vertex((float)(x + width), (float)y, 0.0F)
				.texture((u + (float) uWidth) * scaleX, v * scaleY)
				.color(red, green, blue, alpha);

		// 左上角
		bufferBuilder.vertex((float)x, (float)y, 0.0F)
				.texture(u * scaleX, v * scaleY)
				.color(red, green, blue, alpha);
				
		BuiltBuffer buffer = bufferBuilder.end();
		BufferRenderer.drawWithGlobalProgram(buffer);
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
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(MinecraftClient.getInstance().getShaderLoader().getOrCreateProgram(ShaderProgramKeys.POSITION_COLOR));
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
				GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		
		var mat = stack.<MatrixStack>getHandle().peek().getPositionMatrix();
		
		// 右上
		bufferBuilder.vertex(mat, (float)right, (float)top, zLevel)
				.color(redRightTop, greenRightTop, blueRightTop, alphaRightTop);

		// 左上
		bufferBuilder.vertex(mat, (float)left, (float)top, zLevel)
				.color(redLeftTop, greenLeftTop, blueLeftTop, alphaLeftTop);

		// 左下
		bufferBuilder.vertex(mat, (float)left, (float)bottom, zLevel)
				.color(redLeftBottom, greenLeftBottom, blueLeftBottom, alphaLeftBottom);

		// 右下
		bufferBuilder.vertex(mat, (float)right, (float)bottom, zLevel)
				.color(redRightBottom, greenRightBottom, blueRightBottom, alphaRightBottom);
				
		BuiltBuffer buffer = bufferBuilder.end();
		BufferRenderer.drawWithGlobalProgram(buffer);
		RenderSystem.disableBlend();
	}

	@Override
	public void setShaderColor(float r, float g, float b, float a) {
		RenderSystem.setShaderColor(r, g, b, a);
	}

	@Override
	public void setShader(CommonShader shader) {
		Supplier<ShaderProgram> shaderSupplier = (Supplier<ShaderProgram>)shader.getHandle();
		RenderSystem.setShader(shaderSupplier.get());
	}
}
