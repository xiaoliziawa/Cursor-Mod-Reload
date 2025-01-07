package fr.atesab.customcursormod.client.fabric;

import fr.atesab.customcursormod.client.common.handler.CommonText;
import fr.atesab.customcursormod.client.common.handler.CommonTextAppendable;
import fr.atesab.customcursormod.client.common.handler.StringCommonText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class FabricStringCommonTextImpl extends StringCommonText {

	private final MutableText handle;
	public FabricStringCommonTextImpl(String text) {
		handle = Text.literal(text);
	}
	public FabricStringCommonTextImpl(MutableText handle) {
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
	public FabricStringCommonTextImpl copy() {
		return new FabricStringCommonTextImpl(this.handle.copy());
	}

	@Override
	public CommonTextAppendable append(CommonText text) {
		return new FabricCommonTextAppendable(this.handle.append(text.<Text>getHandle()));
	}
}