package fr.atesab.customcursormod.client.fabric;

import fr.atesab.customcursormod.client.common.handler.CommonText;
import fr.atesab.customcursormod.client.common.handler.CommonTextAppendable;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class FabricCommonTextAppendable extends CommonTextAppendable {

	private final MutableText handle;
	public FabricCommonTextAppendable(MutableText handle) {
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
	public FabricCommonTextAppendable copy() {
		return new FabricCommonTextAppendable(this.handle.copy());
	}

	@Override
	public FabricCommonTextAppendable append(CommonText text) {
		return new FabricCommonTextAppendable(handle.append(text.<Text>getHandle()));
	}
}
