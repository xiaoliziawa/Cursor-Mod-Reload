package fr.atesab.customcursormod.fabric;

import java.util.function.Supplier;

import fr.atesab.customcursormod.common.handler.BasicHandler;
import fr.atesab.customcursormod.common.handler.CommonShader;
import net.minecraft.client.gl.ShaderProgram;

public class FabricCommonShader extends BasicHandler<Supplier<ShaderProgram>> implements CommonShader {

    public FabricCommonShader(Supplier<ShaderProgram> handle) {
        super(handle);
    }

}