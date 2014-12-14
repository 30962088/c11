package cn.cntv.cctv11.android.fragment.network;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import cn.cntv.cctv11.android.adapter.NewsCommentListAdapter;
import cn.cntv.cctv11.android.adapter.StageListAdapter;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.adapter.StageListAdapter.StageItem;
import cn.cntv.cctv11.android.adapter.TabsAdapter.Pager;
import cn.cntv.cctv11.android.fragment.NewsFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.mengle.lib.utils.Utils;

public class NewsCommentRequest extends BaseClient {

	public static class Comment{
		private String commentid;
		
		private String userid;
		
		private String username;
		
		private String remark;
		
		private String userimgurl;
		
		private String datetime;
		
		private String isusername;
		
		
		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm");
		
		private Date getDateTime(){
			long count = Long.parseLong(datetime.replaceAll("\\/Date\\((.*)\\)\\/", "$1"));
			return new Date(count);
		}
		
		public NewsCommentListAdapter.Model toModel(){
			return new NewsCommentListAdapter.Model(commentid,userimgurl, username, remark, DATE_FORMAT.format(getDateTime()),isusername,userid);
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
	
	public static class Params {
		private int pageno;
		private int pagesize;
		private String contentsid;
		public Params(int pageno, int pagesize, String contentsid) {
			super();
			this.pageno = pageno;
			this.pagesize = pagesize;
			this.contentsid = contentsid;
		}

		

	}
	
	private Params params;
	

	

	public NewsCommentRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		return new Gson().fromJson(str, Result.class);

	}
	
	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method", "comment");
		params.add("pagesize", ""+this.params.pagesize);
		params.add("pageno", ""+this.params.pageno);
		params.add("contentsid", ""+this.params.contentsid);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/comment";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}

	@Override
	public void onError(int error, String msg) {
		// TODO Auto-generated method stub
		
	}
}
