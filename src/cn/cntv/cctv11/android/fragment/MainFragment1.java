package cn.cntv.cctv11.android.fragment;
import com.mengle.lib.utils.Utils;
import com.viewpagerindicator.TabPageIndicator;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.SearchActivity;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest.Category;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest.Result;
import cn.cntv.cctv11.android.widget.NoResultView;
import cn.cntv.cctv11.android.widget.NoResultView.OnRefreshClickListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import com.viewpagerindicator.TabPageIndicator.OnTabScrollListener;

public class MainFragment1 extends BaseFragment implements OnClickListener,OnTabScrollListener,OnRefreshClickListener{
	
	
	public static MainFragment1 newInstance(){
		return new MainFragment1();
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
		return inflater.inflate(R.layout.fragment_tabs_1, null);
	}
	
	private TabPageIndicator indicator;
	
	private ViewPager pager;
	
	private View arrowView;
	
	private NoResultView noResultView;
	
	private View tabContainer;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		tabContainer = view.findViewById(R.id.tab_container);
		noResultView = (NoResultView) view.findViewById(R.id.no_result);
		noResultView.setOnRefreshClickListener(this);
		arrowView = view.findViewById(R.id.arrow);
		view.findViewById(R.id.search_btn).setOnClickListener(this);
		pager = (ViewPager) view.findViewById(R.id.pager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		indicator.setOnTabScrollListener(this);
		request();
	}
	
	private Result result;
	
	private void request(){
		CategoryRequest request = new CategoryRequest(getActivity());
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onSuccess(Object object) {
				result = (Result)object;
				if(result.getResult() == 1000){
					TabsAdapter adapter = new TabsAdapter(getChildFragmentManager(), Category.toPagers(result.getCategorylist()));
					pager.setAdapter(adapter);
					indicator.setViewPager(pager);
					pager.setOffscreenPageLimit(adapter.getCount());
					noResultView.setVisibility(View.GONE);
					tabContainer.setVisibility(View.VISIBLE);
					
				}else{
					onError(result.getResult(), "请求失败");
				}
				
			}
			
			@Override
			public void onError(int error, String msg) {
				Utils.tip(getActivity(), msg);
				noResultView.setVisibility(View.VISIBLE);
				tabContainer.setVisibility(View.GONE);
			}
			
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn:
			SearchActivity.open(getActivity(), new SearchActivity.Model(result.getCategorylist()));
			break;

		default:
			break;
		}
		
	}

	@Override
	public void isLastVisible(boolean visible) {
		arrowView.setVisibility(visible?View.GONE:View.VISIBLE);
		
	}

	@Override
	public void onrefreshclick() {
		request();
		
	}

}
