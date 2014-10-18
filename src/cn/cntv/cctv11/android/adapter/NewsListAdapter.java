package cn.cntv.cctv11.android.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.R;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class NewsListAdapter extends BaseAdapter {

	public static class Model {

		public static class Category {
			public enum Background{
				GREEN(R.drawable.rect_green),PURPLE(R.drawable.rect_purple),RED(R.drawable.rect_red);
				private int background;
				private Background(int background) {
					this.background = background;
				}
				
			}
			private Background background;
			private String text;
			public Category(Background background, String text) {
				super();
				this.background = background;
				this.text = text;
			}
			
			
		}

		private String img;

		private String title;

		private int comment;

		private boolean isNew;
		
		private Category category;

		public Model(String img, String title, int comment, boolean isNew,
				Category category) {
			super();
			this.img = img;
			this.title = title;
			this.comment = comment;
			this.isNew = isNew;
			this.category = category;
		}

		
	}

	private Context context;

	private List<Model> list;

	public NewsListAdapter(Context context, List<Model> list) {
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
					R.layout.news_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ImageLoader.getInstance().displayImage(model.img, holder.img,DisplayOptions.IMG.getOptions());

		holder.title.setText(model.title);

		holder.comment.setText("" + model.comment);

		holder.isNew.setVisibility(model.isNew ? View.VISIBLE : View.GONE);
		
		if(model.category != null){
			holder.category.setVisibility(View.VISIBLE);
			holder.category.setText(model.category.text);
			holder.category.setBackgroundResource(model.category.background.background);
		}else{
			holder.category.setVisibility(View.GONE);
		}

		return convertView;
	}

	private static class ViewHolder {

		private ImageView img;

		private TextView title;

		private TextView comment;

		private View isNew;
		
		private TextView category;

		public ViewHolder(View view) {
			img = (ImageView) view.findViewById(R.id.img);
			title = (TextView) view.findViewById(R.id.title);
			comment = (TextView) view.findViewById(R.id.comment);
			isNew = view.findViewById(R.id.isNew);
			category = (TextView) view.findViewById(R.id.category);

		}

	}

}
