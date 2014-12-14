package cn.cntv.cctv11.android;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.PhotoViewActivity.Photo;
import cn.cntv.cctv11.android.utils.ShareUtils;
import cn.cntv.cctv11.android.widget.OnLongTapFrameLayout;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class WallPagerActivity extends BaseActivity implements OnClickListener,OnLongClickListener{

	public static void open(Context context, Model model) {
		Intent intent = new Intent(context, WallPagerActivity.class);
		intent.putExtra("model", model);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}
	
	
	public static class Model implements Serializable{
		private String thunbnail;
		private String name;
		private String origin;
		
		public Model(String thunbnail, String name, String origin) {
			super();
			this.thunbnail = thunbnail;
			this.name = name;
			this.origin = origin;
		}

	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	private PhotoViewAttacher attacher;
	
	private Model model;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
		setContentView(R.layout.activity_wallpager);
		findViewById(R.id.share).setOnClickListener(this);
		((OnLongTapFrameLayout)findViewById(R.id.longtap)).setOnLongClickListener1(this);
		((TextView)findViewById(R.id.title)).setText(model.name);
		final View loading = findViewById(R.id.loading);
		final ImageView imageView = (ImageView) findViewById(R.id.img);
		imageView.setVisibility(View.GONE);
		final ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.share).setOnClickListener(this);
		ImageLoader.getInstance().displayImage(model.thunbnail, thumbnail,DisplayOptions.IMG.getOptions());
//		attacher.setScaleType(ScaleType.FIT_CENTER);
		ImageLoader.getInstance().displayImage(
				model.origin,
				imageView,
				DisplayOptions.IMG.getOptions(),
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri,
							View view, Bitmap loadedImage) {
						loading.setVisibility(View.GONE);
						thumbnail.setVisibility(View.GONE);
						imageView.setVisibility(View.VISIBLE);
						attacher = new PhotoViewAttacher(imageView);
					}
				});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		attacher.cleanup();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.share:
			onshare();
			break;
		default:
			break;
		}
		
	}
	

	private void onshare() {
		ShareUtils.shareImage(this, model.origin);
		
	}

	@Override
	public boolean onLongClick(View v) {
		onshare();
		return false;
	}
	
}
