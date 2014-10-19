package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.LiveListAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.LiveProgramRequest;
import cn.cntv.cctv11.android.fragment.network.LiveProgramRequest.Result;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LiveFragment extends BaseFragment {
	
	public static LiveFragment newInstance(){
		return new LiveFragment();
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
		return inflater.inflate(R.layout.live_layout, null);
	}
	
	private List<LiveListAdapter.Model> list = new ArrayList<LiveListAdapter.Model>();
	
	private LiveListAdapter adapter;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ListView listView = (ListView) view.findViewById(R.id.listview);
		adapter = new LiveListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		request();
	}

	private void request() {
		LiveProgramRequest request = new LiveProgramRequest(getActivity());
		request.request(new RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				list.clear();
				Result result = (Result) object;
				list.addAll(result.toLiveList());
				adapter.notifyDataSetChanged();

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
		});
	}

}
