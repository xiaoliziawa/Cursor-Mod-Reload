package fr.atesab.customcursormod.client.common.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.atesab.customcursormod.client.common.handler.ResourceLocationCommon;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;


public class CursorConfig {
	private static final String CONFIG_PATH = "config/customcursormod.json";

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
		if (cursor != MemoryUtil.NULL) {
			freeCursor();
		}
		
		readImage();
		
		int finalXHotSpot = Math.min(Math.max(0, xHotSpot), size - 1);
		int finalYHotSpot = Math.min(Math.max(0, yHotSpot), size - 1);
		
		cursor = GLFW.glfwCreateCursor(glfwImage, finalXHotSpot, finalYHotSpot);
		
		if (cursor == MemoryUtil.NULL) {
			System.err.println("Failed to create cursor with hotspot (" + finalXHotSpot + "," + finalYHotSpot + ")");
		} else {
			System.out.println("Created cursor with hotspot (" + finalXHotSpot + "," + finalYHotSpot + ")");
		}
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
			if (image == null) {
				System.err.println("Failed to load cursor image: " + link);
				return;
			}

			int originalWidth = image.getWidth();
			int originalHeight = image.getHeight();
			
			float scaleX = (float)size / originalWidth;
			float scaleY = (float)size / originalHeight;
			xHotSpot = Math.round(xHotSpot * scaleX);
			yHotSpot = Math.round(yHotSpot * scaleY);
			
			BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			resized.createGraphics().drawImage(
				image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH), 
				0, 0, null
			);
			
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
			System.err.println("Error loading cursor image: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setLink(String link) {
		this.link = link;
		if (cursor != MemoryUtil.NULL) {
			allocate();
		}
	}

	public void setxHotSpot(int xHotSpot) {
		this.xHotSpot = xHotSpot;
		if (cursor != MemoryUtil.NULL) {
			allocate();
		}
	}

	public void setyHotSpot(int yHotSpot) {
		this.yHotSpot = yHotSpot;
		if (cursor != MemoryUtil.NULL) {
			allocate();
		}
	}

	public CursorConfigStore write() {
		CursorConfigStore store = new CursorConfigStore(this);
		try {
			File configDir = new File("config").getAbsoluteFile();
			if (!configDir.exists()) {
				if (!configDir.mkdirs()) {
					System.err.println("Failed to create config directory: " + configDir);
					return store;
				}
			}
			
			File configFile = new File(configDir, "customcursormod.json");
			
			if (configFile.exists() && !configFile.canWrite()) {
				System.err.println("Config file is not writable: " + configFile);
				return store;
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(store);
			Files.write(configFile.toPath(), json.getBytes());
			
			System.out.println("Successfully saved cursor config to: " + configFile);
		} catch (IOException e) {
			System.err.println("Failed to save cursor config: " + e.getMessage());
			e.printStackTrace();
		}
		return store;
	}

	public static CursorConfig loadFromFile() {
		try {
			File configFile = new File("config/customcursormod.json").getAbsoluteFile();
			
			if (!configFile.exists()) {
				File oldConfigFile = new File("config/cursor-mod.json").getAbsoluteFile();
				if (oldConfigFile.exists()) {
					Files.copy(oldConfigFile.toPath(), configFile.toPath());
					oldConfigFile.delete();
					System.out.println("Migrated config from cursor-mod.json to customcursormod.json");
				}
			}
			
			if (configFile.exists()) {
				if (!configFile.canRead()) {
					System.err.println("Config file is not readable: " + configFile);
					return getDefaultConfig();
				}
				
				String json = new String(Files.readAllBytes(configFile.toPath()));
				Gson gson = new Gson();
				CursorConfigStore store = gson.fromJson(json, CursorConfigStore.class);
				
				System.out.println("Successfully loaded cursor config from: " + configFile);
				return read(store, getDefaultConfig());
			}
		} catch (IOException e) {
			System.err.println("Failed to load cursor config: " + e.getMessage());
			e.printStackTrace();
		}
		return getDefaultConfig();
	}

	private static CursorConfig getDefaultConfig() {
		return new CursorConfig("textures/cursor/default.png", 0, 0);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size <= 0) size = 16;
		if (size > 256) size = 256;
		if (this.size != size) {
			this.size = size;
			if (cursor != MemoryUtil.NULL) {
				allocate();
			}
		}
	}
}
