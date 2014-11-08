package cn.cntv.cctv11.android.widget;

import java.io.IOException;

import cn.cntv.cctv11.android.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.util.AttributeSet;

import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class VideoView extends FrameLayout implements SurfaceHolder.Callback,
		MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl,
		MediaPlayer.OnBufferingUpdateListener,OnKeyListener {

	public VideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public VideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public VideoView(Context context) {
		super(context);
		init();
	}

	private SurfaceView videoSurface;
	private MediaPlayer player;
	private VideoControllerView controller;
	private SurfaceHolder videoHolder;
	private View contaienr, loadingView;
	
	private GestureDetector gestureDetector;

	private void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.activity_video_player, this);
		gestureDetector = new GestureDetector(getContext(), new SingleTapConfirm());
		videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
		loadingView = findViewById(R.id.loading);
		loadingView.setVisibility(View.GONE);
		videoHolder = videoSurface.getHolder();
		videoHolder.addCallback(this);
		setClickable(true);
		
		controller = new VideoControllerView(getContext());
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setOnPreparedListener(this);
		player.setScreenOnWhilePlaying(true);
		setOnKeyListener(this);
	}

	public void setVideoPath(String url,
			OnCompletionListener onCompletionListener,
			OnErrorListener onErrorListener) {

		try {
			loadingView.setVisibility(View.VISIBLE);
			player.setDataSource(getContext(), Uri.parse(url));
			player.prepareAsync();
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
		// player.setOnBufferingUpdateListener(this);

	}

	private boolean prepared = false;

	class SingleTapConfirm extends SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent event) {
			if (prepared) {
				if(controller.isShowing()){
					controller.hide();
				}else{
					controller.show();
				}
				
			}
			return true;
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev);
		return true;
	}
	

	

	// Implement SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);

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
	
	public void release(){
		player.release();
	}

	@Override
	public boolean isFullScreen() {
		return fullScreen;
	}
	
	public boolean isPrepared() {
		return prepared;
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

	private ViewGroup parent;

	private int indexOfChild;

	@SuppressLint("NewApi")
	@Override
	public void toggleFullScreen() {
		fullScreen = !fullScreen;
		if (fullScreen) {
			screenFull();
		} else {
			screenNormal();
		}
	}
	
	private void screenNormal(){
		Activity activity = ((Activity) getContext());
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		((ViewGroup) getParent()).removeView(this);
		parent.addView(this, indexOfChild);
		
	}
	
	private void screenFull(){
		parent = ((ViewGroup) getParent());
		indexOfChild = parent.indexOfChild(this);
		parent.removeView(this);
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		Activity activity = ((Activity) getContext());
		activity.getWindow().addContentView(this, params);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setFocusableInTouchMode(true);
		requestFocus();
		
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		System.out.println("zzm" + percent);

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(isFullScreen()){
			controller.fullscreenButtonPerformClick();
			return true;
		}
		return false;
	}
}
