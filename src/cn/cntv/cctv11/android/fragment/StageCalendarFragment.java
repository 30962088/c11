package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.CalendarListAdapter;
import cn.cntv.cctv11.android.utils.CalendarUtils;
import cn.cntv.cctv11.android.utils.DateUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StageCalendarFragment extends BaseFragment{
	
	public static StageCalendarFragment newInstance(){
		StageCalendarFragment fragment = new StageCalendarFragment();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.stage_calendar_layout, null);
	}
	
	
	private ListView listView;
	
	private CalendarListAdapter adapter;
	
	private List<CalendarListAdapter.Model> list = new ArrayList<CalendarListAdapter.Model>();
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) view.findViewById(R.id.listview);
		adapter = new CalendarListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		request();
	}

	private void request() {
		ArrayList<CalendarListAdapter.Model> models = new ArrayList<CalendarListAdapter.Model>();
		for(int i = 1;i<=12;i++){
			Calendar calendar = Calendar.getInstance();
			calendar.set(2014, i-1, 1);
			models.add(new CalendarListAdapter.Model(calendar.getTime(), CalendarUtils.getDay(2014, i)));
		}
		list.addAll(models);
		adapter.notifyDataSetChanged();
		
	}

}
