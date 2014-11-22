package cn.cntv.cctv11.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class WeiboPublishActivity extends BaseActivity implements OnClickListener{

	public static void open(Context context,String id){
		Intent intent = new Intent(context, WeiboPublishActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibocomment_publish_layout);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
		
	}
	
}
