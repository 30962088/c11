package com.cctv.xiqu.android.adapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cctv.xiqu.android.APP.DisplayOptions;
import com.cctv.xiqu.android.SpecialDetailActivity.Params;
import com.cctv.xiqu.android.utils.RelativeDateFormat;
import com.cctv.xiqu.android.widget.BBSDetailHeaderView;
import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.cctv.xiqu.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class BBSListAdapter extends BaseAdapter implements Serializable,PinnedSectionListAdapter {

	public static class Model implements Serializable {
		private String id;
		private String title;
		private String content;
		private String nickname;
		private String avatar;
		private String time;
		private int comment;
		private String userid;
		private String commentid;
		private String img;
		private boolean top;
		public Model(String id, String title, String content,String avatar, String nickname,
				Date time, int comment,String userid,String commentid,String img,boolean top) {
			super();
			this.id = id;
			this.title = title;
			this.avatar = avatar;
			this.content = content;
			this.nickname = nickname;
			this.time = RelativeDateFormat.format(time);;
			this.comment = comment;
			this.userid= userid;
			this.commentid = commentid;
			this.img = img;
			this.top = top;
		}
		public BBSDetailHeaderView.Model toModel(){
			return new BBSDetailHeaderView.Model(id, title, avatar, nickname, time, content,userid,commentid,img);
		}
		

	}

	private Context context;

	private List<Model> list;

	public BBSListAdapter(Context context, List<Model> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Model model = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.bbs_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.top.setVisibility(model.top?View.VISIBLE:View.GONE);


		holder.time.setText(model.time);

		holder.title.setText(model.title);
		
		holder.nickname.setText(model.nickname);
		
		holder.comment.setText(model.comment+"评论");
		
		return convertView;
	}

	public static class ViewHolder {

		private TextView title;

		private TextView nickname;
		
		private TextView time;
		
		private TextView comment;
		
		private View top;

		public ViewHolder(View view) {
			time = (TextView) view.findViewById(R.id.time);
			title = (TextView) view.findViewById(R.id.title);
			comment =  (TextView) view.findViewById(R.id.comment);
			nickname =  (TextView) view.findViewById(R.id.nickname);
			top = view.findViewById(R.id.top);
		}

	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}