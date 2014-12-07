package cn.cntv.cctv11.android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.cntv.cctv11.android.adapter.NewsListAdapter;
import cn.cntv.cctv11.android.fragment.network.BaseClient.SimpleRequestHandler;
import cn.cntv.cctv11.android.fragment.network.CategoryRequest.Category;
import cn.cntv.cctv11.android.fragment.network.ContentsRequest;
import cn.cntv.cctv11.android.fragment.network.ContentsRequest.News;
import cn.cntv.cctv11.android.fragment.network.SearchRequest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends BaseActivity implements OnClickListener,
		TextWatcher, OnScrollListener, OnEditorActionListener {

	public static <T> ArrayList<T> copyList(List<T> source) {
		ArrayList<T> dest = new ArrayList<T>();
	    for (T item : source) { dest.add(item); }
	    return dest;
	}
	
	public static class Model implements Serializable {
		private ArrayList<Category> categories;

		public Model(ArrayList<Category> categories) {
			super();
			categories = copyList(categories);
			categories.add(0, new Category(0, "全部"));
			this.categories = categories;
		}

	}

	public static void open(Context context, Model model) {

		Intent intent = new Intent(context, SearchActivity.class);

		intent.putExtra("model", model);

		context.startActivity(intent);

	}

	private Model model;

	private ViewGroup tabs;

	private Category category;

	private EditText editText;

	private View clearView;

	private ListView listView;

	private View mFooterLoading;

	private View lastView;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		model = (Model) getIntent().getSerializableExtra("model");
		category = model.categories.get(0);
		setContentView(R.layout.search_layout);
		findViewById(R.id.back).setOnClickListener(this);
		adapter = new NewsListAdapter(this, list);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		
		View footer = LayoutInflater.from(this).inflate(
				R.layout.footer_loading, null);
		listView.addFooterView(footer);
		mFooterLoading = footer.findViewById(R.id.layout_checkmore);
		mFooterLoading.setVisibility(View.GONE);
		tabs = (ViewGroup) findViewById(R.id.tabs);
		editText = (EditText) findViewById(R.id.edit);
		editText.setOnEditorActionListener(this);
		editText.addTextChangedListener(this);
		clearView = findViewById(R.id.clear);
		clearView.setOnClickListener(this);
		initTabs();
		listView.setOnScrollListener(this);
	}

	private void initTabs() {
		LayoutInflater inflater = LayoutInflater.from(this);
		int i = 0;
		for (Category category : model.categories) {
			TextView tab = (TextView) inflater.inflate(
					R.layout.search_tab_template, null);
			tab.setText(category.getCategoryname());
			tab.setTag(category);
			tab.setOnClickListener(this);
			if (i == 0) {
				tab.performClick();
			}
			tabs.addView(tab);
			i++;
		}

	}

	private int pageno = 1;

	private int pagesize = 30;

	private List<NewsListAdapter.Model> list = new ArrayList<NewsListAdapter.Model>();

	private NewsListAdapter adapter;

	private void request() {
		String content = editText.getText().toString();
		if (TextUtils.isEmpty(content)) {
			return;
		}
		if(pageno == 1){
			list.clear();
			adapter.notifyDataSetChanged();
		}
		mFooterLoading.setVisibility(View.GONE);
		SearchRequest request = new SearchRequest(this,
				new SearchRequest.Params(content,
						"" + category.getCategoryid(), pageno, pagesize));
		request.request(new SimpleRequestHandler() {
			@Override
			public void onSuccess(Object object) {
				ContentsRequest.Result result = (ContentsRequest.Result) object;
				if(result.getResult() != 1000){
					mFooterLoading.setVisibility(View.GONE);
					return;
				}
				List<NewsListAdapter.Model> models = News.toNewsList(result
						.getList());
				if (pageno == 1) {
					list.clear();
				}
				if (models.size() >= pagesize) {
					mFooterLoading.setVisibility(View.VISIBLE);
				} else {
					mFooterLoading.setVisibility(View.GONE);
				}
				list.addAll(models);
				adapter.notifyDataSetChanged();

			}
		});

	}

	@Override
	public void onClick(View v) {
		if (v.getTag() instanceof Category) {
			if (lastView != null) {
				lastView.setSelected(false);
			}
			lastView = v;
			v.setSelected(true);
			category = ((Category) v.getTag());
			pageno = 1;
			request();

		}else if(v.getId() == R.id.clear){
			
			editText.setText("");
			
			clearView.setVisibility(View.GONE);
			
		}else if(v.getId() == R.id.back){
			finish();
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		String text = s.toString();
		if (!TextUtils.isEmpty(text)) {
			clearView.setVisibility(View.VISIBLE);
		} else {
			clearView.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		final int lastItem = firstVisibleItem + visibleItemCount;
		if (lastItem == totalItemCount) {
			if (mFooterLoading.getVisibility() == View.VISIBLE) {
				pageno++;
				request();
			}
		}

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			pageno=1;
			request();
			return true;
		}
		return false;
	}

	
	
}
