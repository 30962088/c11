package cn.cntv.cctv11.android;

import java.io.File;
import java.util.Dictionary;

import cn.cntv.cctv11.android.utils.Dirctionary;
import cn.cntv.cctv11.android.utils.Preferences.Session;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.sso.UMQQSsoHandler;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class APP extends Application {

	
	
	public static class Meta{
		private static String weiboAppKey;
		public static String getWeiboAppKey() {
			return weiboAppKey;
		}
	}
	
	public enum DisplayOptions {
		IMG(new Builder().showImageForEmptyUri(R.drawable.empty)
				.showImageOnLoading(R.drawable.empty).cacheInMemory(true)
				.cacheOnDisc(true).build());
		/*
		 * THUNBNAIL(new Builder()
		 * .showImageForEmptyUri(R.drawable.icon_thunbnail_loading)
		 * .showImageOnLoading(R.drawable.icon_thunbnail_loading)
		 * .cacheInMemory(true).cacheOnDisc(true).build()), COMMENT( new
		 * Builder().showImageOnFail(R.drawable.icon_person_gray)
		 * .cacheInMemory(true).cacheOnDisc(true).build()),
		 * 
		 * BANNER(new Builder()
		 * .showImageForEmptyUri(R.drawable.icon_loading_banner)
		 * .showImageOnLoading(R.drawable.icon_loading_banner)
		 * .cacheInMemory(true).cacheOnDisc(true).build()),
		 */
		// SPLASH(new Builder().cacheInMemory(true).cacheOnDisc(true).build());

		DisplayImageOptions options;

		DisplayOptions(DisplayImageOptions options) {
			this.options = options;
		}

		public DisplayImageOptions getOptions() {
			return options;
		}
	}

	private static APP instance = null;

	
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).denyCacheImageMultipleSizesInMemory()
				.memoryCacheExtraOptions(768, 1280)
				.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
				.memoryCacheSize(5 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		// 初始化ImageLoader的与配置。
		mImageLoader.init(config);
		Dirctionary.init(this);
		initMeta();
		initPreference();
	}
	
	private static Session session;
	
	public static Session getSession() {
		return session;
	}
	
	private void initPreference() {
		
		session = new Session(this);
	}

	private void initMeta(){
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			Meta.weiboAppKey = bundle.getString("weibo_app_key");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}


	public static APP getInstance() {
		return instance;
	}

	public static ImageLoader mImageLoader = ImageLoader.getInstance();

}
