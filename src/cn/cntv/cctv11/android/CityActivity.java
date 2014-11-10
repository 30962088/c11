package cn.cntv.cctv11.android;

import org.apache.http.Header;

import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.GetProvinceCityRequest;
import cn.cntv.cctv11.android.fragment.network.GetProvinceCityRequest.Params;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CityActivity extends BaseActivity implements LocationListener {

	private static final long serialVersionUID = 1L;

	private LocationManager mlocManager;

	private String provincecity;

	private TextView city;

	private View confirmBtn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_mycity);
		city = (TextView) findViewById(R.id.city);
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		confirmBtn = findViewById(R.id.confirm);
		confirmBtn.setEnabled(false);
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("city", provincecity);
				setResult(Activity.RESULT_OK, intent);
				finish();

			}
		});
		request();
	}

	private void request() {

		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);

	}

	@Override
	public void onLocationChanged(Location location) {
		mlocManager.removeUpdates(this);
		GetProvinceCityRequest request = new GetProvinceCityRequest(this,
				new Params(location.getLongitude(), location.getLatitude()));

		request.request(new RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				GetProvinceCityRequest.Result result = (GetProvinceCityRequest.Result) object;
				provincecity = result.getProvincecity();
				confirmBtn.setEnabled(true);
				city.setText("您当前所在城市：" + provincecity);
				
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mlocManager.removeUpdates(this);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}