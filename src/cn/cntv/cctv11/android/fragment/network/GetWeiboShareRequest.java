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

public class GetWeiboShareRequest extends BaseClient {

	public static class Result {
		private int total;
		private List<CommentItem> list;
	}

	public GetWeiboShareRequest(Context context) {
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
							"datas/bank.json")),Result.class);
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

	@Override
	public void onError(int error, String msg) {
		// TODO Auto-generated method stub
		
	}

}
