package cn.cntv.cctv11.android.fragment.network;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;

import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.CommentItem;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetWeiboCommentRequest extends BaseClient {

	public static class Result {
		private int total;
		private List<CommentItem> list;
		public int getTotal() {
			return total;
		}
		public List<CommentItem> getList() {
			return list;
		}
	}

	public GetWeiboCommentRequest(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object onSuccess(String str) {
		return null;
	}

	@Override
	public void request(RequestHandler requestHandler) {
		Result result;
		try {
			result = new Gson().fromJson(
					IOUtils.toString(context.getAssets().open(
							"bank.json")),Result.class);
			requestHandler.onSuccess(result);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

}
