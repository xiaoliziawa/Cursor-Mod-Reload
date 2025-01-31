package fr.atesab.customcursormod.common.config;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;

import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;

public class CursorConfig {
	public static class CursorConfigStore {
		int xHotSpot;
		int yHotSpot;
		String link;
		int size;
		public CursorConfigStore(CursorConfig current) {
			this();
			xHotSpot = current.xHotSpot;
			yHotSpot = current.yHotSpot;
			link = current.link;
			size = current.size;
		}
		private CursorConfigStore() {
		}
	}
	public static CursorConfig read(CursorConfigStore store, CursorConfig defaultConfig) {
		CursorConfig cursor = new CursorConfig();
		if (store == null) {
			cursor.xHotSpot = defaultConfig.xHotSpot;
			cursor.yHotSpot = defaultConfig.yHotSpot;
			cursor.link = defaultConfig.link;
			cursor.size = defaultConfig.size;
		} else {
			cursor.xHotSpot = store.xHotSpot;
			cursor.yHotSpot = store.yHotSpot;
			cursor.link = store.link == null ? defaultConfig.link : store.link;
			cursor.size = store.size > 0 ? store.size : defaultConfig.size;
		}

		return cursor;
	}

	private int xHotSpot;
	private int yHotSpot;
	private String link;
	private int size = 32; // 默认大小为32像素
	private long cursor = MemoryUtil.NULL;

	private GLFWImage glfwImage = GLFWImage.create();

	private CursorConfig() {
	}

	public CursorConfig(String link) {
		this(link, 0, 0);
	}

	public CursorConfig(String link, int xHotSpot, int yHotSpot) {
		this(link, xHotSpot, yHotSpot, 32);
	}

	public CursorConfig(String link, int xHotSpot, int yHotSpot, int size) {
		this.xHotSpot = xHotSpot;
		this.yHotSpot = yHotSpot;
		this.link = link;
		this.size = size;
	}

	private void allocate() {
		readImage();
		if (cursor != MemoryUtil.NULL)
			freeCursor();
		cursor = GLFW.glfwCreateCursor(glfwImage, getxHotSpot(), getyHotSpot());
	}

	public CursorConfig copy() {
		CursorConfig config = new CursorConfig(link, xHotSpot, yHotSpot, size);
		return config;
	}

	public void freeCursor() {
		if (isAllocate())
			GLFW.glfwDestroyCursor(cursor);
	}

	public long getCursor() {
		if (cursor == MemoryUtil.NULL)
			allocate();
		return cursor;
	}

	public String getLink() {
		return link;
	}

	public InputStream getResource() {
		try {
			return getResourceLocation().openStream();
		} catch (Exception e) {
			return null;
		}

	}

	public ResourceLocationCommon getResourceLocation() {
		return ResourceLocationCommon.create(link);
	}

	public int getxHotSpot() {
		return xHotSpot;
	}

	public int getyHotSpot() {
		return yHotSpot;
	}

	public boolean isAllocate() {
		return cursor != MemoryUtil.NULL;
	}

	private void readImage() {
		try {
			BufferedImage originalImage = ImageIO.read(getResource());
			if (originalImage == null) return;
			
			int originalWidth = originalImage.getWidth();
			int originalHeight = originalImage.getHeight();
			
			// 计算缩放后的尺寸
			float scale = (float)size / (float)originalWidth;
			int scaledWidth = size;
			int scaledHeight = (int)(originalHeight * scale);
			
			// 创建缩放后的图像
			BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
			java.awt.Graphics2D g2d = scaledImage.createGraphics();
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
				java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();
			
			int[] pixels = new int[scaledWidth * scaledHeight];
			scaledImage.getRGB(0, 0, scaledWidth, scaledHeight, pixels, 0, scaledWidth);
			ByteBuffer buffer = BufferUtils.createByteBuffer(scaledWidth * scaledHeight * 4);
			
			for (int y = 0; y < scaledHeight; y++) {
				for (int x = 0; x < scaledWidth; x++) {
					int pixel = pixels[y * scaledWidth + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF)); // red
					buffer.put((byte) ((pixel >> 8) & 0xFF));  // green
					buffer.put((byte) (pixel & 0xFF));         // blue
					buffer.put((byte) ((pixel >> 24) & 0xFF)); // alpha
				}
			}
			buffer.flip();
			glfwImage.pixels(buffer);
			glfwImage.width(scaledWidth);
			glfwImage.height(scaledHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLink(String link) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.link = link;
	}

	public void setxHotSpot(int xHotSpot) {
		this.xHotSpot = xHotSpot;
		if (cursor != MemoryUtil.NULL) {
			freeCursor();
			allocate();
		}
	}

	public void setyHotSpot(int yHotSpot) {
		this.yHotSpot = yHotSpot;
		if (cursor != MemoryUtil.NULL) {
			freeCursor();
			allocate();
		}
	}

	public CursorConfigStore write() {
		CursorConfigStore store = new CursorConfigStore(this);
		// 保存大小设置
		store.size = this.size;
		return store;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		// 确保大小在有效范围内
		int newSize = Math.max(1, Math.min(128, size));
		if (this.size != newSize) {
			this.size = newSize;
			// 重新创建光标
			if (cursor != MemoryUtil.NULL) {
				freeCursor();
				allocate();
			}
		}
	}
}
