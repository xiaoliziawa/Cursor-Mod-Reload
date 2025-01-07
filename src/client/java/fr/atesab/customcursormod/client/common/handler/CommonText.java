package fr.atesab.customcursormod.client.common.handler;

public abstract class CommonText {
	public abstract String getString();
	public abstract <T> T getHandle();

	public abstract CommonTextAppendable copy();
}
