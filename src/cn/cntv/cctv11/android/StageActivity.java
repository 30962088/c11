package cn.cntv.cctv11.android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import cn.cntv.cctv11.android.adapter.StageListAdapter;
import cn.cntv.cctv11.android.adapter.StageListAdapter.StageItem;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.adapter.TabsAdapter.Pager;
import cn.cntv.cctv11.android.fragment.StageFragment;
import cn.cntv.cctv11.android.fragment.network.StageRequest.Result;
import cn.cntv.cctv11.android.fragment.network.StageRequest.StageGroup;
import cn.cntv.cctv11.android.widget.HIndicator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class StageActivity extends BaseActivity {

	public static class Model implements Serializable {
		private Date start;
		private Date end;
		private Result result;

		public Model(Date start, Date end, Result result) {
			super();
			this.start = start;
			this.end = end;
			this.result = result;
		}

	}

	public static void open(Context context, Model model) {

		Intent intent = new Intent(context, StageActivity.class);

		intent.putExtra("model", model);

		context.startActivity(intent);

	}

	private Model model;

	private ViewPager pager;

	private HIndicator indicator;
	

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
		setContentView(R.layout.stage_layout);
		pager = (ViewPager) findViewById(R.id.pager);
		indicator = (HIndicator) findViewById(R.id.indicator);
		init();
	}

	private void init(){
		
		Date start = model.start,end = model.end;
		int length = (int) ((end.getTime() -start.getTime())/(1000*3600*24));
		List<Pager> pagers = new ArrayList<Pager>();
		for(int i = 0;i<length;i++){
			
			Calendar c = DateUtils.toCalendar(start);
			c.setTime(start);
			c.add(Calendar.DATE, i);
			Date date = c.getTime();
			
			ArrayList<StageListAdapter.StageItem> list = new ArrayList<StageListAdapter.StageItem>();
			for(StageGroup group: model.result.getGrouplist()){
				if(DateUtils.isSameDay(date, group.getDate())){
					list.addAll(group.toList());
					break;
				}
			}
			pagers.add(new Pager(cn.cntv.cctv11.android.utils.DateUtils.getPageTitle(date)
					, StageFragment.newInstance(new StageFragment.Model(date, list))));
			
		}
		pager.setAdapter(new TabsAdapter(getSupportFragmentManager(), pagers));
		indicator.setViewPager(pager);
	}
}
