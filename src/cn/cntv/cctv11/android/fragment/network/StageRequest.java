package cn.cntv.cctv11.android.fragment.network;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import cn.cntv.cctv11.android.adapter.StageListAdapter;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.adapter.StageListAdapter.StageItem;
import cn.cntv.cctv11.android.adapter.TabsAdapter.Pager;
import cn.cntv.cctv11.android.fragment.NewsFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.mengle.lib.utils.Utils;

public class StageRequest extends BaseClient {

	public static class Stage{
		private String stageid;
		private String stagetitle;
		private String stageurl;
		private String stagecity;
		private String stagetheater;
		private String endtime;
		private String starttime;
		public StageItem toModel(){
			return new StageItem(stagetitle, stageurl, stagecity+"-"+stagetheater, DATE_FORMAT.format(getStartDate()));
		}
		
		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
		
		private Date getEndDate(){
			long count = Long.parseLong(endtime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
			return new Date(count);
		}
		private Date getStartDate(){
			long count = Long.parseLong(starttime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
			return new Date(count);
		}
	}
	
	public static class StageGroup{
		private int datecount;
		private String showdatetime;
		private List<Stage> stagelist;
		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
		public StageListAdapter.StageGroup toModel(){
			List<StageItem> list = new ArrayList<StageListAdapter.StageItem>();
			for(Stage stage : stagelist){
				list.add(stage.toModel());
			}
			return new StageListAdapter.StageGroup(DATE_FORMAT.format(getDate())+" "+Utils.getWeekOfDate(getDate()), list);
		}
		private Date getDate(){
			long count = Long.parseLong(showdatetime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
			return new Date(count);
		}
	}

	public static class Result {
		private List<StageGroup> countlist;
		public List<StageListAdapter.StageGroup> toStageListModel(){
			List<StageListAdapter.StageGroup> list = new ArrayList<StageListAdapter.StageGroup>();
			for(StageGroup group : countlist){
				list.add(group.toModel());
			}
			return list;
		}
	}
	
	public static class Params{
		private Date start;
		private Date end;
		public Params(Date start, Date end) {
			super();
			this.start = start;
			this.end = end;
		}
		
	}
	
	private Params params;
	

	

	public StageRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		return new Gson().fromJson(str, Result.class);

	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onServerError(int arg0, Header[] arg1, byte[] arg2,
			Throwable arg3) {
		// TODO Auto-generated method stub

	}
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method", "stagecount");
/*		params.add("startdate", DATE_FORMAT.format(this.params.start));
		params.add("enddate", DATE_FORMAT.format(this.params.end));*/
		params.add("startdate", "2014/01/01");
		params.add("enddate", "2015/01/01");
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "stagecount";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
