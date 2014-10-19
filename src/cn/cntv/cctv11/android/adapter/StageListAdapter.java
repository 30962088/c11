package cn.cntv.cctv11.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;

import cn.cntv.cctv11.android.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import android.widget.TextView;

public class StageListAdapter extends BaseAdapter implements
		PinnedSectionListAdapter{

	public static final int ITEM = 0;
	
	public static final int SECTION = 1;
	
	public static class StageItem implements Serializable {

		private String name;

		private String link;

		private String loc;

		private String time;


		public StageItem(String name, String link, String loc, String time) {
			super();
			this.name = name;
			this.link = link;
			this.loc = loc;
			this.time = time;
		}

	}

	public static class DateItem {
		private String date;

		public DateItem(String date) {
			super();
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

	private List<Object> datas;

	public StageListAdapter(Context context, List<StageGroup> list) {
		super();
		this.context = context;
		this.list = list;
		this.datas = getDatas();

	}

	private List<Object> getDatas() {
		List<Object> datas = new ArrayList<Object>();
		for (StageGroup group : list) {
			datas.add(new DateItem(group.date));
			for (StageItem item : group.list) {
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

		ViewHolder holder = null; 
		if(convertView == null){
			holder = new ViewHolder();
			View stageview = LayoutInflater.from(context).inflate(
					R.layout.stage_item, null);
			holder.stageViewHolder = new StageViewHolder(stageview);
			holder.stageViewHolder.container.setTag(holder);
			View headerview = LayoutInflater.from(context).inflate(
					R.layout.stage_header, null);
			holder.headerViewHolder = new HeaderViewHolder(headerview);
			holder.headerViewHolder.container.setTag(holder);
			if(isItemViewTypePinned(getItemViewType(position))){
				convertView = holder.headerViewHolder.container;
			}else{
				convertView = holder.stageViewHolder.container;
			}
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(isItemViewTypePinned(getItemViewType(position))){
			
			DateItem item = (DateItem) datas.get(position);
			
			holder.headerViewHolder.date.setText(item.date);
			
		}else{
			StageItem item = (StageItem) datas.get(position);
			
			holder.stageViewHolder.name.setText(item.name);

			holder.stageViewHolder.link.setText(item.link);

			holder.stageViewHolder.loc.setText(item.loc);

			holder.stageViewHolder.time.setText(item.time);

		}

		return convertView;
	}
	
	public static class ViewHolder{
		private StageViewHolder stageViewHolder;
		private HeaderViewHolder headerViewHolder;
		
	}

	public static class StageViewHolder {

		private TextView name;

		private TextView link;

		private TextView loc;

		private TextView time;
		
		private View container;

		public StageViewHolder(View view) {
			container = view;
			name = (TextView) view.findViewById(R.id.name);
			link = (TextView) view.findViewById(R.id.link);
			loc = (TextView) view.findViewById(R.id.loc);
			time = (TextView) view.findViewById(R.id.time);
		}

	}

	public static class HeaderViewHolder {
		private TextView date;
		
		private View container;

		public HeaderViewHolder(View view) {
			container = view;
			date = (TextView) view.findViewById(R.id.date);
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
		if(object instanceof DateItem){
			type = SECTION;
		}else{
			type = ITEM;
		}
		return type;
	}
}
