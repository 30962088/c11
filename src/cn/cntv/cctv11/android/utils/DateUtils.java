package cn.cntv.cctv11.android.utils;

import java.util.Date;

public class DateUtils {
	public static Date getDate(String datetime){
		long count = Long.parseLong(datetime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
		return new Date(count);
	}
}
