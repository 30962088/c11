package cn.cntv.cctv11.android.widget;

import com.mengle.lib.wiget.ConfirmDialog;

import cn.cntv.cctv11.android.APP;
import cn.cntv.cctv11.android.BBSListActivity;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.SettingActivity;
import cn.cntv.cctv11.android.utils.CacheManager;
import cn.cntv.cctv11.android.utils.LoadingPopup;
import cn.cntv.cctv11.android.utils.CacheManager.OnClearCacheListner;
import cn.cntv.cctv11.android.utils.Preferences.Session;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BBSHeaderView extends FrameLayout{

	public BBSHeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BBSHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BBSHeaderView(Context context) {
		super(context);
		init();
	}

	private ViewHolder holder;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.bbs_header, this);
		holder = new ViewHolder();
	}
	
	private class ViewHolder implements OnClickListener{
		
		private TextView published;
		
		private TextView ipublish;
		
		public ViewHolder() {
			published = (TextView) findViewById(R.id.published);
			ipublish = (TextView) findViewById(R.id.ipublish);
			findViewById(R.id.publish_btn).setOnClickListener(this);
			findViewById(R.id.published_btn).setOnClickListener(this);
			findViewById(R.id.ipublish_btn).setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.publish_btn:
				if(check()){
					
				}
				break;
			case R.id.published_btn:
				if(check()){
					BBSListActivity.open(getContext(), BBSListActivity.TYPE_PUBLISH);
					
				}
				
				break;
			case R.id.ipublish_btn:
				if(check()){
					BBSListActivity.open(getContext(), BBSListActivity.TYPE_REPLY);
				}
				break;
			default:
				break;
			}
			
		}
		private boolean check() {
			
			if(!APP.getSession().isLogin()){
				ConfirmDialog.open(getContext(), "提示", "您还没有登录，是否立即登录？",
						new ConfirmDialog.OnClickListener() {

							@Override
							public void onPositiveClick() {
								
							}

							@Override
							public void onNegativeClick() {
								// TODO Auto-generated method stub

							}

						});
				return false;
			}
			
			return true;
		}
		
	}
	
	/*public void setModel(Model model){
		holder.ipublish.setText("("+model.ipublish+")");
		holder.published.setText("("+model.published+")");
	}
	
	public static class Model{
		private int published;
		private int ipublish;
		public Model(int published, int ipublish) {
			super();
			this.published = published;
			this.ipublish = ipublish;
		}
		
	}*/
	
	

}
