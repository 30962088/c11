package cn.cntv.cctv11.android.widget;

import org.apache.http.Header;

import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;

import com.cheshang8.library.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class BaseListView extends PullToRefreshListView {
	public static interface OnLoadListener {
		public BaseClient onLoad(int offset, int limit);

		public boolean onLoadSuccess(Object object, int offset, int limit);

		public void onError(String error);

		public Type getRequestType();
	}

	public enum Type {
		PAGE, OFFSET
	}

	public static class RequestResult {
		private Type type;
		private BaseClient baseClient;

		public RequestResult(Type type, BaseClient baseClient) {
			super();
			this.type = type;
			this.baseClient = baseClient;
		}

	}

	private int offset = 0;

	private OnLoadListener onLoadListener;

	private View mFooterLoading;

	protected boolean hasMode = false;

	private int limit = 10;

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOnLoadListener(OnLoadListener onLoadListener) {
		this.onLoadListener = onLoadListener;
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseListView(
			Context context,
			com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
			com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle style) {
		super(context, mode, style);
		init();
	}

	public BaseListView(Context context,
			com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode) {
		super(context, mode);
		init();
	}

	public BaseListView(Context context) {
		super(context);
		init();
	}

	private void init() {
		View footer = inflate(getContext(), R.layout.footer_loading, null);
		mFooterLoading = footer.findViewById(R.id.layout_checkmore);
		mFooterLoading.setVisibility(View.GONE);
		getRefreshableView().addFooterView(footer);
		setOnRefreshListener(new OnRefreshListener<ListView>() {

			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				load(true);

			}
		});

		setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			public void onLastItemVisible() {
				if (hasMode) {
					load(false);
				}

			}
		});
	}

	public void load(boolean refresh) {
		final Type type = onLoadListener.getRequestType();
		hasMode = false;
		if (refresh) {
			setRefreshing(true);
			if (type == Type.PAGE) {
				offset = 1;
			} else {
				offset = 0;
			}

		}
		if (onLoadListener != null) {
			BaseClient baseClient = onLoadListener.onLoad(offset, limit);
			baseClient.request(new RequestHandler() {

				@Override
				public void onSuccess(Object object) {
					boolean result = onLoadListener.onLoadSuccess(object,
							offset, limit);
					hasMode = result;
					if (!result) {
						mFooterLoading.setVisibility(View.GONE);
					} else {
						mFooterLoading.setVisibility(View.VISIBLE);
					}
					BaseListView.this.onRefreshComplete();
					if (type == Type.OFFSET) {
						offset += limit;
					} else {
						offset++;
					}
					String label = DateUtils.formatDateTime(getContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					getLoadingLayoutProxy()
							.setLastUpdatedLabel("最近更新:" + label);

				}

				@Override
				public void onServerError(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(int error) {
					// TODO Auto-generated method stub

				}
			});

		}
	}

}
