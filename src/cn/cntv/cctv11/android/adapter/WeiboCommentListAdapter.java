package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.WeiboCommentListAdapter.TitleItem.Current;
import cn.cntv.cctv11.android.utils.WeiboUtils;
import cn.cntv.cctv11.android.utils.WeiboUtils.OnSymbolClickLisenter;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymboResult;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymbol;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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
		
		private SpannableString spannableString;

		public CommentItem(String avatar, String name, String content,
				String time) {
			super();
			this.avatar = avatar;
			this.name = name;
			this.content = content;
			this.time = time;
		}
		
		public SpannableString getSpannableString() {
			if (spannableString == null) {
				ArrayList<WeiboSymboResult> list = WeiboUtils.build(content,
						new OnSymbolClickLisenter() {

							@Override
							public void OnSymbolClick(WeiboSymbol symbol) {
								System.out.println(symbol);

							}
						});
				this.spannableString = new SpannableString(content);
				for (WeiboSymboResult result : list) {
					this.spannableString.setSpan(result.getClickableString(),
							result.getStart(), result.getEnd(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
			return spannableString;
		}

		

	}
	
	public static interface OnTitleClickListener{
		public void onCommentClick();
		public void onShareClick();
	}

	public static class TitleItem implements Serializable{
		
		public enum Current{
			Share,Comment
		}
		
		private long share;

		private long comment;
		
		private Current current = Current.Comment;
		
		
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

	private OnTitleClickListener onTitleClickListener;
	
	private Context context;

	private List<Object> datas;

	private Model model;
	
	public WeiboCommentListAdapter(Context context, Model model,OnTitleClickListener onTitleClickListener) {
		super();
		this.onTitleClickListener = onTitleClickListener;
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
			commentItem.findViewById(R.id.comment_btn).setVisibility(View.GONE);
			holder.commentItemHolder = new CommentItemHolder(commentItem);
			holder.commentItemHolder.container.setTag(holder);
			View titleItem = LayoutInflater.from(context).inflate(
					R.layout.comment_header, null);
			
			holder.titleItemHolder = new TitleItemHolder(titleItem);
			
			holder.titleItemHolder.comment.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					model.titleItem.current = Current.Comment;
					
					onTitleClickListener.onCommentClick();
					
				}
			});
			holder.titleItemHolder.share.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					model.titleItem.current = Current.Share;
					
					onTitleClickListener.onShareClick();
					
				}
			});
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
			final TitleItem item = (TitleItem) datas.get(position);
			final ViewHolder h = holder;
			holder.titleItemHolder.container.post(new Runnable() {
				
				@Override
				public void run() {
					int x,comment_x,share_x = 0;
					RelativeLayout.LayoutParams comment_params = (LayoutParams) h.titleItemHolder.comment.getLayoutParams();
					RelativeLayout.LayoutParams share_params = (LayoutParams) h.titleItemHolder.share.getLayoutParams();
					comment_x = share_params.leftMargin+h.titleItemHolder.share.getMeasuredWidth() + 
							comment_params.leftMargin+h.titleItemHolder.comment.getMeasuredWidth()/2;
					share_x = share_params.leftMargin + h.titleItemHolder.share.getMeasuredWidth()/2;
					
					
					if(item.current == Current.Comment){
						x = comment_x;
						h.titleItemHolder.comment.setSelected(true);
						h.titleItemHolder.share.setSelected(false);
					}else{
						x = share_x;
						h.titleItemHolder.comment.setSelected(false);
						h.titleItemHolder.share.setSelected(true);
					}
					RelativeLayout.LayoutParams params = (LayoutParams) h.titleItemHolder.tag.getLayoutParams();
					params.leftMargin = x;
					/*int fx = params.leftMargin+params.width/2;
					int fy = params.topMargin;
					
					Animation animation = new TranslateAnimation(fx, x, fy, fy);
					
					animation.setDuration(500);
					animation.setFillBefore(false);
					animation.setFillAfter(true);*/

//					holder.titleItemHolder.tag.startAnimation(animation);
					h.titleItemHolder.tag.setLayoutParams(params);
				}
			});
			
			
			holder.titleItemHolder.comment.setText("评论"+item.comment);
			
			holder.titleItemHolder.share.setText("转发"+item.share);
			
			

		} else {
			CommentItem item = (CommentItem) datas.get(position);

			holder.commentItemHolder.name.setText(item.name);

			holder.commentItemHolder.content.setText(item.getSpannableString());

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

	public static class TitleItemHolder{
		private TextView share;
		private TextView comment;
		private View container;
		private View tag;

		public TitleItemHolder(View view) {
			container = view;
			share = (TextView) view.findViewById(R.id.share);
			comment = (TextView) view.findViewById(R.id.comment);
			tag = view.findViewById(R.id.tag);
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
