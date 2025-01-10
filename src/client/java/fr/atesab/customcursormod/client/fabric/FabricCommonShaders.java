package fr.atesab.customcursormod.client.fabric;

import fr.atesab.customcursormod.client.common.handler.CommonShader;
import fr.atesab.customcursormod.client.common.handler.CommonShaders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import java.util.function.Supplier;

public class FabricCommonShaders extends CommonShaders {

    private FabricCommonShaders() {
    }

    private static final FabricCommonShaders instance = new FabricCommonShaders();

    /**
     * @return the instance
     */
    public static FabricCommonShaders getFabric() {
        return instance;
    }

    private Supplier<ShaderProgram> getShaderSupplier(ShaderProgramKey key) {
        return () -> MinecraftClient.getInstance().getShaderLoader().getOrCreateProgram(key);
    }

    @Override
    public CommonShader getPositionShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION));
    }

    @Override
    public CommonShader getPositionColorShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_COLOR));
    }

    @Override
    public CommonShader getPositionColorTexShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_TEX_COLOR));
    }

    @Override
    public CommonShader getPositionTexShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_TEX));
    }

    @Override
    public CommonShader getPositionTexColorShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_TEX_COLOR));
    }

    @Override
    public CommonShader getBlockShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_SOLID));
    }

    @Override
    public CommonShader getNewEntityShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_SOLID));
    }

    @Override
    public CommonShader getParticleShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.PARTICLE));
    }

    @Override
    public CommonShader getPositionColorLightmapShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_COLOR_LIGHTMAP));
    }

    @Override
    public CommonShader getPositionColorTexLightmapShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_COLOR_TEX_LIGHTMAP));
    }

    @Override
    public CommonShader getPositionTexColorNormalShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_SOLID));
    }

    @Override
    public CommonShader getPositionTexLightmapColorShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.POSITION_COLOR_TEX_LIGHTMAP));
    }

    @Override
    public CommonShader getRendertypeSolidShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_SOLID));
    }

    @Override
    public CommonShader getRendertypeCutoutMippedShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_CUTOUT_MIPPED));
    }

    @Override
    public CommonShader getRendertypeCutoutShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_CUTOUT));
    }

    @Override
    public CommonShader getRendertypeTranslucentShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TRANSLUCENT));
    }

    @Override
    public CommonShader getRendertypeTranslucentMovingBlockShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TRANSLUCENT_MOVING_BLOCK));
    }

    @Override
    public CommonShader getRendertypeTranslucentNoCrumblingShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TRANSLUCENT));
    }

    @Override
    public CommonShader getRendertypeArmorCutoutNoCullShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ARMOR_CUTOUT_NO_CULL));
    }

    @Override
    public CommonShader getRendertypeEntitySolidShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_SOLID));
    }

    @Override
    public CommonShader getRendertypeEntityCutoutShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_CUTOUT));
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_CUTOUT_NO_CULL));
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullZOffsetShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_CUTOUT_NO_CULL_Z_OFFSET));
    }

    @Override
    public CommonShader getRendertypeItemEntityTranslucentCullShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL));
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentCullShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_TRANSLUCENT));
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_TRANSLUCENT));
    }

    @Override
    public CommonShader getRendertypeEntitySmoothCutoutShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_SMOOTH_CUTOUT));
    }

    @Override
    public CommonShader getRendertypeBeaconBeamShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_BEACON_BEAM));
    }

    @Override
    public CommonShader getRendertypeEntityDecalShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_DECAL));
    }

    @Override
    public CommonShader getRendertypeEntityNoOutlineShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_NO_OUTLINE));
    }

    @Override
    public CommonShader getRendertypeEntityShadowShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_SHADOW));
    }

    @Override
    public CommonShader getRendertypeEntityAlphaShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_ALPHA));
    }

    @Override
    public CommonShader getRendertypeEyesShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_EYES));
    }

    @Override
    public CommonShader getRendertypeEnergySwirlShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENERGY_SWIRL));
    }

    @Override
    public CommonShader getRendertypeLeashShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_LEASH));
    }

    @Override
    public CommonShader getRendertypeWaterMaskShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_WATER_MASK));
    }

    @Override
    public CommonShader getRendertypeOutlineShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_OUTLINE));
    }

    @Override
    public CommonShader getRendertypeArmorGlintShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ARMOR_ENTITY_GLINT));
    }

    @Override
    public CommonShader getRendertypeArmorEntityGlintShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ARMOR_ENTITY_GLINT));
    }

    @Override
    public CommonShader getRendertypeGlintTranslucentShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_GLINT_TRANSLUCENT));
    }

    @Override
    public CommonShader getRendertypeGlintShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_GLINT));
    }

    @Override
    public CommonShader getRendertypeGlintDirectShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_GLINT));
    }

    @Override
    public CommonShader getRendertypeEntityGlintShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_GLINT));
    }

    @Override
    public CommonShader getRendertypeEntityGlintDirectShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_ENTITY_GLINT));
    }

    @Override
    public CommonShader getRendertypeTextShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TEXT));
    }

    @Override
    public CommonShader getRendertypeTextIntensityShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TEXT_INTENSITY));
    }

    @Override
    public CommonShader getRendertypeTextSeeThroughShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TEXT_SEE_THROUGH));
    }

    @Override
    public CommonShader getRendertypeTextIntensitySeeThroughShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH));
    }

    @Override
    public CommonShader getRendertypeLightningShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_LIGHTNING));
    }

    @Override
    public CommonShader getRendertypeTripwireShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_TRIPWIRE));
    }

    @Override
    public CommonShader getRendertypeEndPortalShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_END_PORTAL));
    }

    @Override
    public CommonShader getRendertypeEndGatewayShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_END_GATEWAY));
    }

    @Override
    public CommonShader getRendertypeLinesShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_LINES));
    }

    @Override
    public CommonShader getRendertypeCrumblingShader() {
        return new FabricCommonShader(getShaderSupplier(ShaderProgramKeys.RENDERTYPE_CRUMBLING));
    }
}