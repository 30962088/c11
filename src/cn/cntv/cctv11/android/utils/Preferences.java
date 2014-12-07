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

		
		public void clearWeibo(){
			setWeiboAccessToken(null);
			setWeiboUid(null);
		}
		
		public void setGuide(boolean guide){
			preferences.edit().putBoolean("guide", guide).commit();
		}
		
		public boolean isGuide(){
			return preferences.getBoolean("guide", false);
		}
		
		public void setVoice(boolean voice){
			preferences.edit().putBoolean("voice", voice).commit();
		}
		
		public boolean getVoice(){
			return preferences.getBoolean("voice", true);
		}
		
		public void setNewsPush(boolean push){
			preferences.edit().putBoolean("news_push", push).commit();
		}
		
		public boolean getNewsPush(){
			return preferences.getBoolean("news_push", true);
		}
		
		public void setWeiboUid(String uid){
			preferences.edit().putString("weibo_uid", uid).commit();
		}
		
		public String getWeiboUid(){
			return preferences.getString("weibo_uid",null);
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
