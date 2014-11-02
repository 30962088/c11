package cn.cntv.cctv11.android.widget;

import java.io.IOException;

import cn.cntv.cctv11.android.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.util.AttributeSet;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class VideoView extends FrameLayout implements SurfaceHolder.Callback,
		MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl,
		MediaPlayer.OnBufferingUpdateListener {

	public VideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VideoView(Context context) {
		super(context);
	}

	private SurfaceView videoSurface;
	private MediaPlayer player;
	private VideoControllerView controller;
	private SurfaceHolder videoHolder;
	private View contaienr, loadingView;

	public void setVideoPath(String url,
			OnCompletionListener onCompletionListener,
			OnErrorListener onErrorListener) {
		if (contaienr == null) {
			LayoutInflater.from(getContext()).inflate(
					R.layout.activity_video_player, this);
			videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
			loadingView = findViewById(R.id.loading);
			videoHolder = videoSurface.getHolder();

			controller = new VideoControllerView(getContext());
			videoHolder.addCallback(this);
			player = new MediaPlayer();
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setOnPreparedListener(this);
			
		}

		try {
			player.setOnErrorListener(onErrorListener);

			player.setOnCompletionListener(onCompletionListener);
			player.setDataSource(getContext(), Uri.parse(url));
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.setOnBufferingUpdateListener(this);

	}
	
	private boolean prepared = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(prepared){
			controller.show();
		}
		
		return false;
	}

	// Implement SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);
		player.prepareAsync();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	// End SurfaceHolder.Callback

	// Implement MediaPlayer.OnPreparedListener
	@Override
	public void onPrepared(MediaPlayer mp) {
		controller.setMediaPlayer(this);
		controller
				.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
		player.start();
		loadingView.setVisibility(View.GONE);
		prepared = true;
	}

	// End MediaPlayer.OnPreparedListener

	// Implement VideoMediaController.MediaPlayerControl
	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return player.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void seekTo(int i) {
		player.seekTo(i);
	}

	@Override
	public void start() {
		player.start();
	}

	@Override
	public boolean isFullScreen() {
		return fullScreen;
	}

	public static interface OnToggleFullScreenListener {
		public void onToggleFullScreen(boolean isFullScreen);
	}

	private OnToggleFullScreenListener onToggleFullScreenListener;

	public void setOnToggleFullScreenListener(
			OnToggleFullScreenListener onToggleFullScreenListener) {
		this.onToggleFullScreenListener = onToggleFullScreenListener;
	}

	private boolean fullScreen = false;

	@Override
	public void toggleFullScreen() {
		if (onToggleFullScreenListener != null) {
			fullScreen = !fullScreen;
			onToggleFullScreenListener.onToggleFullScreen(fullScreen);
		}
	}

	

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		System.out.println("zzm"+percent);
		
	}
}
