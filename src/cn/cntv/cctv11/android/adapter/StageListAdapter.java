package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.cntv.cctv11.android.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import android.widget.TextView;

public class StageListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

	public static class StageItem implements Serializable {

		private String name;

		private String link;

		private String loc;

		private String time;
		
		private String date;

		public StageItem(String name, String link, String loc, String time) {
			super();
			this.name = name;
			this.link = link;
			this.loc = loc;
			this.time = time;
		}
		public void setDate(String date) {
			this.date = date;
		}

	}

	public static class StageGroup {
		private String date;
		private List<StageItem> list;

		public StageGroup(String date, List<StageItem> list) {
			super();
			this.date = date;
			this.list = list;
		}

	}

	private Context context;

	private List<StageGroup> list;

	private List<StageItem> datas;


	public StageListAdapter(Context context, List<StageGroup> list) {
		super();
		this.context = context;
		this.list = list;
		this.datas = getDatas();

	}
	
	

	private List<StageItem> getDatas() {
		List<StageItem> datas = new ArrayList<StageListAdapter.StageItem>();
		for (StageGroup group : list) {
			for (StageItem item : group.list) {
				item.setDate(group.date);
				datas.add(item);
			}
		}
		return datas;
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

		StageViewHolder holder = null;
		StageItem model = datas.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.stage_item, null);
			holder = new StageViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (StageViewHolder) convertView.getTag();
		}

		holder.name.setText(model.name);

		holder.link.setText(model.link);

		holder.loc.setText(model.loc);

		holder.time.setText(model.time);

		return convertView;
	}

	public static class StageViewHolder {

		private TextView name;

		private TextView link;

		private TextView loc;

		private TextView time;

		public StageViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.name);
			link = (TextView) view.findViewById(R.id.link);
			loc = (TextView) view.findViewById(R.id.loc);
			time = (TextView) view.findViewById(R.id.time);
		}

	}

	public static class HeaderViewHolder {
		private TextView date;

		public HeaderViewHolder(View view) {
			date = (TextView) view.findViewById(R.id.date);
		}

	}


	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null;
		String section = datas.get(position).date;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.stage_header, null);
			holder = new HeaderViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		
		holder.date.setText(section);
		
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
