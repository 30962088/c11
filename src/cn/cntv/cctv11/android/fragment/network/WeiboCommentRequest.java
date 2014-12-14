package cn.cntv.cctv11.android.fragment.network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import cn.cntv.cctv11.android.WeiboDetailActivity.DataSource;
import cn.cntv.cctv11.android.WeiboDetailActivity.WeiboDataSource;
import cn.cntv.cctv11.android.WeiboDetailActivity.WeiboRequest;
import cn.cntv.cctv11.android.WeiboDetailActivity.WeiboResult;
import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.CommentItem;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class WeiboCommentRequest extends BaseClient {

	public static class Params {
		private String access_token;
		private String id;
		private int count;
		private int page;

		public Params(String access_token, String id, int count, int page) {
			super();
			this.access_token = access_token;
			this.id = id;
			this.count = count;
			this.page = page;
		}

	}

	public static class User {
		private String id;
		private String screen_name;
		private String name;
		private String profile_image_url;

	}

	public static class Comment {

		private String id;
		private String created_at;
		private String text;
		private User user;

		public CommentItem toCommentItem() {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(getCreateDate());
			return new CommentItem(user.profile_image_url, user.screen_name,
					text, time);
		}

		private Date getCreateDate() {
			
			return new Date(created_at);
		}

	}

	public static class Result implements WeiboResult{
		private List<Comment> comments;
		private long total_number;

		@Override
		public DataSource toDataSource() {
			List<CommentItem> list = new ArrayList<CommentItem>();
			for (Comment comment : comments) {
				list.add(comment.toCommentItem());
			}
			return new DataSource(total_number, list);
		}
	}

	private Params params;

	public WeiboCommentRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		// TODO Auto-generated method stub
		return new Gson().fromJson(str, Result.class);
	}

	@Override
	protected RequestParams getParams() {
		
		RequestParams params = new RequestParams();
		params.add("access_token", "" + this.params.access_token);
		params.add("id", "" + this.params.id);
		params.add("count", "" + this.params.count);
		params.add("page", "" + this.params.page);
		return params;
	}

	@Override
	protected boolean isRelativeUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "https://api.weibo.com/2/comments/show.json";
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
