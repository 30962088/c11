package cn.cntv.cctv11.android.fragment.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.adapter.TabsAdapter.Pager;
import cn.cntv.cctv11.android.fragment.NewsFragment;
import cn.cntv.cctv11.android.fragment.VideoListFragment;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class CategoryRequest extends BaseClient {

	public static class Category implements Serializable {
		private int categoryid;
		private String categoryname;
		public Category() {
			// TODO Auto-generated constructor stub
		}
		
		public Category(int categoryid, String categoryname) {
			super();
			this.categoryid = categoryid;
			this.categoryname = categoryname;
		}

		public String getCategoryname() {
			return categoryname;
		}
		public int getCategoryid() {
			return categoryid;
		}
		
		public Pager toPager() {
			Fragment fragment = null;
			if(TextUtils.equals("视频", categoryname)){
				fragment = VideoListFragment.newInstance(categoryid);
			}else{
				fragment = NewsFragment.newInstance(categoryid,categoryname);
			}
			return new TabsAdapter.Pager(categoryname,
					fragment);
		}

		public static List<Pager> toPagers(List<Category> results) {
			List<Pager> list = new ArrayList<TabsAdapter.Pager>();
			for (Category result : results) {
				list.add(result.toPager());
			}
			return list;
		}
	}

	public static class Result {
		private ArrayList<Category> categorylist;
		public ArrayList<Category> getCategorylist() {
			return categorylist;
		}
	}
	

	public CategoryRequest(Context context) {
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

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method", "category");
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/category";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
