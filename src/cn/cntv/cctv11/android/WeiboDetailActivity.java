package cn.cntv.cctv11.android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter;
import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.CommentItem;
import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.Model;
import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.TitleItem;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.GetWeiboCommentRequest;
import cn.cntv.cctv11.android.fragment.network.GetWeiboCommentRequest.Result;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import cn.cntv.cctv11.android.widget.WeiboItemView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WeiboDetailActivity extends BaseActivity implements OnLoadListener{
	
	
	public static void open(Context context,WeiboItemView.Model model){
		Intent intent = new Intent(context, WeiboDetailActivity.class);
		intent.putExtra("params", model);
		context.startActivity(intent);
	}
	
	private WeiboItemView itemView;
	
	private TitleItem titleItem = new TitleItem();
	
	private List<CommentItem> list = new ArrayList<CommentItem>();
	
	private WeiboItemView.Model params;
	
	private Model model;
	
	private BaseListView baseListView;
	
	private WeiboCommentListAdapter adapter ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		params = (cn.cntv.cctv11.android.widget.WeiboItemView.Model) getIntent().getSerializableExtra("params");
		titleItem.setShare(params.getContent().getCount().getShare());
		titleItem.setComment(params.getContent().getCount().getComment());
		model = new Model(titleItem, list);
		adapter = new WeiboCommentListAdapter(this, model);
		setContentView(R.layout.activity_weibo);
		itemView = new WeiboItemView(this);
		itemView.setModel(params);
		baseListView = (BaseListView) findViewById(R.id.listview);
		baseListView.getRefreshableView().addHeaderView(itemView);
		baseListView.setAdapter(adapter);
		baseListView.setOnLoadListener(this);
		baseListView.load(true);
	}
	@Override
	public BaseClient onLoad(int offset, int limit) {
		// TODO Auto-generated method stub
		return new GetWeiboCommentRequest(this);
	}
	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		Result result = (Result)object;
		titleItem.setComment(result.getTotal());
		list.addAll(result.getList());
		adapter.notifyDataSetChanged();
//		baseListView.onRefreshComplete();
		return false;
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

}
