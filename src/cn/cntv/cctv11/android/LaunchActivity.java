package cn.cntv.cctv11.android;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class LaunchActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_luanch);
		VideoView view = (VideoView) findViewById(R.id.video);
		String path = "android.resource://" + getPackageName() + "/" + R.raw.launch;
		view.setVideoURI(Uri.parse(path));
		view.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				
			}
		});
		view.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				MainActivity.open(LaunchActivity.this);
				finish();
			}
		});
	}
	
}
