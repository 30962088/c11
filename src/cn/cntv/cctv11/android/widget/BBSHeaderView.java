package cn.cntv.cctv11.android.widget;

import cn.cntv.cctv11.android.R;
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
				
				break;
			case R.id.published_btn:
				break;
			case R.id.ipublish_btn:
				break;
			default:
				break;
			}
			
		}
		
	}
	
	public void setModel(Model model){
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
		
	}
	
	

}
