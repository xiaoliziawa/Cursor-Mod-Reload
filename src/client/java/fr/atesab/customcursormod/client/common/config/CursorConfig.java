package fr.atesab.customcursormod.client.common.config;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import fr.atesab.customcursormod.client.common.handler.ResourceLocationCommon;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;


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
			cursor.size = store.size > 0 ? store.size : 32;
		}

		return cursor;
	}

	private int xHotSpot;
	private int yHotSpot;
	private String link;
	private int size = 32;
	private long cursor = MemoryUtil.NULL;

	private GLFWImage glfwImage = GLFWImage.create();

	private CursorConfig() {
	}

	public CursorConfig(String link) {
		this(link, 0, 0);
	}

	public CursorConfig(String link, int xHotSpot, int yHotSpot) {
		this.xHotSpot = xHotSpot;
		this.yHotSpot = yHotSpot;
		this.link = link;
		this.size = 32;
	}

	private void allocate() {
		readImage();
		if (cursor != MemoryUtil.NULL)
			freeCursor();
		cursor = GLFW.glfwCreateCursor(glfwImage, getxHotSpot(), getyHotSpot());
	}

	public CursorConfig copy() {
		CursorConfig config = new CursorConfig(link, xHotSpot, yHotSpot);
		config.size = this.size;
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
			BufferedImage image = ImageIO.read(getResource());
			int w = image.getWidth();
			int h = image.getHeight();
			
			BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			resized.createGraphics().drawImage(image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			
			int[] pixels = new int[size * size];
			resized.getRGB(0, 0, size, size, pixels, 0, size);
			ByteBuffer buffer = BufferUtils.createByteBuffer(size * size * 4);
			
			for (int y = size - 1; y >= 0; y--)
				for (int x = 0; x < size; x++) {
					int pixel = pixels[(size - 1 - y) * size + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF));
					buffer.put((byte) ((pixel >> 8) & 0xFF));
					buffer.put((byte) (pixel & 0xFF));
					buffer.put((byte) ((pixel >> 24) & 0xFF));
				}
			buffer.flip();
			glfwImage.pixels(buffer).width(size).height(size);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void setLink(String link) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.link = link;
	}

	public void setxHotSpot(int xHotSpot) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.xHotSpot = xHotSpot;
	}

	public void setyHotSpot(int yHotSpot) {
		if (cursor != MemoryUtil.NULL)
			allocate();
		this.yHotSpot = yHotSpot;
	}

	public CursorConfigStore write() {
		return new CursorConfigStore(this);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size <= 0) size = 32;
		if (size > 256) size = 256;
		if (this.size != size) {
			this.size = size;
			if (cursor != MemoryUtil.NULL) {
				allocate();
			}
		}
	}
}
