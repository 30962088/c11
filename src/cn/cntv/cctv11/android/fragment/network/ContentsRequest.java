package cn.cntv.cctv11.android.fragment.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;


import cn.cntv.cctv11.android.adapter.NewsListAdapter.Model;
import cn.cntv.cctv11.android.adapter.NewsListAdapter.Model.Category;
import cn.cntv.cctv11.android.adapter.NewsListAdapter.Model.Category.Background;
import cn.cntv.cctv11.android.fragment.SliderFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class ContentsRequest extends BaseClient{

	public static class Result{
		private List<News> list;
		private List<News> lunbolist;
		public List<News> getList() {
			return list;
		}
		public List<News> getLunbolist() {
			return lunbolist;
		}
	}
	
	
	
	public static class News{
		private int contentsid;
		private String contentstitle;
		private String poemauthor;
		private int ishavevideo;
		private int categoryid;
		private String categoryname;
		private String videositeurl;
		private int istoutiao;
		private int islunbo;
		private Attachment attachment;
		private int commentcount;
		private String contentsdate;
		private Background getBackground(int id){
			Map<Integer, Background> map = new HashMap<Integer, Background>();
			map.put(1, Background.RED);
			map.put(2, Background.PURPLE);
			map.put(3, Background.GREEN);
			Background background = map.get(id);
			if(background == null){
				background = Background.RED;
			}
			return background;
		}
		public Model toNewsModel(){
			Category category = null;
			
			if(istoutiao == 1 && categoryname != null){
				category = new Category(getBackground(categoryid),categoryname);
			}
			return new Model(attachment.attachmentimgurl, contentstitle, commentcount, false,category);
		}
		public static List<Model> toNewsList(List<News> list){
			List<Model> models = new ArrayList<Model>();
			for(News n : list){
				if(n.islunbo == 0){
					models.add(n.toNewsModel());
				}
			}
			return models;
		}
		public SliderFragment.Model toSliderModel(){
			return new SliderFragment.Model(contentsid, attachment.attachmentimgurl, contentstitle);
		}
		public static ArrayList<SliderFragment.Model> toSliderList(List<News> list){
			ArrayList<SliderFragment.Model> models = new ArrayList<SliderFragment.Model>();
			if(list != null){
				for(News n : list){
					if(n.islunbo == 1){
						models.add(n.toSliderModel());
					}
				}
			}
			
			return models;
		}
	}
	
	public static class Attachment{
		private int attachmentid;
		private String attachmentname;
		private String attachmentguid;
		private String attachmentformat;
		private String attachmentsize;
		private int attachmentwidth;
		private int attachmentheight;
		private String attachmentdate;
		private String attachmentimgurl;
		private int isfirstimg;
	}
	
	
	public static class Params{
		private int categoryid;
		private int pageno;
		private int pagesize;
		public Params(int categoryid, int pageno, int pagesize) {
			super();
			this.categoryid = categoryid;
			this.pageno = pageno;
			this.pagesize = pagesize;
		}
		
	}

	private Params param;

	public ContentsRequest(Params param) {
		super();
		this.param = param;
	}

	@Override
	public Object onSuccess(String str) {
		return new Gson().fromJson(str,Result.class);
		
	}
	
	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onServerError(int arg0, Header[] arg1, byte[] arg2,
			Throwable arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method","contents");
		params.add("pageno",""+param.pageno);
		params.add("pagesize",""+param.pagesize);
		params.add("categoryid",""+param.categoryid);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "contents";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
