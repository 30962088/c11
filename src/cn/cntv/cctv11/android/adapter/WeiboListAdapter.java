package cn.cntv.cctv11.android.adapter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.PhotoViewActivity;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.WeiboDetailActivity;
import cn.cntv.cctv11.android.SpecialDetailActivity.Params;
import cn.cntv.cctv11.android.utils.WeiboUtils;
import cn.cntv.cctv11.android.utils.WeiboUtils.OnSymbolClickLisenter;
import cn.cntv.cctv11.android.utils.WeiboUtils.Synbol;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymboResult;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboSymbol;
import cn.cntv.cctv11.android.utils.WeiboUtils.WeiboText;
import cn.cntv.cctv11.android.widget.WeiboItemView;
import cn.cntv.cctv11.android.widget.WeiboItemView.Model;
import cn.cntv.cctv11.android.widget.WeiboItemView.ViewHolder;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;

public class WeiboListAdapter extends BaseAdapter implements Serializable,PinnedSectionListAdapter {

	

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
		final Model model = list.get(position);
		if (convertView == null) {
			convertView = new WeiboItemView(context);
		}
		
		WeiboItemView itemView = (WeiboItemView) convertView;
		itemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WeiboDetailActivity.open(context, model);
				
			}
		});
		itemView.setModel(model);


		return convertView;
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
