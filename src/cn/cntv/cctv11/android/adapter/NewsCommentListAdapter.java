package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.adapter.BBSListAdapter.ViewHolder;
import cn.cntv.cctv11.android.R;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;

import android.widget.TextView;

public class NewsCommentListAdapter extends BaseAdapter implements PinnedSectionListAdapter {


	public static class Model implements Serializable {

		private String id;
		
		private String avatar;

		private String name;

		private String content;

		private String time;
		
		private String userid;

		public Model(String id,String avatar, String name, String content, String time,
				String replynickname,String userid) {
			super();
			this.id = id;
			this.avatar = avatar;
			this.name = name;
			this.content = content;
			this.time = time;
			this.userid = userid;
			if(!TextUtils.isEmpty(replynickname)){
				this.content = "回复<font color='#0b92c3'>"+replynickname+"</font>"+" "+this.content;
			}
			
		}
		public String getId() {
			return id;
		}
		
		public String getUserid() {
			return userid;
		}

		

		

	}

	

	public static interface OnCommentBtnClickListener{
		public void onCommentBtnClick(Model model);
	}

	private Context context;

	private List<Model> list;
	
	private OnCommentBtnClickListener onCommentBtnClickListener;

	
	public NewsCommentListAdapter(Context context, List<Model> list,OnCommentBtnClickListener onCommentBtnClickListener) {
		super();
		this.context = context;
		this.list = list;
		this.onCommentBtnClickListener = onCommentBtnClickListener;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Model model = list.get(position);
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.new_comment_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.content.setText(Html.fromHtml(model.content));
		
		holder.name.setText(model.name);
		
		holder.time.setText(model.time);
		
		holder.commentBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onCommentBtnClickListener.onCommentBtnClick(model);
				
			}
		});
		
		ImageLoader.getInstance().displayImage(model.avatar, holder.avatar,DisplayOptions.IMG.getOptions());
		

		return convertView;
	}

	

	public static class ViewHolder {

		private ImageView avatar;

		private TextView name;

		private TextView content;

		private TextView time;
		
		private View commentBtn;

		public ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.name);
			avatar = (ImageView) view.findViewById(R.id.avatar);
			content = (TextView) view.findViewById(R.id.content);
			time = (TextView) view.findViewById(R.id.time);
			commentBtn = view.findViewById(R.id.comment_btn);

		}

	}

	
	

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return false;
	}

}
