package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.SpecialDetailActivity.Params;
import cn.cntv.cctv11.android.utils.RelativeDateFormat;
import cn.cntv.cctv11.android.widget.BBSDetailHeaderView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class AppListAdapter extends BaseAdapter implements Serializable,PinnedSectionListAdapter {

	public enum Status{
		DOWNLOAD,OPEN
	}
	public static class Model implements Serializable {
		private String img;
		private String title;
		private String desc;
		private Status status;
		public Model(String img, String title, String desc, Status status) {
			super();
			this.img = img;
			this.title = title;
			this.desc = desc;
			this.status = status;
		}
		
	}
	public static interface OnBtnClickListener{
		public void onBtnClick(Model model);
	}

	private Context context;

	private List<Model> list;
	
	

	public AppListAdapter(Context context, List<Model> list) {
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
		final Model model = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.app_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(model.title);
		holder.desc.setText(model.desc);
		ImageLoader.getInstance().displayImage(model.img, holder.img,DisplayOptions.IMG.getOptions());
		switch (model.status) {
		case DOWNLOAD:
			holder.openBtn.setVisibility(View.GONE);
			holder.downloadBtn.setVisibility(View.VISIBLE);
			break;
		case OPEN:
			holder.openBtn.setVisibility(View.VISIBLE);
			holder.downloadBtn.setVisibility(View.GONE);
			break;
		}
		
		return convertView;
	}

	public static class ViewHolder {

		private ImageView img;

		private TextView title;
		
		private TextView desc;
		
		private View downloadBtn;
		
		private View openBtn;

		public ViewHolder(View view) {
			img = (ImageView) view.findViewById(R.id.img);
			title = (TextView) view.findViewById(R.id.title);
			desc = (TextView) view.findViewById(R.id.desc);
			downloadBtn = view.findViewById(R.id.download_btn);
			openBtn = view.findViewById(R.id.open_btn);
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
