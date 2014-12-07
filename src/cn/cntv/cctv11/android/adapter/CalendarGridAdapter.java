package cn.cntv.cctv11.android.adapter;

import java.util.Date;
import java.util.List;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.utils.CalendarUtils.CalendarDate;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarGridAdapter extends BaseAdapter{


	
	private Context context;
	
	private List<CalendarDate> list;
	

	

	public CalendarGridAdapter(Context context, List<CalendarDate> list) {
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
		final CalendarDate model = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.day_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.day.setText(""+model.getDay());
		holder.day.setEnabled(model.isEnable());
		
		
		if(model.getTotal() > 0){
			holder.popup.setVisibility(View.VISIBLE);
			holder.popup.setText(""+model.getTotal());
		}else{
			holder.popup.setVisibility(View.GONE);
		}
		
		holder.selected.setVisibility(model.isSelected()?View.VISIBLE:View.GONE);
		
		return convertView;
	}
	
	public static class ViewHolder {

		private TextView day;

		private TextView popup;
		
		private View selected;

		public ViewHolder(View view) {
			day = (TextView) view.findViewById(R.id.day);
			popup = (TextView) view.findViewById(R.id.popup);
			selected =  view.findViewById(R.id.selected);
		}

	}

	
	
}
