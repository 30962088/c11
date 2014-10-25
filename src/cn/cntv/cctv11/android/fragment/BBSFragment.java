package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.BBSListAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.GetTopicRequest;
import cn.cntv.cctv11.android.fragment.network.GetTopicRequest.Result;
import cn.cntv.cctv11.android.widget.BBSHeaderView;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BBSFragment extends BaseFragment implements OnLoadListener{

	
	public static BBSFragment newInstance(){
		return new BBSFragment();
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
		return inflater.inflate(R.layout.bbs_layout, null);
	}
	
	private BBSListAdapter adapter;
	
	private List<BBSListAdapter.Model> list = new ArrayList<BBSListAdapter.Model>();
	
	private BBSHeaderView headerView;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		headerView = new BBSHeaderView(getActivity());
		BaseListView listView = (BaseListView) view.findViewById(R.id.listview);
		listView.getRefreshableView().addHeaderView(headerView);
		headerView.setModel(new BBSHeaderView.Model(10, 10));
		adapter = new BBSListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnLoadListener(this);
		listView.load(true);
	}

	@Override
	public BaseClient onLoad(int offset, int limit) {
		GetTopicRequest request = new GetTopicRequest(getActivity(), new GetTopicRequest.Params(offset, limit));
		return request;
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		Result result = (Result) object;
		if(offset == 1){
			list.clear();
		}
		List<BBSListAdapter.Model> results = result.toModels();
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
