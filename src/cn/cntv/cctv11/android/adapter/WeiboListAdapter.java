package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.SpecialDetailActivity.Params;
import cn.cntv.cctv11.android.utils.WeiboUtils;
import cn.cntv.cctv11.android.utils.WeiboUtils.OnSymbolClickLisenter;
import cn.cntv.cctv11.android.utils.WeiboUtils.Synbol;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymboResult;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymbol;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboText;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;

public class WeiboListAdapter extends BaseAdapter implements Serializable {

	public static class Photo {
		private String url;

		public Photo(String url) {
			super();
			this.url = url;
		}

		public WeiboImgAdapter.Model toModel() {
			return new WeiboImgAdapter.Model(url);
		}
	}

	public static class Content {
		private Spannable text;
		private List<Photo> photos;


		public Content(String text, List<Photo> photos) {
			super();
			List<WeiboSymboResult> list = WeiboUtils.build(text, new OnSymbolClickLisenter() {
				
				@Override
				public void OnSymbolClick(WeiboSymbol symbol) {
					System.out.println(symbol);
					
				}
			});
			this.text = new SpannableString(text);        
			for(WeiboSymboResult result : list){
				this.text.setSpan(result.getClickableString(), result.getStart(), result.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			this.photos = photos;
		}

		public List<WeiboImgAdapter.Model> toModel() {
			List<WeiboImgAdapter.Model> list = new ArrayList<WeiboImgAdapter.Model>();
			for (Photo photo : photos) {
				list.add(photo.toModel());
			}
			return list;
		}
	}

	public static class Model implements Serializable {

		private String avatar;
		private String name;
		private String time;
		private Content content;
		private Content retweetedContent;

		public Model(String avatar, String name, String time, Content content) {
			super();
			this.avatar = avatar;
			this.name = name;
			this.time = time;
			this.content = content;
		}

		public Model(String avatar, String name, String time, Content content,
				Content retweetedContent) {
			super();
			this.avatar = avatar;
			this.name = name;
			this.time = time;
			this.content = content;
			this.retweetedContent = retweetedContent;
		}

	}

	private Context context;

	private List<Model> list;

	public WeiboListAdapter(Context context, List<Model> list) {
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
					R.layout.weibo_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(model.name);

		holder.time.setText(model.time);

		ImageLoader.getInstance().displayImage(model.avatar, holder.avatar,
				DisplayOptions.IMG.getOptions());

		holder.text.setText(model.content.text);

		holder.retweetedPhotogrid.setVisibility(View.GONE);

		holder.photogrid.setVisibility(View.GONE);

		if (model.retweetedContent != null) {
			holder.photogrid.setVisibility(View.GONE);
			holder.retweeted.setVisibility(View.VISIBLE);
			holder.retweetedText.setText(model.retweetedContent.text);
			holder.retweetedText.setText(model.retweetedContent.text);
			if (model.retweetedContent.photos != null) {
				holder.retweetedPhotogrid.setVisibility(View.VISIBLE);
				holder.retweetedPhotogrid.setAdapter(new WeiboImgAdapter(
						context, model.retweetedContent.toModel()));
			}

		} else {
			holder.photogrid.setVisibility(View.VISIBLE);
			holder.retweeted.setVisibility(View.GONE);
			if (model.content.photos != null) {
				holder.photogrid.setAdapter(new WeiboImgAdapter(context,
						model.content.toModel()));
			}
		}

		return convertView;
	}

	public static class ViewHolder {

		private TextView time;

		private TextView name;

		private ImageView avatar;

		private TextView text;

		private GridView photogrid;

		private View retweeted;

		private TextView retweetedText;

		private GridView retweetedPhotogrid;

		public ViewHolder(View view) {
			time = (TextView) view.findViewById(R.id.time);
			name = (TextView) view.findViewById(R.id.name);
			avatar = (ImageView) view.findViewById(R.id.avatar);
			text = (TextView) view.findViewById(R.id.text);
			text.setMovementMethod(LinkMovementMethod.getInstance());
			photogrid = (GridView) view.findViewById(R.id.photogrid);
			retweeted = view.findViewById(R.id.retweeted);
			retweetedText = (TextView) view.findViewById(R.id.retweetedText);
			retweetedText.setMovementMethod(LinkMovementMethod.getInstance());
			retweetedPhotogrid = (GridView) view
					.findViewById(R.id.retweetedPhotogrid);
			
			
		}

	}


	
}
