package cn.cntv.cctv11.android.fragment;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.StageListAdapter;
import cn.cntv.cctv11.android.adapter.StageListAdapter.StageItem;
import cn.cntv.cctv11.android.utils.DateUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class StageFragment extends BaseFragment{

	
	public static class Model implements Serializable{
		private Date date;
		private ArrayList<StageItem> list;
		public Model(Date date, ArrayList<StageItem> list) {
			super();
			this.date = date;
			this.list = list;
		}
		
		public String getDateString(){
			return new SimpleDateFormat("yyyy/MM/dd").format(date)+" "+DateUtils.getWeek(date);
		}
		
	}
	
	public static StageFragment newInstance(Model model){
		StageFragment fragment = new StageFragment();
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		model = (Model) getArguments().getSerializable("model");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_stage, null);
	}
	
	private ListView listView;
	
	private Model model;


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		listView = (ListView) view
				.findViewById(R.id.listview);
		
		TextView dateView = (TextView) view.findViewById(R.id.date);
		
		dateView.setText(model.getDateString());
		
		listView.setAdapter(new StageListAdapter(getActivity(), model.list));
	}

	
}
