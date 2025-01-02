package fr.atesab.customcursormod.fabric;

import fr.atesab.customcursormod.common.handler.CommonShader;
import fr.atesab.customcursormod.common.handler.CommonShaders;
import net.minecraft.client.render.GameRenderer;

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

    @Override
    public CommonShader getPositionShader() {
        return new FabricCommonShader(GameRenderer::getPositionProgram);
    }

    @Override
    public CommonShader getPositionColorShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorProgram);
    }

    @Override
    public CommonShader getPositionColorTexShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorTexProgram);
    }

    @Override
    public CommonShader getPositionTexShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexProgram);
    }

    @Override
    public CommonShader getPositionTexColorShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexColorProgram);
    }

    @Override
    public CommonShader getBlockShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeSolidProgram);
    }

    @Override
    public CommonShader getNewEntityShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntitySolidProgram);
    }

    @Override
    public CommonShader getParticleShader() {
        return new FabricCommonShader(GameRenderer::getParticleProgram);
    }

    @Override
    public CommonShader getPositionColorLightmapShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorLightmapProgram);
    }

    @Override
    public CommonShader getPositionColorTexLightmapShader() {
        return new FabricCommonShader(GameRenderer::getPositionColorTexLightmapProgram);
    }

    @Override
    public CommonShader getPositionTexColorNormalShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexColorNormalProgram);
    }

    @Override
    public CommonShader getPositionTexLightmapColorShader() {
        return new FabricCommonShader(GameRenderer::getPositionTexLightmapColorProgram);
    }

    @Override
    public CommonShader getRendertypeSolidShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeSolidProgram);
    }

    @Override
    public CommonShader getRendertypeCutoutMippedShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeCutoutMippedProgram);
    }

    @Override
    public CommonShader getRendertypeCutoutShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeCutoutProgram);
    }

    @Override
    public CommonShader getRendertypeTranslucentShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTranslucentProgram);
    }

    @Override
    public CommonShader getRendertypeTranslucentMovingBlockShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTranslucentMovingBlockProgram);
    }

    @Override
    public CommonShader getRendertypeTranslucentNoCrumblingShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTranslucentNoCrumblingProgram);
    }

    @Override
    public CommonShader getRendertypeArmorCutoutNoCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeArmorCutoutNoCullProgram);
    }

    @Override
    public CommonShader getRendertypeEntitySolidShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntitySolidProgram);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityCutoutProgram);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityCutoutNoNullProgram);
    }

    @Override
    public CommonShader getRendertypeEntityCutoutNoCullZOffsetShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityCutoutNoNullZOffsetProgram);
    }

    @Override
    public CommonShader getRendertypeItemEntityTranslucentCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeItemEntityTranslucentCullProgram);
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentCullShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityTranslucentCullProgram);
    }

    @Override
    public CommonShader getRendertypeEntityTranslucentShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityTranslucentProgram);
    }

    @Override
    public CommonShader getRendertypeEntitySmoothCutoutShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntitySmoothCutoutProgram);
    }

    @Override
    public CommonShader getRendertypeBeaconBeamShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeBeaconBeamProgram);
    }

    @Override
    public CommonShader getRendertypeEntityDecalShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityDecalProgram);
    }

    @Override
    public CommonShader getRendertypeEntityNoOutlineShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityNoOutlineProgram);
    }

    @Override
    public CommonShader getRendertypeEntityShadowShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityShadowProgram);
    }

    @Override
    public CommonShader getRendertypeEntityAlphaShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityAlphaProgram);
    }

    @Override
    public CommonShader getRendertypeEyesShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEyesProgram);
    }

    @Override
    public CommonShader getRendertypeEnergySwirlShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEnergySwirlProgram);
    }

    @Override
    public CommonShader getRendertypeLeashShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeLeashProgram);
    }

    @Override
    public CommonShader getRendertypeWaterMaskShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeWaterMaskProgram);
    }

    @Override
    public CommonShader getRendertypeOutlineShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeOutlineProgram);
    }

    @Override
    public CommonShader getRendertypeArmorGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeArmorGlintProgram);
    }

    @Override
    public CommonShader getRendertypeArmorEntityGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeArmorEntityGlintProgram);
    }

    @Override
    public CommonShader getRendertypeGlintTranslucentShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeGlintTranslucentProgram);
    }

    @Override
    public CommonShader getRendertypeGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeGlintProgram);
    }

    @Override
    public CommonShader getRendertypeGlintDirectShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeGlintDirectProgram);
    }

    @Override
    public CommonShader getRendertypeEntityGlintShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityGlintProgram);
    }

    @Override
    public CommonShader getRendertypeEntityGlintDirectShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEntityGlintDirectProgram);
    }

    @Override
    public CommonShader getRendertypeTextShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextProgram);
    }

    @Override
    public CommonShader getRendertypeTextIntensityShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextIntensityProgram);
    }

    @Override
    public CommonShader getRendertypeTextSeeThroughShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextSeeThroughProgram);
    }

    @Override
    public CommonShader getRendertypeTextIntensitySeeThroughShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTextIntensitySeeThroughProgram);
    }

    @Override
    public CommonShader getRendertypeLightningShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeLightningProgram);
    }

    @Override
    public CommonShader getRendertypeTripwireShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeTripwireProgram);
    }

    @Override
    public CommonShader getRendertypeEndPortalShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEndPortalProgram);
    }

    @Override
    public CommonShader getRendertypeEndGatewayShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeEndGatewayProgram);
    }

    @Override
    public CommonShader getRendertypeLinesShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeLinesProgram);
    }

    @Override
    public CommonShader getRendertypeCrumblingShader() {
        return new FabricCommonShader(GameRenderer::getRenderTypeCrumblingProgram);
    }
}
