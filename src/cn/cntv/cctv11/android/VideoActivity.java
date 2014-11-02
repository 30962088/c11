package cn.cntv.cctv11.android;

import org.apache.http.Header;



import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.GetLiveUrlRequest;
import cn.cntv.cctv11.android.widget.VideoView;

import android.os.Bundle;



public class VideoActivity extends BaseActivity {

	VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_layout);
		videoView = (VideoView) findViewById(R.id.video);
		request();
	}
	
	private void request(){
		GetLiveUrlRequest request = new GetLiveUrlRequest(this);
		request.request(new RequestHandler() {
			
			@Override
			public void onSuccess(Object object) {
				String list[] =((GetLiveUrlRequest.Result)object).getHls_url().toList();
//				videoView.setVideoUri(list[0]);
//				getSupportFragmentManager().beginTransaction().replace(R.id.media_container, VideoFrament.newInstance(list)).commit();
				
			}
			
			@Override
			public void onServerError(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(int error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
}
