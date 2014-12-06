package cn.cntv.cctv11.android.utils;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import android.content.Context;

public class HtmlUtils {
	
	public static String getHtml(Context context, String templateUrl,String content) throws IOException{
		String template = IOUtils.toString(context.getAssets().open(templateUrl));
		String html = template.replace("{{content}}", content);
		return html;
	}
	
}
