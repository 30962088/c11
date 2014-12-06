package cn.cntv.cctv11.android.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mengle.lib.utils.Utils;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.utils.CalendarUtils.CalendarDate;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class CalendarListAdapter extends BaseAdapter{

	
	public static class Model{
		private Date date;
		private List<CalendarDate> list;
		public Model(Date date, List<CalendarDate> list) {
			super();
			this.date = date;
			this.list = list;
		}
		public String getDateString(){
			return new SimpleDateFormat("yyyy年MM月").format(date);
		}
	}
	
	private Context context;
	
	
	private List<Model> list;
	

	public CalendarListAdapter(Context context, List<Model> list) {
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
					R.layout.calendar_layout, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText(model.getDateString());
		
		holder.gridview.setAdapter(new CalendarGridAdapter(context, model.list));
		
//		Utils.setGridViewHeightBasedOnChildren(holder.gridview);
		
		return convertView;
	}
	
	public static class ViewHolder {

		private TextView title;
		
		private GridView gridview;

		public ViewHolder(View view) {
			title = (TextView) view.findViewById(R.id.title);
			gridview = (GridView) view.findViewById(R.id.gridview);
		}

	}

	
	
}
