package cn.cntv.cctv11.android.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.adapter.LiveListAdapter;
import cn.cntv.cctv11.android.adapter.LiveListAdapter.Model.State;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.GetLiveUrlRequest;
import cn.cntv.cctv11.android.fragment.network.LiveProgramRequest;
import cn.cntv.cctv11.android.fragment.network.LiveProgramRequest.Result;
import cn.cntv.cctv11.android.widget.VideoView;
import cn.cntv.cctv11.android.widget.VideoView.OnToggleFullScreenListener;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class LiveFragment extends BaseFragment{
	
	public static LiveFragment newInstance(){
		return new LiveFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.live_layout, null);
	}
	
	private List<LiveListAdapter.Model> list = new ArrayList<LiveListAdapter.Model>();
	
	private LiveListAdapter adapter;
	

	
	private VideoView videoView;

	
	private ListView listView;
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		videoView = (VideoView) view.findViewById(R.id.video);
		videoView.setVidepPath("http://m3u8.1du1du.com:1019/index.m3u8");
//		videoView.setVidepPath("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");
		
		listView = (ListView) view.findViewById(R.id.listview);
		adapter = new LiveListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		request();
	}
	
	

	

	private void request() {
		LiveProgramRequest request = new LiveProgramRequest(getActivity());
		request.request(new RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				if(object == null){
					return;
				}
				list.clear();
				Result result = (Result) object;
				List<LiveListAdapter.Model> models = result.toLiveList();
				int index = 0;
				for(int i = 0 ;i<models.size();i++){
					if(models.get(i).getState() == State.CURRENT){
						index = i;
						break;
					}
				}
				list.addAll(models);
				adapter.notifyDataSetChanged();
				listView.setSelection(index);

			}


			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void onError(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
//	private boolean isPlaying;
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		isPlaying = videoView.isPlaying();
//		if(videoView.isPrepared() && videoView.isPlaying()){
			videoView.pause();
//		}
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		videoView.release();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		if(videoView.isPrepared() && isPlaying){
//			videoView.start();
//		}
	}
	



}
