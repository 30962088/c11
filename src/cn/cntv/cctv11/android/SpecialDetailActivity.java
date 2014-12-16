package cn.cntv.cctv11.android;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import com.mengle.lib.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.DescripitionRequest;
import cn.cntv.cctv11.android.fragment.network.DescripitionRequest.Result;
import cn.cntv.cctv11.android.fragment.network.InsertCommentRequest;
import cn.cntv.cctv11.android.utils.HtmlUtils;
import cn.cntv.cctv11.android.utils.LoadingPopup;
import cn.cntv.cctv11.android.utils.ShareUtils;
import cn.cntv.cctv11.android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
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
		
		

		private int fontSize = APP.getSession().getFontSize();

		private int fonts[] = new int[] { 10, 14, 18, 22, 26 };

		private int fontIndex = ArrayUtils.indexOf(fonts, fontSize);

		public ViewHolder(int template) {
			((ViewStub) findViewById(template)).inflate();
			findViewById(R.id.back).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					SpecialDetailActivity.this.finish();

				}
			});
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
			webView = (WebView) findViewById(R.id.webView);
			webView.setBackgroundColor(Color.TRANSPARENT);
			webView.getSettings().setAppCacheEnabled(true);
			settings = webView.getSettings();
			settings.setJavaScriptEnabled(true);

			coverView = (ImageView) findViewById(R.id.cover);
			title = (TextView) findViewById(R.id.title);
			subTitle = (TextView) findViewById(R.id.subtitle);
			comment = (TextView) findViewById(R.id.comment);
			editText = (EditText) findViewById(R.id.edit);
		}

		private String html;

		private void setModel(final Model model) {
			if (model.title != null) {
				title.setText(model.title);
			}

			if (model.subTitle != null) {
				subTitle.setText(model.subTitle);
			}

			if (model.comment != null) {
				comment.setText("" + model.comment+" 跟帖");
			}

			if (model.cover != null) {
				ImageLoader.getInstance().displayImage(model.cover, coverView,
						DisplayOptions.IMG.getOptions());
			}
			if (model.html != null) {

				try {
					html = HtmlUtils.getHtml(SpecialDetailActivity.this,
							"desc_template.html",
							new HashMap<String, String>() {
								{
									put("content", model.html);
									put("font-size", "" + fontSize);
								}
							});
					webView.loadDataWithBaseURL(null, html, "text/html",
							"utf-8", null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void setFontSize(int fontSize) {
			APP.getSession().setFontSize(fontSize);
			webView.loadUrl("javascript:setFontSize(" + fontSize + ")");
		}

		private void scaleUp() {
			if (fontIndex < fonts.length - 1) {
				fontIndex++;
			}
			setFontSize(fonts[fontIndex]);
		}

		private void scaleDown() {
			if (fontIndex > 0) {
				fontIndex--;
			}
			setFontSize(fonts[fontIndex]);
		}

	}

	private ViewHolder holder;
	
	private View notLoginView;

	private Params params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specical_detail_layout);
		findViewById(R.id.share).setOnClickListener(this);
		notLoginView = findViewById(R.id.not_login_view);
		notLoginView.setOnClickListener(this);
		if(APP.getSession().isLogin()){
			notLoginView.setVisibility(View.GONE);
		}else{
			notLoginView.setVisibility(View.VISIBLE);
		}
		findViewById(R.id.sendBtn).setOnClickListener(this);
		findViewById(R.id.comment_btn).setOnClickListener(this);
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
			public void onComplete() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share:
			onshare();
			break;
		case R.id.not_login_view:
			toLogin();
			break;
		case R.id.comment_btn:
			NewsCommentActivity.open(this, new NewsCommentActivity.Model(
					params.contentId, params.comment, params.title));
			break;
		case R.id.sendBtn:
			LoadingPopup.show(this);
			String content = holder.editText.getText().toString();
			new InsertCommentRequest(this, new InsertCommentRequest.Params(
					params.contentId, "0", "0", APP.getSession().getSid(), content,APP.getSession().getPkey()))
					.request(new RequestHandler() {

						@Override
						public void onSuccess(Object object) {
							Utils.tip(SpecialDetailActivity.this, "评论成功");
							holder.editText.setText("");

						}

						@Override
						public void onError(int error, String msg) {
							Utils.tip(SpecialDetailActivity.this, "评论失败");
							
						}

						@Override
						public void onComplete() {
							LoadingPopup.show(SpecialDetailActivity.this);

						}
					});
			break;
		default:
			break;
		}

	}

	private void onshare() {
		ShareUtils.shareText(this, params.title,BaseClient.getSharecontent(params.contentId));
		
	}

}
