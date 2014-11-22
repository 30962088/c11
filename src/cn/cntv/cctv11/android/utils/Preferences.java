package cn.cntv.cctv11.android.utils;



import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

	
	public static class Session{
		
		private static final String NAME = "Session";
		
		private SharedPreferences preferences;

		private Context context;
		
		public Session(Context context) {
			preferences = context.getSharedPreferences(NAME, 0);
			this.context = context;
		}

		
		public void setWeiboAccessToken(String access_token){
			preferences.edit().putString("weibo_access_token", access_token).commit();
		}
		
		public String getWeiboAccessToken(){
			return preferences.getString("weibo_access_token",null);
		}
		
		public void setSid(String sid){
			preferences.edit().putString("sid", sid).commit();
		}
		
		public String getSid(){
			return preferences.getString("sid",null);
		}
		
		public boolean isLogin(){
			if(getPkey() != null && getSid() != null){
				return true;
			}
			return false;
		}
		
		public void login(String sid,String pkey){
			setPkey(pkey);
			setSid(sid);
		}
		
		public void logout(){
			setPkey(null);
			setSid(null);
		}
		
		public void setPkey(String pkey){
			preferences.edit().putString("pkey", ""+pkey).commit();
		}
		
		public String getPkey(){
			return preferences.getString("pkey", null);
		}
		
		
		
		
		
	}
	
	

}
