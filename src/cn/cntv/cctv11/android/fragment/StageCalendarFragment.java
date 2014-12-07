package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.Header;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.StageActivity;
import cn.cntv.cctv11.android.adapter.CalendarListAdapter;
import cn.cntv.cctv11.android.adapter.CalendarListAdapter.OnCalendarGridItemClickListener;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.StageRequest;
import cn.cntv.cctv11.android.fragment.network.StageRequest.DateCount;
import cn.cntv.cctv11.android.fragment.network.StageRequest.Result;
import cn.cntv.cctv11.android.utils.CalendarUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StageCalendarFragment extends BaseFragment implements OnCalendarGridItemClickListener{

	public static StageCalendarFragment newInstance() {
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
		adapter = new CalendarListAdapter(getActivity(), list,this);
		listView.setAdapter(adapter);
		request();
	}

	private Date getRelativeMonth(int d) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, d);
		return calendar.getTime();
	}

	private Date getStartDate() {
		return getRelativeMonth(-2);
	}

	private Date getEndDate() {
		return getRelativeMonth(4);
	}

	private List<CalendarListAdapter.Model> getModels(Date start, Date end,List<DateCount> dateCounts) {
		int sy = DateUtils.toCalendar(start).get(Calendar.YEAR),ey = DateUtils.toCalendar(end).get(Calendar.YEAR),
				ly = ey-sy;
		int sm = DateUtils.toCalendar(start).get(Calendar.MONTH), em = DateUtils
				.toCalendar(end).get(Calendar.MONTH), length = ly*12 + em - sm;
		List<CalendarListAdapter.Model> list = new ArrayList<CalendarListAdapter.Model>();
		for (int i = 0; i <= length; i++) {
			Calendar calendar = DateUtils.toCalendar(start);
			calendar.add(Calendar.MONTH, i);
			int year = calendar.get(Calendar.YEAR), month = calendar
					.get(Calendar.MONTH)+1;
			
			list.add(new CalendarListAdapter.Model(calendar.getTime(), CalendarUtils.getDay(year, month, dateCounts)));
		}
		
		return list;

	}
	
	private Date startDate;
	
	private Date endDate;
	
	private Result result;

	private void request() {

		startDate = getStartDate();
		endDate = getEndDate();
		StageRequest request = new StageRequest(getActivity(),
				new StageRequest.Params(startDate, endDate));

		request.request(new BaseClient.SimpleRequestHandler() {
			@Override
			public void onSuccess(Object object) {
				result = (StageRequest.Result) object;
				list.addAll(getModels(startDate, endDate, result.getDateCounts()));
				adapter.notifyDataSetChanged();
			}
		});

		
	}

	@Override
	public void OnCalendarGridItemClick(Date date) {
		StageActivity.open(getActivity(), new StageActivity.Model(startDate, endDate, result,date));
		
	}

}
