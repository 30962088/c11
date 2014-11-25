package cn.cntv.cctv11.android;

import java.util.ArrayList;

import java.util.List;

import cn.cntv.cctv11.android.adapter.AppListAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AppListActivity extends BaseActivity implements OnLoadListener{

	public static void open(Context context) {
		context.startActivity(new Intent(context, AppListActivity.class));
	}
	
	private BaseListView listView;
	
	private List<AppListAdapter.Model> list = new ArrayList<AppListAdapter.Model>();
	
	private AppListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.app_list_layout);
		adapter = new AppListAdapter(this, list);
		listView = (BaseListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
//		listView.setOnLoadListener(this);
//		listView.load(true);
	}

	@Override
	public BaseClient onLoad(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		// TODO Auto-generated method stub
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
