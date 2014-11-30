package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.List;



import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.SpecialDetailActivity;
import cn.cntv.cctv11.android.adapter.NewsListAdapter;
import cn.cntv.cctv11.android.adapter.NewsListAdapter.Model;
import cn.cntv.cctv11.android.fragment.SliderFragment.OnSliderItemClickListener;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.ContentsRequest;
import cn.cntv.cctv11.android.fragment.network.ContentsRequest.News;
import cn.cntv.cctv11.android.fragment.network.ContentsRequest.Result;
import cn.cntv.cctv11.android.widget.BaseListView;
import cn.cntv.cctv11.android.widget.BaseListView.OnLoadListener;
import cn.cntv.cctv11.android.widget.BaseListView.RequestResult;
import cn.cntv.cctv11.android.widget.BaseListView.Type;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class NewsFragment extends BaseFragment implements OnLoadListener,OnSliderItemClickListener,OnItemClickListener{
	
	public static NewsFragment newInstance(int categoryId){
		NewsFragment fragment = new NewsFragment();
		Bundle args = new Bundle();
		args.putInt("categoryId", categoryId);
		fragment.setArguments(args);
		return fragment;
	}
	
	private int categoryId;
	
	public NewsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		categoryId = getArguments().getInt("categoryId");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.common_list, null);
	}
	
	private ArrayList<Model> list = new ArrayList<Model>();
	
	private NewsListAdapter adapter;
	
	private View listHeader;
	private View listHeaderInner;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		BaseListView listView = (BaseListView) view.findViewById(R.id.list);
		listView.setOnLoadListener(this);
		listHeader = LayoutInflater.from(getActivity()).inflate(R.layout.list_header, null);
		listView.getRefreshableView().addHeaderView(listHeader);
		listView.setOnItemClickListener(this);
		listHeaderInner = listHeader.findViewById(R.id.list_header);
		adapter = new NewsListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.load(true);
	}
	

	private int offset;
	
	private int size;
	
	



	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BaseClient onLoad(int offset, int limit) {
		// TODO Auto-generated method stub
		return new ContentsRequest(getActivity(), new ContentsRequest.Params( categoryId, offset, limit));
	}

	@Override
	public boolean onLoadSuccess(Object object, int offset, int limit) {
		Result result = (Result) object;
		ArrayList<News> list = result.getList();
		if(offset == 1){
			this.list.clear();
			ArrayList<SliderFragment.Model> sliderList =  News.toSliderList(result.getLunbolist());
			if(sliderList.size()>0){
				listHeaderInner.setVisibility(View.VISIBLE);
				getChildFragmentManager().beginTransaction().replace(R.id.list_header,SliderFragment.newInstance(this,sliderList)).commit();
			}else{
				listHeaderInner.setVisibility(View.GONE);
			}
			
		}
		
		
		this.list.addAll( News.toNewsList(list));
		adapter.notifyDataSetChanged();
		
		return list.size()>limit?true:false;
	}

	@Override
	public void OnSliderItemClick(
			cn.cntv.cctv11.android.fragment.SliderFragment.Model model) {
		SpecialDetailActivity.open(getActivity(), model.toDetailParams());
		
	}

	@Override
	public Type getRequestType() {
		// TODO Auto-generated method stub
		return Type.PAGE;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Model model = (Model) adapter.getItem(position-2);
		SpecialDetailActivity.open(getActivity(), model.toDetailParams());
		
		
		
	}
	
	

}
