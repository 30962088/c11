package cn.cntv.cctv11.android.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.cntv.cctv11.android.fragment.network.BaseClient;

public class ImageUtils {

	public static class Size{
		private Integer width;
		private Integer height;
		public Size(Integer width, Integer height) {
			super();
			this.width = width;
			this.height = height;
		}
		public Integer getWidth() {
			return width;
		}
		public Integer getHeight() {
			return height;
		}
		
		
	}
	
	public static String getTheImage(String filename,Size size){
		List<String> params = new ArrayList<String>();
		params.add("fileName="+filename);
		if(size != null){
			if(size.width != null){
				params.add("width="+size.width);
			}
			if(size.height != null){
				params.add("height="+size.height);
			}
		}
		String param = StringUtils.join(params,"&");
		return BaseClient.BASE_URL+"cctv11/getTheImage?"+param;
		
	}
	
}