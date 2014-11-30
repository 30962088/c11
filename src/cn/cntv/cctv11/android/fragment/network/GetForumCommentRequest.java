package cn.cntv.cctv11.android.fragment.network;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter;
import cn.cntv.cctv11.android.fragment.network.NewsCommentRequest.Comment;
import cn.cntv.cctv11.android.fragment.network.NewsCommentRequest.Result;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetForumCommentRequest extends BaseClient{

	public static class Params{
		private String topicid;
		private int pageno;
		private int pagesize;
		public Params(String topicid, int pageno, int pagesize) {
			super();
			this.topicid = topicid;
			this.pageno = pageno;
			this.pagesize = pagesize;
		}	
	}
	
	public static class Comment{
		private String username;
		private String userid;
		private String datetime;
		private String userimgurl;
		private String remark;
		private String isusername;
		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		private Date getDateTime(){
			long count = Long.parseLong(datetime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
			return new Date(count);
		}
		public NewsCommentListAdapter.Model toModel(){
			return new NewsCommentListAdapter.Model(userimgurl, username, remark, DATE_FORMAT.format(getDateTime()),isusername,userid);
		}
	}
	
	public static class Result {
		private List<Comment> commentlist;
		public  List<NewsCommentListAdapter.Model> toCommentList(){
			List<NewsCommentListAdapter.Model> list = new ArrayList<NewsCommentListAdapter.Model>();
			for(Comment comment : commentlist){
				list.add(comment.toModel());
			}
			return list;
		}
	}
	
	private Params params;

	public GetForumCommentRequest(Context context, Params params) {
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
		params.add("method", "forumcomment");
		params.add("pagesize", ""+this.params.pagesize);
		params.add("pageno", ""+this.params.pageno);
		params.add("topicid", ""+this.params.topicid);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/forumcomment";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
	
	
	

}
