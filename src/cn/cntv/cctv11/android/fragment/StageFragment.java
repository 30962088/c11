package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.StageListAdapter;
import cn.cntv.cctv11.android.adapter.StageListAdapter.StageGroup;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.StageRequest;
import cn.cntv.cctv11.android.fragment.network.StageRequest.Result;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StageFragment extends BaseFragment implements OnRefreshListener<ListView>{

	public static StageFragment newInstance(){
		StageFragment fragment = new StageFragment();
		return fragment;
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
		return inflater.inflate(R.layout.activity_stage, null);
	}


	
	private PullToRefreshPinnedSectionListView listView;


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		listView = (PullToRefreshPinnedSectionListView) view
				.findViewById(R.id.listview);
		listView.setOnRefreshListener(this);
		request();
		
	}

	private void request() {
		Date start = new Date();
		Date end = new Date();
		end.setYear(end.getYear() + 1);
		StageRequest request = new StageRequest(getActivity(),
				new StageRequest.Params(start, end));
		request.request(new RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				Result result = (Result) object;
				listView.setAdapter(new StageListAdapter(getActivity(),result.toStageListModel()));
				listView.onRefreshComplete();
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

			}

			@Override
			public void onServerError(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(int error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		request();
		
	}

}
