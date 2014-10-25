package cn.cntv.cctv11.android.fragment.network;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;

import cn.cntv.cctv11.android.adapter.BBSListAdapter;
import cn.cntv.cctv11.android.adapter.WeiboListAdapter;
import cn.cntv.cctv11.android.widget.WeiboItemView.Content;
import cn.cntv.cctv11.android.widget.WeiboItemView.Count;
import cn.cntv.cctv11.android.widget.WeiboItemView.Model;
import cn.cntv.cctv11.android.widget.WeiboItemView.Photo;


import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class GetTopicRequest extends BaseClient {

	public static class Topic implements Serializable{
		private String topicid;
		private String userid;
		private String username;
		private String topictitle;
		private String topiccontent;
		private String datetime;
		private String userimgguid;
		private String userimgformat;
		private String userimgurl;
		private int commentcount;
		private String colstatus;
		public BBSListAdapter.Model toModel(){
			return new BBSListAdapter.Model(topictitle, username, getDate(), commentcount);
		}
		private Date getDate(){
			long count = Long.parseLong(datetime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
			return new Date(count);
		}
		
	}
	public static class Result{
		private List<Topic> topiclist;
		
		public List<BBSListAdapter.Model> toModels(){
			List<BBSListAdapter.Model> list = new ArrayList<BBSListAdapter.Model>();
			for(Topic topic : topiclist){
				list.add(topic.toModel());
			}
			return list;
		}
		
		
	}

	public static class Params {
		private int pageno;
		private int pagesize;

		public Params(int pageno, int pagesize) {
			super();
			this.pageno = pageno;
			this.pagesize = pagesize;
		}

	}

	

	private Params params;

	public GetTopicRequest(Context context, Params params) {
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

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method", "topic");
		params.add("pageno", "" + this.params.pageno);
		params.add("pagesize", "" + this.params.pagesize);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/topic";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
