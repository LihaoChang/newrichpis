package com.newRich.util;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class I18NLocales {
	// 使用current保存當前用戶選擇的Locale
	private Locale current;

	public Locale getCurrent() {
		return current;
	}

	public void setCurrent(Locale current) {
		this.current = current;
	}

	// 取得系統所支持的全部語言
	public Map<String, Locale> getLocales() {
		// 將當前系統所支持的全部語言保存在Map對像中
		Map<String, Locale> locales = new Hashtable<String, Locale>();
		// System.out.println("getLocales current:" + current);
		// ResourceBundle bundle = ResourceBundle.getBundle("globalMessages", current);

		/**
		 * 添加當前系統支持的語言 key 是系統支持語言的顯示名字 value 是支持語言的Locale實例
		 */
		// locales.put(bundle.getString("lang.en_US"), Locale.US);
		// locales.put(bundle.getString("lang.zh_TW"), Locale.TAIWAN);
		// locales.put(bundle.getString("lang.zh_CN"), Locale.CHINA);

		locales.put("EN", Locale.US);
		locales.put("繁", Locale.TAIWAN);
		locales.put("简", Locale.CHINA);
		return locales;
	}
}
