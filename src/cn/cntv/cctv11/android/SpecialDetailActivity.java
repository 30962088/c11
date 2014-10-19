package cn.cntv.cctv11.android;

import java.io.Serializable;

import org.apache.http.Header;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.DescripitionRequest;
import cn.cntv.cctv11.android.fragment.network.DescripitionRequest.Result;
import cn.cntv.cctv11.android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class SpecialDetailActivity extends BaseActivity implements
		OnClickListener {

	public static void open(Context context, Params params) {
		Intent intent = new Intent(context, SpecialDetailActivity.class);
		intent.putExtra("params", params);
		context.startActivity(intent);
	}

	public static class Params implements Serializable {
		private String contentId;
		private String title;

		private String subTitle;

		private String cover;

		private int comment;

		public Params(String contentId, String title, String subTitle,
				String cover, int comment) {
			super();
			this.contentId = contentId;
			this.title = title;
			this.subTitle = subTitle;
			this.cover = cover;
			this.comment = comment;
		}

		private Model toModel() {
			return new Model(title, subTitle, cover, comment);
		}
	}

	public static class Model implements Serializable {

		private String title;

		private String subTitle;

		private String cover;

		private String html;

		private Integer comment;

		public Model(String title, String subTitle, String cover,
				Integer comment) {
			super();
			this.title = title;
			this.subTitle = subTitle;
			this.cover = cover;
			this.comment = comment;
		}

		public Model(String html) {
			super();
			this.html = html;
		}

	}

	private class ViewHolder {
		private WebView webView;

		private ImageView coverView;

		private TextView title;

		private TextView subTitle;

		private TextView comment;

		private EditText editText;

		private WebSettings settings;

		private ScrollView scrollView;

		private final TextSize[] TEXTSIZE1 = new TextSize[] {
				TextSize.SMALLEST, TextSize.SMALLER, TextSize.NORMAL,
				TextSize.LARGER, TextSize.LARGEST };

		private final int[] TEXTSIZE2 = new int[] { 50, 75, 100, 150, 200 };

		private int scale = 3;

		public ViewHolder(int template) {
			((ViewStub) findViewById(template)).inflate();
			findViewById(R.id.back).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					SpecialDetailActivity.this.finish();

				}
			});
			findViewById(R.id.share).setOnClickListener(
					SpecialDetailActivity.this);
			findViewById(R.id.scaleDown).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							scaleDown();

						}
					});
			findViewById(R.id.scaleUp).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							scaleUp();

						}
					});
			scrollView = (ScrollView) findViewById(R.id.scrollview);
			webView = (WebView) findViewById(R.id.webView);
			webView.setBackgroundColor(Color.TRANSPARENT);
			settings = webView.getSettings();
			settings.setJavaScriptEnabled(true);
			settings.setJavaScriptCanOpenWindowsAutomatically(true);
			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public boolean onConsoleMessage(ConsoleMessage cm) {
					Log.d("MyApplication",
							cm.message() + " -- From line " + cm.lineNumber()
									+ " of " + cm.sourceId());
					return true;
				}
			});
			

			coverView = (ImageView) findViewById(R.id.cover);
			title = (TextView) findViewById(R.id.title);
			subTitle = (TextView) findViewById(R.id.subtitle);
			comment = (TextView) findViewById(R.id.comment);
			editText = (EditText) findViewById(R.id.edit);
		}

		private String html;

		private void setModel(Model model) {
			if (model.title != null) {
				title.setText(model.title);
			}

			if (model.subTitle != null) {
				subTitle.setText(model.subTitle);
			}

			if (model.comment != null) {
				comment.setText("" + model.comment);
			}

			if (model.cover != null) {
				ImageLoader.getInstance().displayImage(model.cover, coverView,
						DisplayOptions.IMG.getOptions());
			}
			StringBuilder sb = new StringBuilder();
			sb.append("<!doctype html><html><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'><style>body{background:rgba(255,255,255,0)}</style></head><body>");
			sb.append(model.html);
			sb.append("</body></html>");
			html = sb.toString();
			loadHtml();
		}

		private void loadHtml() {
			webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
		}

		private void scaleUp() {
			if (scale <= TEXTSIZE1.length - 2) {
				scale++;
				reloadWebview();
			}
		}

		@SuppressLint("NewApi")
		private void reloadWebview() {
			if (Build.VERSION.SDK_INT >= 14) {
				settings.setTextZoom(TEXTSIZE2[scale]);
			} else {
				settings.setTextSize(TEXTSIZE1[scale]);
			}
			webView.postDelayed(new Runnable() {
				@Override
				public void run() {
					webView.clearView();
					loadHtml();
					webView.requestLayout();
				}
			}, 10);
		}

		private void scaleDown() {
			if (scale >= 1) {
				scale--;
				reloadWebview();
			}
		}

	}

	private ViewHolder holder;

	private Params params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specical_detail_layout);
		params = (Params) getIntent().getSerializableExtra("params");
		int template = R.id.detail_template;
		if (params.cover != null) {
			template = R.id.sepical_detail_template;
		}
		holder = new ViewHolder(template);
		holder.setModel(params.toModel());
		request();
	}

	private void request() {
		DescripitionRequest request = new DescripitionRequest(this,
				params.contentId);
		request.request(new RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				Result result = (Result) object;
				holder.setModel(result.toDetailModel());

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

}