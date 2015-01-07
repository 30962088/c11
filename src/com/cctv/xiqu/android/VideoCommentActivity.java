package com.cctv.xiqu.android;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;

import com.cctv.xiqu.android.APP.DisplayOptions;
import com.cctv.xiqu.android.adapter.NewsCommentListAdapter;
import com.cctv.xiqu.android.adapter.NewsCommentListAdapter.OnCommentBtnClickListener;
import com.cctv.xiqu.android.fragment.network.BaseClient;
import com.cctv.xiqu.android.fragment.network.InsertCommentRequest;
import com.cctv.xiqu.android.fragment.network.NewsCommentRequest;
import com.cctv.xiqu.android.fragment.network.BaseClient.RequestHandler;
import com.cctv.xiqu.android.fragment.network.NewsCommentRequest.Params;
import com.cctv.xiqu.android.utils.ShareUtils;
import com.cctv.xiqu.android.widget.BaseListView;
import com.cctv.xiqu.android.widget.IOSPopupWindow;
import com.cctv.xiqu.android.widget.BaseListView.OnLoadListener;
import com.cctv.xiqu.android.widget.BaseListView.Type;
import com.cctv.xiqu.android.widget.IOSPopupWindow.OnIOSItemClickListener;
import com.mengle.lib.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;

import com.cctv.xiqu.android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoCommentActivity extends BaseActivity implements OnLoadListener,OnClickListener,OnCommentBtnClickListener,OnTouchListener{

	public static class Model implements Serializable{
		private String id;
		private int count;
		private String title;
		private String img;
		private String url;
		public Model(String id, int count, String title, String img, String url) {
			super();
			this.id = id;
			this.count = count;
			this.title = title;
			this.img = img;
			this.url = url;
		}
		
		
		
	}
	
	public static void open(Context context,Model model){
		
		Intent intent = new Intent(context, VideoCommentActivity.class);
		
		intent.putExtra("model", model);
		
		context.startActivity(intent);
		
	}
	
	private BaseListView listView;
	
	private List<NewsCommentListAdapter.Model> list = new ArrayList<NewsCommentListAdapter.Model>();
	
	private NewsCommentListAdapter adapter;
	
	private Model model;
	
	private EditText editText;
	
	private View notLoginView;
	
	private String isuserid = "0";
	
	private String iscommentid = "0";
	
	private View container;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
		setContentView(R.layout.news_comment_layout);
		container = findViewById(R.id.container);
		notLoginView = findViewById(R.id.not_login_view);
		notLoginView.setOnClickListener(this);
		if(APP.getSession().isLogin()){
			notLoginView.setVisibility(View.GONE);
		}else{
			notLoginView.setVisibility(View.VISIBLE);
		}
		View shareView = findViewById(R.id.share);
		shareView.setVisibility(View.VISIBLE);
		shareView.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.header_container).setSelected(true);
		editText = (EditText) findViewById(R.id.edit);
		findViewById(R.id.sendBtn).setOnClickListener(this);
		listView = (BaseListView) findViewById(R.id.listview);
		listView.getRefreshableView().setOnTouchListener(this);
		View headerView = LayoutInflater.from(this).inflate(R.layout.video_comment_header, null);
		headerView.findViewById(R.id.play_btn).setOnClickListener(this);
		listView.getRefreshableView().addHeaderView(headerView);
		TextView titleView = (TextView) headerView.findViewById(R.id.title);
		titleView.setText(model.title);
		ImageLoader.getInstance().displayImage(model.img, (ImageView)headerView.findViewById(R.id.img),DisplayOptions.IMG.getOptions());
		TextView countView = (TextView) headerView.findViewById(R.id.comment);
		countView.setText("热门评论("+model.count+")");
		adapter = new NewsCommentListAdapter(this, list,this);
		listView.setAdapter(adapter);
		listView.setOnLoadListener(this);
		listView.load(true);
	}

	

	@Override
	public BaseClient onLoad(int offset, int limit) {
		
		return new NewsCommentRequest(this, new Params(offset, limit,""+model.id));
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		NewsCommentRequest.Result result = (NewsCommentRequest.Result) object;
		
		if(offset == 1){
			this.list.clear();
			
		}
		List<NewsCommentListAdapter.Model> list = result.toCommentList();
		this.list.addAll(list);
		adapter.notifyDataSetChanged();
		return list.size()>=limit?true:false;
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Type getRequestType() {
		// TODO Auto-generated method stub
		return Type.PAGE;
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share:
			onshare();
			break;
		case R.id.not_login_view:
			toLogin();
			break;
		case R.id.play_btn:
			onplay();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.sendBtn:
			String content = editText.getText().toString();
			new InsertCommentRequest(this, new  InsertCommentRequest.Params(model.id,iscommentid, isuserid, APP.getSession().getSid(), content,APP.getSession().getPkey())).request(new RequestHandler() {
				
				@Override
				public void onSuccess(Object object) {
					Utils.tip(VideoCommentActivity.this, "评论成功");
					editText.setText("");
//					listView.load(true);
					
				}
				
				@Override
				public void onError(int error, String msg) {
					Utils.tip(VideoCommentActivity.this, "评论失败");
					
				}

				@Override
				public void onComplete() {
					// TODO Auto-generated method stub
					
				}
			});
			break;

		default:
			break;
		}
		
	}



	private void onshare() {
		new IOSPopupWindow(this, new IOSPopupWindow.Params(
				Arrays.asList(new String[] { "分享给QQ好友", "分享到QQ空间", "分享给微信好友",
						"分享到朋友圈", "分享到新浪微博" }), new OnIOSItemClickListener() {

					@Override
					public void oniositemclick(int pos, String text) {

						SHARE_MEDIA media = new SHARE_MEDIA[] { SHARE_MEDIA.QQ,
								SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
								SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA }[pos];
						File bitmapFile = ImageLoader.getInstance().getDiscCache().get(model.img);
						ShareUtils.shareWebsite(VideoCommentActivity.this,
								media, model.title, APP.getAppConfig()
										.getSharecontent(model.id),bitmapFile);

					}

				}));
		
	}



	private void onplay() {
		VideoActivity.open(this, model.url);
		
	}



	@Override
	public void onCommentBtnClick(
			com.cctv.xiqu.android.adapter.NewsCommentListAdapter.Model model) {
		if(!APP.getSession().isLogin()){
			toLogin();
			return;
		}
		InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		isuserid = model.getUserid();
		iscommentid = model.getId();
		editText.requestFocus();
		
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(editText.isFocused()){
			isuserid = "0";
			iscommentid = "0";
			container.requestFocus();
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			return true;
		}
		return false;
	}
	
}