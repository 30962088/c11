package cn.cntv.cctv11.android.fragment.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import cn.cntv.cctv11.android.adapter.TabsAdapter;
import cn.cntv.cctv11.android.adapter.TabsAdapter.Pager;
import cn.cntv.cctv11.android.fragment.NewsFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class CategoryRequest extends BaseClient {

	public static class Category {
		private int categoryid;
		private String categoryname;

		public Pager toPager() {
			return new TabsAdapter.Pager(categoryname,
					NewsFragment.newInstance(categoryid));
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
		private List<Category> categorylist;
		public List<Category> getCategorylist() {
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
		return "category";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
