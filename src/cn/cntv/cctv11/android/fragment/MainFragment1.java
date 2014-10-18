package cn.cntv.cctv11.android.fragment;
import com.viewpagerindicator.TabPageIndicator;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest.Category;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest.Result;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

public class MainFragment1 extends BaseFragment{
	
	
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
		return inflater.inflate(R.layout.fragment_tabs, null);
	}
	
	private TabPageIndicator indicator;
	
	private ViewPager pager;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		pager = (ViewPager) view.findViewById(R.id.pager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		request();
	}
	
	private void request(){
		CategoryRequest request = new CategoryRequest();
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onSuccess(Object object) {
				Result result = (Result)object;
				TabsAdapter adapter = new TabsAdapter(getChildFragmentManager(), Category.toPagers(result.getCategorylist()));
				pager.setAdapter(adapter);
				indicator.setViewPager(pager);
				pager.setOffscreenPageLimit(adapter.getCount());
			}
		});
	}

}
