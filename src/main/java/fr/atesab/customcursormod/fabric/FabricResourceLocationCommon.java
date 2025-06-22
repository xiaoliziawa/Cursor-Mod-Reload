package fr.atesab.customcursormod.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class FabricResourceLocationCommon extends ResourceLocationCommon {
	private final Identifier resource;
	private GpuTextureView textureView;

	public FabricResourceLocationCommon(String link) {
		resource = Identifier.ofVanilla(link);
	}

	public FabricResourceLocationCommon(Identifier resource) {
		this.resource = resource;
	}

	private void bindTexture() {
		AbstractTexture abstractTexture = MinecraftClient.getInstance().getTextureManager().getTexture(resource);
		textureView = abstractTexture.getGlTextureView();
	}

	@Override
	public void bindForSetup() {
		if (textureView == null) {
			bindTexture();
		}
		RenderSystem.setShaderTexture(0, textureView);
	}

	@Override
	public void setShaderTexture() {
		if (textureView == null) {
			bindTexture();
		}
		RenderSystem.setShaderTexture(0, textureView);
		FabricGuiUtils.setCurrentTexture(resource);
	}

	@Override
	public InputStream openStream() throws IOException {
		Optional<Resource> res = MinecraftClient.getInstance().getResourceManager().getResource(resource);
		if (res.isEmpty()) {
			return null;
		}
		return res.get().getInputStream();
	}
}
