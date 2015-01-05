package com.cctv.xiqu.android;

import org.apache.http.Header;

import com.cctv.xiqu.android.fragment.network.GetLiveUrlRequest;
import com.cctv.xiqu.android.fragment.network.BaseClient.RequestHandler;

import com.cctv.xiqu.android.R;

import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends BaseActivity {

	public static void open(Context context, String url) {
		Intent intent = new Intent(context, VideoActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	private VideoView videoView;

	private View loadingView;

	private MediaController mediaControls;
	
	private String url;
	
	private int position = 0;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		url = getIntent().getStringExtra("url");
		setContentView(R.layout.activity_video_layout);
		videoView = (VideoView) findViewById(R.id.video);
		loadingView = findViewById(R.id.loading);
		if (mediaControls == null) {
			mediaControls = new MediaController(this);
		}
		videoView.setMediaController(mediaControls);
		videoView.setVideoURI(Uri.parse(url));
		videoView.requestFocus();
		videoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				loadingView.setVisibility(View.GONE);
				videoView.seekTo(position);
				if (position == 0) {
					videoView.start();
				}else{
					videoView.pause();
				}
			}
			
		});

	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("Position", videoView.getCurrentPosition());

	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		position = savedInstanceState.getInt("Position");
		videoView.seekTo(position);

	}

}
