package fr.atesab.customcursormod.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.blaze3d.textures.GpuTexture;
import fr.atesab.customcursormod.common.CursorMod;
import fr.atesab.customcursormod.common.handler.ResourceLocationCommon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class FabricResourceLocationCommon extends ResourceLocationCommon {
	private final Identifier resource;
	private GpuTexture texture;

	public FabricResourceLocationCommon(String link) {
		resource = Identifier.of(CursorMod.MOD_ID, link);
	}

	public FabricResourceLocationCommon(Identifier resource) {
		this.resource = resource;
	}

	private void bindTexture() {
		texture = MinecraftClient.getInstance().getTextureManager().getTexture(resource).getGlTexture();
	}

	@Override
	public void bindForSetup() {
		if (texture == null) {
			bindTexture();
		}
		RenderSystem.setShaderTexture(0, texture);
	}

	@Override
	public void setShaderTexture() {
		if (texture == null) {
			bindTexture();
		}
		RenderSystem.setShaderTexture(0, texture);
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
