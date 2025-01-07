package fr.atesab.customcursormod.client.fabric;

import fr.atesab.customcursormod.client.common.handler.CommonText;
import fr.atesab.customcursormod.client.common.handler.CommonTextAppendable;
import net.minecraft.text.Text;

public class FabricCommonText extends CommonText {
	private final Text handle;
	public FabricCommonText(Text handle) {
		this.handle = handle;
	}
	@Override
	public String getString() {
		return handle.getString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getHandle() {
		return (T) handle;
	}

	@Override
	public CommonTextAppendable copy() {
		return new FabricCommonTextAppendable(handle.copy());
	}
	
}
