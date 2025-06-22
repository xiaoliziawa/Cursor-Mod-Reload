package fr.atesab.customcursormod.fabric;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class FabricRenderLayers {
    public static final RenderLayer CURSOR = RenderLayer.of(
            "cursor",
            2048,
            FabricRenderPipelines.CURSOR,
            RenderLayer.MultiPhaseParameters.builder()
                    .build(false)
    );

    public static final Function<Identifier, RenderLayer> CURSOR_TEXTURED = Util.memoize(
            resourceLocation -> RenderLayer.of(
                    "cursor",
                    2048,
                    FabricRenderPipelines.CURSOR,
                    RenderLayer.MultiPhaseParameters.builder()
                            .texture(new RenderPhase.Texture(resourceLocation, false))
                            .build(false)
            )
    );
}
