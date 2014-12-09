package cn.cntv.cctv11.android.widget;

import java.io.Serializable;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.APP.DisplayOptions;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BBSDetailHeaderView extends FrameLayout{

	public BBSDetailHeaderView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public BBSDetailHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BBSDetailHeaderView(Context context) {
		super(context);
		init();
	}
	
	private TextView titleView;
	
	private ImageView avatarView;
	
	private TextView nameView;
	
	private TextView timeView;
	
	private TextView contentView;
	
	private ImageView imgView;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.bbs_detail_header, this);
		titleView = (TextView) findViewById(R.id.title);
		avatarView = (ImageView) findViewById(R.id.avatar);
		nameView = (TextView) findViewById(R.id.name);
		imgView = (ImageView) findViewById(R.id.img);
		timeView = (TextView) findViewById(R.id.time);
		contentView = (TextView) findViewById(R.id.content);
	}
	
	public void setModel(Model model){
		titleView.setText(model.title);
		ImageLoader.getInstance().displayImage(model.avatar, avatarView,DisplayOptions.IMG.getOptions());
		nameView.setText(model.name);
		timeView.setText(model.time);
		contentView.setText(model.content);
		if(model.img != null){
			ImageLoader.getInstance().displayImage(model.img, imgView,DisplayOptions.IMG.getOptions());
		}
	}
	
	public static class Model implements Serializable{
		private String id;
		private String title;
		private String avatar;
		private String name;
		private String time;
		private String content;
		private String userid;
		private String commentid;
		private String img;
		
		

		
		public Model(String id, String title, String avatar, String name,
				String time, String content, String userid, String commentid,
				String img) {
			super();
			this.id = id;
			this.title = title;
			this.avatar = avatar;
			this.name = name;
			this.time = time;
			this.content = content;
			this.userid = userid;
			this.commentid = commentid;
			this.img = img;
		}
		public String getId() {
			return id;
		}
		public String getUserid() {
			return userid;
		}
		public String getCommentid() {
			return commentid;
		}
	}
	

}
