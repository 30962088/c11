package cn.cntv.cctv11.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.mengle.lib.utils.Utils;

import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter;
import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter.OnCommentBtnClickListener;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.GetForumCommentRequest;
import cn.cntv.cctv11.android.fragment.network.InsertCommentRequest;
import cn.cntv.cctv11.android.fragment.network.InsertForumRequest;
import cn.cntv.cctv11.android.fragment.network.NewsCommentRequest;
import cn.cntv.cctv11.android.utils.LoadingPopup;
import cn.cntv.cctv11.android.widget.BBSDetailHeaderView.Model;
import cn.cntv.cctv11.android.widget.BBSDetailHeaderView;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

public class BBSDetailActivity extends BaseActivity implements OnLoadListener,
		OnClickListener, OnItemClickListener,OnCommentBtnClickListener {

	public static void open(Context context, Model model) {

		Intent intent = new Intent(context, BBSDetailActivity.class);

		intent.putExtra("model", model);

		context.startActivity(intent);

	}

	private BaseListView listView;

	private List<NewsCommentListAdapter.Model> list = new ArrayList<NewsCommentListAdapter.Model>();

	private NewsCommentListAdapter adapter;

	private Model model;

	private EditText editText;

	private String replyUid;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
		setContentView(R.layout.bbs_detail_layout);
		editText = (EditText) findViewById(R.id.edit);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.sendBtn).setOnClickListener(this);
		listView = (BaseListView) findViewById(R.id.listview);
		BBSDetailHeaderView headerView = new BBSDetailHeaderView(this);
		headerView.setModel(model);
		listView.getRefreshableView().addHeaderView(headerView);
		listView.setOnItemClickListener(this);
		adapter = new NewsCommentListAdapter(this, list,this);
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

		}
		List<NewsCommentListAdapter.Model> list = result.toCommentList();
		this.list.addAll(list);
		adapter.notifyDataSetChanged();
		return list.size() > limit ? true : false;
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
					APP.getSession().getSid(), content))
					.request(new RequestHandler() {

						@Override
						public void onSuccess(Object object) {
							Utils.tip(BBSDetailActivity.this, "评论成功");
							editText.setText("");
							listView.load(true);

						}

						@Override
						public void onError(int error) {
							Utils.tip(BBSDetailActivity.this, "评论失败");

						}

						@Override
						public void onComplete() {
							LoadingPopup.hide(BBSDetailActivity.this);

						}

						@Override
						public void onServerError(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							Utils.tip(BBSDetailActivity.this, "评论失败");

						}

					});
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		hidden();
	}

	private void hidden() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		replyUid = null;
	}

	@Override
	public void onCommentBtnClick(
			cn.cntv.cctv11.android.adapter.NewsCommentListAdapter.Model model) {
		replyUid = model.getUserid();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
	}

}
