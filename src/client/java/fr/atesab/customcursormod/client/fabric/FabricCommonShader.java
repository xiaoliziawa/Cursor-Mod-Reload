package fr.atesab.customcursormod.client.fabric;

import java.util.function.Supplier;

import fr.atesab.customcursormod.client.common.handler.BasicHandler;
import fr.atesab.customcursormod.client.common.handler.CommonShader;
import net.minecraft.client.gl.ShaderProgram;

public class FabricCommonShader extends BasicHandler<Supplier<ShaderProgram>> implements CommonShader {

    public FabricCommonShader(Supplier<ShaderProgram> handle) {
        super(handle);
    }

}