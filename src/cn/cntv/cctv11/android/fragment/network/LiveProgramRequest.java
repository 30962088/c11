package cn.cntv.cctv11.android.fragment.network;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;

import cn.cntv.cctv11.android.adapter.LiveListAdapter;
import cn.cntv.cctv11.android.adapter.LiveListAdapter.Model.State;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class LiveProgramRequest extends BaseClient {

	public static class Program {
		private String t;
		private long st;
		private long et;
		private String showTime;
		private long duration;

	}

	public static class CCTV11 {
		private String isLive;
		private long liveSt;
		private String channelName;
		private List<Program> program;
	}

	public static class Result {
		private CCTV11 cctv11;

		public List<LiveListAdapter.Model> toLiveList() {
			List<LiveListAdapter.Model> list = new ArrayList<LiveListAdapter.Model>();
			for (Program program : cctv11.program) {
				State state = null;
				if (program.st == cctv11.liveSt) {
					state = State.CURRENT;
				} else if (program.et <= cctv11.liveSt) {
					state = State.OUTTIME;
				} else {
					state = State.INTIME;
				}
				list.add(new LiveListAdapter.Model(program.showTime, program.t,
						state));
			}
			return list;
		}
	}

	public LiveProgramRequest(Context context) {
		super(context);
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

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd");

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("serviceId", "cbox");
		params.add("c", "cctv11");
		params.add("d", DATE_FORMAT.format(new Date()));
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://api.cntv.cn/epg/epginfo";
	}

	@Override
	protected boolean isRelativeUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}