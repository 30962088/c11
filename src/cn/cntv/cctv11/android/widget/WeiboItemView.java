package cn.cntv.cctv11.android.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.PhotoViewActivity;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.adapter.WeiboImgAdapter;
import cn.cntv.cctv11.android.utils.WeiboUtils;
import cn.cntv.cctv11.android.utils.WeiboUtils.OnSymbolClickLisenter;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymboResult;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymbol;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WeiboItemView extends FrameLayout {

	public WeiboItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public WeiboItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WeiboItemView(Context context) {
		super(context);
		init();
	}

	
	public static class Count implements Serializable{
		private int share;
		private int comment;
		public Count(int share, int comment) {
			super();
			this.share = share;
			this.comment = comment;
		}
		public int getShare() {
			return share;
		}
		public int getComment() {
			return comment;
		}
	}
	
	public static class Model implements Serializable {
		private String id;
		private String avatar;
		private String name;
		private String time;
		private Content content;
		private Content retweetedContent;
		public Model(String id,String avatar, String name, String time, Content content,
				Content retweetedContent) {
			super();
			this.id = id;
			this.avatar = avatar;
			this.name = name;
			this.time = time;
			this.content = content;
			this.retweetedContent = retweetedContent;
		}
		
		public Content getContent() {
			return content;
		}
		public String getId() {
			return id;
		}

		

	}

	private ViewHolder holder;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.weibo_item, this);
		holder = new ViewHolder();

	}

	public void setModel(final Model model) {
		holder.name.setText(model.name);

		holder.time.setText(model.time);

		ImageLoader.getInstance().displayImage(model.avatar, holder.avatar,
				DisplayOptions.IMG.getOptions());

		holder.text.setText(model.content.getSpannableString());

		holder.retweetedPhotogrid.setVisibility(View.GONE);

		holder.photogrid.setVisibility(View.GONE);

		if (model.retweetedContent != null) {
			holder.photogrid.setVisibility(View.GONE);
			holder.retweeted.setVisibility(View.VISIBLE);
			holder.retweetedText.setText(model.retweetedContent.getSpannableString());
//			holder.retweetedText.setText(model.retweetedContent.text);
			holder.retweetedCount.setText("转发（"+model.retweetedContent.count.share+"）| 评论（"+model.retweetedContent.count.comment+"）");
			if (model.retweetedContent.photos != null) {
				holder.retweetedPhotogrid.setVisibility(View.VISIBLE);
				holder.retweetedPhotogrid.setAdapter(new WeiboImgAdapter(
						getContext(), model.retweetedContent.toModel()));
				holder.retweetedPhotogrid
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								onImageClick(model.retweetedContent.getPhotos());

							}

						});
			}

		} else {
			holder.photogrid.setVisibility(View.VISIBLE);
			holder.retweeted.setVisibility(View.GONE);
			if (model.content.photos != null) {
				holder.photogrid
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								onImageClick(model.content.getPhotos());

							}

						});
				holder.photogrid.setAdapter(new WeiboImgAdapter(getContext(),
						model.content.toModel()));
			}
		}
	}

	public class ViewHolder {

		private TextView time;

		private TextView name;

		private ImageView avatar;

		private TextView text;

		private GridView photogrid;

		private View retweeted;

		private TextView retweetedText;

		private GridView retweetedPhotogrid;
		
		private TextView retweetedCount;

		public ViewHolder() {
			time = (TextView) findViewById(R.id.time);
			name = (TextView) findViewById(R.id.name);
			avatar = (ImageView) findViewById(R.id.avatar);
			text = (TextView) findViewById(R.id.text);
			text.setMovementMethod(LinkMovementMethod.getInstance());
			photogrid = (GridView) findViewById(R.id.photogrid);
			retweeted = findViewById(R.id.retweeted);
			retweetedText = (TextView) findViewById(R.id.retweetedText);
			retweetedText.setMovementMethod(LinkMovementMethod.getInstance());
			retweetedPhotogrid = (GridView) findViewById(R.id.retweetedPhotogrid);
			retweetedCount = (TextView) findViewById(R.id.retweetedCount);
		}

	}

	public static class Photo implements Serializable {
		private String url;
		private String real;

		public Photo(String url, String real) {
			super();
			this.url = url;
			this.real = real;
		}

		public WeiboImgAdapter.Model toModel() {
			return new WeiboImgAdapter.Model(url);
		}
	}


	public static class Content implements Serializable {
		private String text;
		private ArrayList<Photo> photos;
		private Count count;
		private transient SpannableString spannableString;
		public Count getCount() {
			return count;
		}
		public List<Photo> getPhotos() {

			return photos;
		}

		public Content(String text, ArrayList<Photo> photos,Count count) {
			super();
			this.text = text;
			this.photos = photos;
			this.count = count;
		}

		public SpannableString getSpannableString() {
			if (spannableString == null) {
				ArrayList<WeiboSymboResult> list = WeiboUtils.build(text,
						new OnSymbolClickLisenter() {

							@Override
							public void OnSymbolClick(WeiboSymbol symbol) {
								System.out.println(symbol);

							}
						});
				this.spannableString = new SpannableString(text);
				for (WeiboSymboResult result : list) {
					this.spannableString.setSpan(result.getClickableString(),
							result.getStart(), result.getEnd(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
			return spannableString;
		}

		public List<WeiboImgAdapter.Model> toModel() {
			List<WeiboImgAdapter.Model> list = new ArrayList<WeiboImgAdapter.Model>();
			for (Photo photo : photos) {
				list.add(photo.toModel());
			}
			return list;
		}
	}

	private void onImageClick(List<Photo> imgs) {
		ArrayList<PhotoViewActivity.Photo> list = new ArrayList<PhotoViewActivity.Photo>();

		for (Photo photo : imgs) {

			list.add(new PhotoViewActivity.Photo(photo.url, photo.real));
		}
		PhotoViewActivity.open(getContext(), list);
	}

}
