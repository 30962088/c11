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
import cn.cntv.cctv11.android.adapter.WallPagerListAdapter;
import cn.cntv.cctv11.android.adapter.WeiboListAdapter;
import cn.cntv.cctv11.android.utils.ImageUtils;
import cn.cntv.cctv11.android.utils.SizeUtils;
import cn.cntv.cctv11.android.utils.ImageUtils.Size;
import cn.cntv.cctv11.android.widget.WeiboItemView.Content;
import cn.cntv.cctv11.android.widget.WeiboItemView.Count;
import cn.cntv.cctv11.android.widget.WeiboItemView.Model;
import cn.cntv.cctv11.android.widget.WeiboItemView.Photo;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class GetPagerRequest extends BaseClient {

	public static class Attachment {
		private String attachmentid;
		private String attachmentname;
		private String attachmentguid;
		private String attachmentformat;
		private long attachmentsize;
		private int attachmentwidth;
		private int attachmentheight;
		private String attachmentdate;
		private String attachmentimgurl;
		private int isfirstimg;

		public String getSize() {
			return SizeUtils.humanReadableByteCount(attachmentsize, true);
		}

		public String getImg(Size size) {
			Integer width = null, height = null;
			if (size != null) {
				if (size.getWidth() != null) {
					if (size.getWidth() > attachmentwidth) {
						width = attachmentwidth;
					} else {
						width = size.getWidth();
					}
				}
				if (size.getHeight() != null) {
					if (size.getHeight() > attachmentheight) {
						height = attachmentheight;
					} else {
						height = size.getHeight();
					}
				}
			}

			return ImageUtils.getTheImage(attachmentguid+attachmentformat, new Size(width,
					height));
		}
	}

	public static class Pager implements Serializable {
		private String paperid;
		private String papername;
		private String datetime;
		private Attachment attachment;

		public WallPagerListAdapter.Model toModel(Size size) {
			return new WallPagerListAdapter.Model(attachment.getImg(size),
					papername, attachment.getSize(),attachment.attachmentimgurl);
		}
	}

	public static class Result {
		private List<Pager> paperlist;
		public List<WallPagerListAdapter.Model> toModels(Size size){
			List<WallPagerListAdapter.Model> list = new ArrayList<WallPagerListAdapter.Model>();
			for(Pager pager:paperlist){
				list.add(pager.toModel(size));
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

	public GetPagerRequest(Context context, Params params) {
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
		params.add("method", "paper");
		params.add("pageno", "" + this.params.pageno);
		params.add("pagesize", "" + this.params.pagesize);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/paper";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
