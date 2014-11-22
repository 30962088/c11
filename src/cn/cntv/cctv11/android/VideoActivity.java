package cn.cntv.cctv11.android;



import org.apache.http.Header;



import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.GetLiveUrlRequest;


import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;



public class VideoActivity extends BaseActivity {

	VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_layout);
		videoView = new VideoView(this);
		request();
	}
	
	private void request(){
		GetLiveUrlRequest request = new GetLiveUrlRequest(this);
		request.request(new RequestHandler() {
			
			@Override
			public void onSuccess(Object object) {
				String list[] =((GetLiveUrlRequest.Result)object).getHls_url().toList();
				videoView.setVideoURI(Uri.parse("http://vcbox1.fw.live.cntv.cn:8000/cache/cctv11.f4m?AUTH=ip%3D221.216.138.206%7Est%3D1415443475%7Eexp%3D1415529875%7Eacl%3D%2F*%7Ehmac%3D25dd7f6f8a3c0ab83d36ddc8b8df1301684e0175d98a90157e82bf9f52a4f5df"));
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

			@Override
			public void onComplete() {
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
