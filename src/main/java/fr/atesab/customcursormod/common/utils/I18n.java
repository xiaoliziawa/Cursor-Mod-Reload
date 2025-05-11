package fr.atesab.customcursormod.common.utils;


import fr.atesab.customcursormod.common.handler.CommonSupplier;
import fr.atesab.customcursormod.common.handler.TranslationCommonText;

public class I18n {
	public static final CommonSupplier<TranslationCommonText.TranslationObject, String> SUPPLIER = new CommonSupplier<>(false);
	public static String get(String format, Object... args) {
		return SUPPLIER.fetch(new TranslationCommonText.TranslationObject(format, args));
	}
	private I18n() {
	}
}
