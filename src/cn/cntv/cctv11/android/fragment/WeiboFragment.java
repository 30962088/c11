package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.WeiboListAdapter;
import cn.cntv.cctv11.android.adapter.WeiboListAdapter.Model;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.GetWeiboRequest;
import cn.cntv.cctv11.android.fragment.network.GetWeiboRequest.Result;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeiboFragment extends BaseFragment implements OnLoadListener{
	
	
	public static WeiboFragment newInstance(){
		return new WeiboFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.weibo_layout, null);
	}
	
	
	private List<WeiboListAdapter.Model> list = new ArrayList<WeiboListAdapter.Model>();
	private WeiboListAdapter adapter;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		BaseListView listView = (BaseListView) view.findViewById(R.id.listview);
		adapter = new WeiboListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnLoadListener(this);
		listView.load(true);
	}

	@Override
	public BaseClient onLoad(int offset, int limit) {
		GetWeiboRequest.Params params = new GetWeiboRequest.Params("0", offset, limit);
		return new GetWeiboRequest(getActivity(), params);
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		List<Model> results = ((Result)object).toModelList();
		if(offset == 0){
			list.clear();
		}
		list.addAll(results);
		adapter.notifyDataSetChanged();
		return results.size()>=limit?true:false;
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
