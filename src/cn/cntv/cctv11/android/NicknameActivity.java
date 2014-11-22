package cn.cntv.cctv11.android;


import com.mengle.lib.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NicknameActivity extends BaseActivity implements OnClickListener {

	private static final long serialVersionUID = 1L;

	private EditText nicknameText;

	private View confirmBtn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		String nickname = getIntent().getStringExtra("nickname");
		setContentView(R.layout.activity_nickname);
		nicknameText = (EditText) findViewById(R.id.nickname);
		nicknameText.setText(nickname);
		findViewById(R.id.back).setOnClickListener(this);
		confirmBtn = findViewById(R.id.confirm);
		confirmBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.confirm:
			onconfirm();
			break;

		default:
			break;
		}
		
	}

	private void onconfirm() {
		String nickname = nicknameText.getText().toString();
		if(TextUtils.isEmpty(nickname)){
			Utils.tip(this, "请输入新的昵称");
			return;
		}
		
		Intent intent = new Intent();
		intent.putExtra("nickname", nickname);
		setResult(Activity.RESULT_OK, intent);
		finish();

	}

	
	

}