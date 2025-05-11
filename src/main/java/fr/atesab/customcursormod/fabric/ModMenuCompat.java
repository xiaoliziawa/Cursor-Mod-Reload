package fr.atesab.customcursormod.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import fr.atesab.customcursormod.common.gui.GuiConfig;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> ((FabricCommonScreen) GuiConfig.create(new FabricBasicCommonScreen(screen))).getHandle();
    }
}
