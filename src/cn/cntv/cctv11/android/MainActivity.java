package cn.cntv.cctv11.android;




import cn.cntv.cctv11.android.fragment.MainFragment1;
import cn.cntv.cctv11.android.fragment.MainFragment2;
import cn.cntv.cctv11.android.fragment.MainFragment4;
import cn.cntv.cctv11.android.fragment.StageFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	public static void open(Context context){
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	private FragmentTabHost mTabHost;
	
	private View tab1,tab2,tab3,tab4,tab5;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent); 
		tab1 = getIndicatorView("焦点", R.drawable.icon_main_tab1);
		tab2 = getIndicatorView("直播", R.drawable.icon_main_tab2);
		tab3 = getIndicatorView("戏台", R.drawable.icon_main_tab3);
		tab4 = getIndicatorView("互动", R.drawable.icon_main_tab4);
		tab5 = getIndicatorView("用户", R.drawable.icon_main_tab5);
		
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tab1),MainFragment1.class,null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tab2),MainFragment2.class,null);
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(tab3),StageFragment.class,null);
		mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator(tab4),MainFragment4.class,null);
		mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator(tab5),Fragment.class,null);
	}
	
	private View getIndicatorView(String name,int icon) {  
        View v = getLayoutInflater().inflate(R.layout.tab_item, null);  
        TextView tv = (TextView) v.findViewById(R.id.text);
        ImageView iv = (ImageView) v.findViewById(R.id.icon);
        tv.setText(name);  
        iv.setImageResource(icon);
        return v;  
    }  


}
