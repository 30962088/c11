package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;

import android.widget.TextView;

public class WeiboCommentListAdapter extends BaseAdapter implements
		PinnedSectionListAdapter {

	public static final int ITEM = 0;

	public static final int SECTION = 1;

	public static class CommentItem implements Serializable {

		private String avatar;

		private String name;

		private String content;

		private String time;

		public CommentItem(String avatar, String name, String content,
				String time) {
			super();
			this.avatar = avatar;
			this.name = name;
			this.content = content;
			this.time = time;
		}

		

	}

	public static class TitleItem implements Serializable{
		
		private long share;

		private long comment;

		
		public void setShare(long share) {
			this.share = share;
		}
		public void setComment(long comment) {
			this.comment = comment;
		}

	}

	public static class Model implements Serializable{
		private TitleItem titleItem;
		private List<CommentItem> list;

		public Model(TitleItem titleItem, List<CommentItem> list) {
			super();
			this.titleItem = titleItem;
			this.list = list;
		}

		private List<Object> toDatas() {
			List<Object> datas = new ArrayList<Object>();
			datas.add(titleItem);
			datas.addAll(list);
			return datas;
		}

	}

	private Context context;

	private List<Object> datas;

	private Model model;
	
	public WeiboCommentListAdapter(Context context, Model model) {
		super();
		this.context = context;
		this.model = model;
		this.datas = model.toDatas();
	}
	
	@Override
	public void notifyDataSetChanged() {
		this.datas = model.toDatas();
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return datas.size();
	}

	@Override
	public Object getItem(int position) {

		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			View commentItem = LayoutInflater.from(context).inflate(
					R.layout.comment_item, null);
			holder.commentItemHolder = new CommentItemHolder(commentItem);
			holder.commentItemHolder.container.setTag(holder);
			View titleItem = LayoutInflater.from(context).inflate(
					R.layout.comment_header, null);
			holder.titleItemHolder = new TitleItemHolder(titleItem);
			holder.titleItemHolder.container.setTag(holder);
			if (isItemViewTypePinned(getItemViewType(position))) {
				convertView = holder.titleItemHolder.container;
			} else {
				convertView = holder.commentItemHolder.container;
			}

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (isItemViewTypePinned(getItemViewType(position))) {

			TitleItem item = (TitleItem) datas.get(position);

			holder.titleItemHolder.comment.setText("评论"+item.comment);
			
			holder.titleItemHolder.share.setText("转发"+item.share);

		} else {
			CommentItem item = (CommentItem) datas.get(position);

			holder.commentItemHolder.name.setText(item.name);

			holder.commentItemHolder.content.setText(item.content);

			holder.commentItemHolder.time.setText(item.time);
			
			ImageLoader.getInstance().displayImage(item.avatar,
					holder.commentItemHolder.avatar,
					DisplayOptions.IMG.getOptions());

		}

		return convertView;
	}

	public static class ViewHolder {
		private CommentItemHolder commentItemHolder;
		private TitleItemHolder titleItemHolder;

	}

	public static class CommentItemHolder {

		private ImageView avatar;

		private TextView name;

		private TextView content;

		private TextView time;

		private View container;

		public CommentItemHolder(View view) {
			container = view;
			name = (TextView) view.findViewById(R.id.name);
			avatar = (ImageView) view.findViewById(R.id.avatar);
			content = (TextView) view.findViewById(R.id.content);
			time = (TextView) view.findViewById(R.id.time);

		}

	}

	public static class TitleItemHolder {
		private TextView share;
		private TextView comment;
		private View container;

		public TitleItemHolder(View view) {
			container = view;
			share = (TextView) view.findViewById(R.id.share);
			comment = (TextView) view.findViewById(R.id.comment);
		}

	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType == SECTION;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		Object object = datas.get(position);
		int type = 0;
		if (object instanceof TitleItem) {
			type = SECTION;
		} else {
			type = ITEM;
		}
		return type;
	}
}
