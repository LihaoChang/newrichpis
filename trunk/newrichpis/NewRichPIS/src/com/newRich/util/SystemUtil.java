package com.newRich.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class SystemUtil implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8706229590393992105L;

	public String moneyFormat(String str) {
		String check_str = str.substring(0, 1);
		String add_str = "";
		if (check_str.equals("-")) {
			str = str.substring(1);
			add_str = "-";
		}

		int size = str.length();
		if (str.length() <= 3) {
			return add_str + str;
		} else {
			return moneyFormat(add_str + str.substring(0, size - 3)) + ","
					+ (str.substring(size - 3));
		}
	}

	// string is null to ""
	public String StrUtilNull(String str) {
		String out_str = "";
		if (str.trim().equals("null")) {
			out_str = "";
		} else {
			out_str = str.trim();
		}
		return out_str;
	}
	
	//將map裡的值進行排序，由小到大
	public static List sortMap(Map map){
		//排序
		List data = new ArrayList(map.values());
		Collections.sort(data);
		return data;
	}
	
}
