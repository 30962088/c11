package cn.cntv.cctv11.android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.mengle.lib.utils.Utils;

import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter;
import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter.OnCommentBtnClickListener;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.InsertCommentRequest;
import cn.cntv.cctv11.android.fragment.network.NewsCommentRequest;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.NewsCommentRequest.Params;
import cn.cntv.cctv11.android.utils.LoadingPopup;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class NewsCommentActivity extends BaseActivity implements OnLoadListener,OnClickListener,OnCommentBtnClickListener,OnTouchListener{

	public static class Model implements Serializable{
		private String id;
		private int count;
		private String title;
		public Model(String id, int count, String title) {
			super();
			this.id = id;
			this.count = count;
			this.title = title;
		}
		
	}
	
	public static void open(Context context,Model model){
		
		Intent intent = new Intent(context, NewsCommentActivity.class);
		
		intent.putExtra("model", model);
		
		context.startActivity(intent);
		
	}
	private View container;
	private BaseListView listView;
	
	private List<NewsCommentListAdapter.Model> list = new ArrayList<NewsCommentListAdapter.Model>();
	
	private NewsCommentListAdapter adapter;
	
	private Model model;
	
	private EditText editText;
	
	private View notLoginView;
	
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
		findViewById(R.id.back).setOnClickListener(this);
		editText = (EditText) findViewById(R.id.edit);
		findViewById(R.id.sendBtn).setOnClickListener(this);
		listView = (BaseListView) findViewById(R.id.listview);
		View headerView = LayoutInflater.from(this).inflate(R.layout.news_comment_header, null);
		listView.getRefreshableView().addHeaderView(headerView);
		listView.getRefreshableView().setOnTouchListener(this);
		TextView titleView = (TextView) headerView.findViewById(R.id.title);
		titleView.setText(model.title);
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
		return list.size()>limit?true:false;
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
		case R.id.not_login_view:
			toLogin();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.sendBtn:
			String content = editText.getText().toString();
			LoadingPopup.show(this);
			new InsertCommentRequest(this, new InsertCommentRequest.Params(model.id,iscommentid, isuserid, APP.getSession().getSid(), content)).request(new RequestHandler() {
				
				@Override
				public void onSuccess(Object object) {
					Utils.tip(NewsCommentActivity.this, "评论成功");
					editText.setText("");
//					listView.load(true);
					
				}
				
				@Override
				public void onError(int error, String msg) {
					Utils.tip(NewsCommentActivity.this, "评论失败");
					
				}

				@Override
				public void onComplete() {
					LoadingPopup.hide(NewsCommentActivity.this);
					
				}
			});
			break;

		default:
			break;
		}
		
	}

	private String isuserid = "0";
	
	private String iscommentid = "0";

	@Override
	public void onCommentBtnClick(
			cn.cntv.cctv11.android.adapter.NewsCommentListAdapter.Model model) {
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
