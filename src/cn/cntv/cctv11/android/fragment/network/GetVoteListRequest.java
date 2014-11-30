package cn.cntv.cctv11.android.fragment.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import cn.cntv.cctv11.android.adapter.VoteListAdapter;
import cn.cntv.cctv11.android.utils.DateUtils;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetVoteListRequest extends BaseClient{

	public static class Params{
		private int pageno;
		private int pagesize;
		public Params(int pageno, int pagesize) {
			super();
			this.pageno = pageno;
			this.pagesize = pagesize;
		}
		
	}
	
	public static class Attachment{
		private String attachmentimgurl;
	}
	
	public static class Vote{
		private String voteid;
		private String votetitle;
		private String datetime;
		private String endtime;
		private boolean isover;
		private int voteusercount;
		private String votecontent;
		private Attachment attachment;
		public VoteListAdapter.Model toModel(){
			return new VoteListAdapter.Model(attachment.attachmentimgurl, 
					votetitle, DateUtils.getDate(datetime), voteusercount,"http://cctv11news.1du1du.com/votepage/index?voteid="+voteid);
		}
		
		
	}
	
	public static class Result{
		private List<Vote> votelist;
		
		public List<VoteListAdapter.Model> toList(){
			List<VoteListAdapter.Model> models = new ArrayList<VoteListAdapter.Model>();
			for(Vote vote : votelist){
				models.add(vote.toModel());
			}
			return models;
		}
		
	}
	
	private Params params;

	public GetVoteListRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		// TODO Auto-generated method stub
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
		params.add("method", "vote");
		params.add("pageno", ""+this.params.pageno);
		params.add("pagesize", ""+this.params.pagesize);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/vote";
	}
	
	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
	
	
	
}
