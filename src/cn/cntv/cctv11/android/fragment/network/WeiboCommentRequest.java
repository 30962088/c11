package cn.cntv.cctv11.android.fragment.network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import cn.cntv.cctv11.android.APP.Meta;
import cn.cntv.cctv11.android.WeiboDetailActivity.DataSource;
import cn.cntv.cctv11.android.WeiboDetailActivity.WeiboDataSource;
import cn.cntv.cctv11.android.WeiboDetailActivity.WeiboRequest;
import cn.cntv.cctv11.android.WeiboDetailActivity.WeiboResult;
import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.CommentItem;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class WeiboCommentRequest extends BaseClient {

	public static class Params {
		private String source = "3636217490";
		private String id;
		private int count;
		private int page;

		public Params(String id, int count, int page) {
			super();
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
		params.add("source", "" + this.params.source);
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
		return "http://api.weibo.com/2/comments/show.json";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
	
	@Override
	protected Header[] fillHeaders() {
		// TODO Auto-generated method stub
		return new Header[]{new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"),
				new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4"),
				new BasicHeader("Cache-Control", "max-age=0"),
				new BasicHeader("Connection", "keep-alive"),
				new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36"),
				new BasicHeader("Cookie", "SINAGLOBAL=2702185292728.245.1416152276205; __utma=15428400.1310452581.1416234941.1416234941.1416234941.1; __utmz=15428400.1416234941.1.1.utmcsr=login.sina.com.cn|utmccn=(referral)|utmcmd=referral|utmcct=/sso/login.php; myuid=5135223393; un=poolk002@qq.com; wvr=6; SUS=SID-3197405540-1416403241-XD-5xg2y-40db5e53b0438c384a4a9b968ea80303; SUE=es%3D459b151c98062fa4d7415c1794f0fcce%26ev%3Dv1%26es2%3D6757f82db363d697e156bcf7d114b32d%26rs0%3DIiBmiadUaMInVxLyDBZ9G7d9w4%252BWJjjfx9dRlpSWZCv4GHXMgTMWpmIhjkv7CSlpmWWO3DkBV%252FJJFqreYGzPdGdOT6x6Xl5iZl%252FoK0hQqH3ke4fSIYz1%252F4CXX8c43cbe7lAsnEz9GM5%252FUAyo1I2EjWa7%252F5QrryDltGoGNrKMBkI%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1416403241%26et%3D1416489641%26d%3Dc909%26i%3D0303%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D2%26st%3D0%26uid%3D3197405540%26name%3Dpoolk002%2540qq.com%26nick%3D%25E5%25B0%258F%25E5%25B0%258F%25E9%25A3%259E%25E7%259A%2584demo%26fmp%3D%26lcp%3D; SUB=_2AkMjMBYea8NlrABQkf8RzG7naI1H-jyQ5troAn7uJhIyGxh-7lc3qSUJr_obo9TjIpPlRFGYNTDqXJXQxg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5.dbrNY_cNDKpNHwTPhW6P5JpX5KMt; ALF=1447939241; SSOLoginState=1416403241; _s_tentry=login.sina.com.cn; Apache=215347532648.5932.1416402989826; UOR=,,www.google.com.hk; ULV=1416402989950:4:4:4:215347532648.5932.1416402989826:1416325753905; JSESSIONID=4DBBE6A852A29DF0DD0AF285ACCE38A5")};
	}

	

}
