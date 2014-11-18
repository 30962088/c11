package cn.cntv.cctv11.android;

import java.util.ArrayList;
import java.util.List;


import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.GetForumCommentRequest;
import cn.cntv.cctv11.android.fragment.network.NewsCommentRequest;
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
import android.widget.EditText;

public class BBSDetailActivity extends BaseActivity implements OnLoadListener,OnClickListener{

	
	public static void open(Context context,Model model){
		
		Intent intent = new Intent(context, BBSDetailActivity.class);
		
		intent.putExtra("model", model);
		
		context.startActivity(intent);
		
	}
	
	private BaseListView listView;
	
	private List<NewsCommentListAdapter.Model> list = new ArrayList<NewsCommentListAdapter.Model>();
	
	private NewsCommentListAdapter adapter;
	
	private Model model;
	
	private EditText editText;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
		setContentView(R.layout.bbs_detail_layout);
		editText = (EditText) findViewById(R.id.edit);
		findViewById(R.id.sendBtn).setOnClickListener(this);
		listView = (BaseListView) findViewById(R.id.listview);
		BBSDetailHeaderView headerView = new BBSDetailHeaderView(this);
		headerView.setModel(model);
		listView.getRefreshableView().addHeaderView(headerView);
		
		adapter = new NewsCommentListAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setOnLoadListener(this);
		listView.load(true);
	}

	

	@Override
	public BaseClient onLoad(int offset, int limit) {
		
		return new GetForumCommentRequest(this, new GetForumCommentRequest.Params(model.getId(),offset, limit));
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		GetForumCommentRequest.Result result = (GetForumCommentRequest.Result) object;
		
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
		case R.id.sendBtn:
			String content = editText.getText().toString();
			/*new InsertCommentRequest(this, new InsertCommentRequest.Params(model.id,"0", "0", "134", content)).request(new RequestHandler() {
				
				@Override
				public void onSuccess(Object object) {
					Utils.tip(BBSDetailActivity.this, "评论成功");
					editText.setText("");
					listView.load(true);
					
				}
				
				@Override
				public void onServerError(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					Utils.tip(BBSDetailActivity.this, "评论失败");
					
				}
				
				@Override
				public void onError(int error) {
					Utils.tip(BBSDetailActivity.this, "评论失败");
					
				}
			});*/
			break;

		default:
			break;
		}
		
	}
	
}
