package com.cctv.xiqu.android;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.cctv.xiqu.android.adapter.BBSCommentListAdapter;
import com.cctv.xiqu.android.adapter.NewsCommentListAdapter;
import com.cctv.xiqu.android.adapter.NewsCommentListAdapter.OnCommentBtnClickListener;
import com.cctv.xiqu.android.fragment.network.BaseClient;
import com.cctv.xiqu.android.fragment.network.GetForumCommentRequest;
import com.cctv.xiqu.android.fragment.network.InsertForumRequest;
import com.cctv.xiqu.android.fragment.network.BaseClient.RequestHandler;
import com.cctv.xiqu.android.utils.LoadingPopup;
import com.cctv.xiqu.android.utils.ShareUtils;
import com.cctv.xiqu.android.widget.BaseListView;
import com.cctv.xiqu.android.widget.IOSPopupWindow;
import com.cctv.xiqu.android.widget.BBSDetailHeaderView.Model;
import com.cctv.xiqu.android.widget.BaseListView.OnLoadListener;
import com.cctv.xiqu.android.widget.BaseListView.Type;
import com.cctv.xiqu.android.widget.IOSPopupWindow.OnIOSItemClickListener;
import com.mengle.lib.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;

import com.cctv.xiqu.android.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class BBSDetailActivity extends BaseActivity implements OnLoadListener,
		OnClickListener,OnCommentBtnClickListener,OnTouchListener {

	public static void open(Context context, Model model) {

		Intent intent = new Intent(context, BBSDetailActivity.class);

		intent.putExtra("model", model);

		context.startActivity(intent);

	}

	private BaseListView listView;

	private List<Object> list = new ArrayList<Object>();

	private BBSCommentListAdapter adapter;

	private Model model;

	private EditText editText;
	
	private View container;

	private String replyUid;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
//		list.add(model);
		setContentView(R.layout.bbs_detail_layout);
		container = findViewById(R.id.container);
		editText = (EditText) findViewById(R.id.edit);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.sendBtn).setOnClickListener(this);
		findViewById(R.id.share).setOnClickListener(this);
		listView = (BaseListView) findViewById(R.id.listview);
		listView.getRefreshableView().setOnTouchListener(this);
		adapter = new BBSCommentListAdapter(this, list,this);
		listView.setAdapter(adapter);
		listView.setOnLoadListener(this);
		listView.load(true);
	}

	@Override
	public BaseClient onLoad(int offset, int limit) {

		return new GetForumCommentRequest(this,
				new GetForumCommentRequest.Params(model.getId(), offset, limit));
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		GetForumCommentRequest.Result result = (GetForumCommentRequest.Result) object;

		if (offset == 1) {
			this.list.clear();
			this.list.add(model);
		}
		List<NewsCommentListAdapter.Model> list = result.toCommentList();
		this.list.addAll(list);
		adapter.notifyDataSetChanged();
		return list.size() >= limit ? true : false;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share:
			onshare();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.sendBtn:
			String content = editText.getText().toString();

			if (!APP.getSession().isLogin()) {
				Utils.tip(this, "请先登录");
				return;
			}
			LoadingPopup.show(this);
			String userid = "0";
			String commentId = "0";
			if (replyUid != null) {
				userid = replyUid;
				commentId = model.getCommentid();
			}
			new InsertForumRequest(this, new InsertForumRequest.Params(
					model.getId(), commentId, userid,
					APP.getSession().getSid(), content,APP.getSession().getPkey()))
					.request(new RequestHandler() {

						@Override
						public void onSuccess(Object object) {
							Utils.tip(BBSDetailActivity.this, "评论成功");
							editText.setText("");
//							listView.load(true);

						}
						
						@Override
						public void onError(int error, String msg) {
							Utils.tip(BBSDetailActivity.this, "评论失败");
							
						}

						@Override
						public void onComplete() {
							LoadingPopup.hide(BBSDetailActivity.this);

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
						"分享到朋友圈", "分享到新浪微博", "举报" }),
				new OnIOSItemClickListener() {

					@Override
					public void oniositemclick(int pos, String text) {
						if (pos == 5) {
							ReportActivity.open(BBSDetailActivity.this, new ReportActivity.Model(model.getId()));
						} else {

							SHARE_MEDIA media = new SHARE_MEDIA[] {
									SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
									SHARE_MEDIA.WEIXIN,
									SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA }[pos];
							File bitmapFile = ImageLoader.getInstance().getDiscCache().get(model.getImg());
							ShareUtils.shareWebsite(BBSDetailActivity.this,
									media, model.getTitle(), APP.getAppConfig()
											.getShareForumcontent(model.getId()),bitmapFile);

						}

					}

				}));
		
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
		replyUid = model.getUserid();
		editText.requestFocus();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(editText.isFocused()){
			container.requestFocus();
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				replyUid = null;
			return true;
		}
		return false;
	}

}