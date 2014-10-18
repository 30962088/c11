package cn.cntv.cctv11.android;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;

public class APP extends Application{
	
	private static APP instance = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.denyCacheImageMultipleSizesInMemory()
		.memoryCacheExtraOptions(768, 1280)
		.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
		.memoryCacheSize(5 * 1024 * 1024)
		.discCacheSize(50 * 1024 * 1024)
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.threadPoolSize(3)
		.threadPriority(Thread.NORM_PRIORITY - 1)
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		// 初始化ImageLoader的与配置。
		mImageLoader.init(config);
	}
	
	public static APP getInstance() {
		return instance;
	}
	
	public static ImageLoader mImageLoader = ImageLoader.getInstance();

	
}
