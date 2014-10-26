package cn.cntv.cctv11.android.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SnsAccount;
import com.umeng.socialize.bean.SocializeUser;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.FetchUserListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

public class OauthUtils implements UMAuthListener, FetchUserListener {

	public static class Result {

		private String sid;
		
		private SHARE_MEDIA media;

		public Result(String sid, SHARE_MEDIA media) {
			super();
			this.sid = sid;
			this.media = media;
		} 
		
		public SHARE_MEDIA getMedia() {
			return media;
		}
		public String getSid() {
			return sid;
		}
	}

	private UMSocialService mController;

	private Context context;

	private SHARE_MEDIA media;

	public static interface OauthCallback {
		public void onSuccess(Result params);
	}

	private OauthCallback callback;

	public OauthUtils(Context context) {
		this.context = context;
		mController = UMServiceFactory.getUMSocialService("com.umeng.login",RequestType.SOCIAL);
	}

	public void setOauthCallback(OauthCallback callback) {
		this.callback = callback;
	}

	public void tencentOauth() {
		media = SHARE_MEDIA.TENCENT;
		oauth();
	}

	public void sinaOauth() {
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		media = SHARE_MEDIA.SINA;
		oauth();
	}

	public void renrenOauth() {
		media = SHARE_MEDIA.RENREN;
		oauth();

	}

	public void qqOauth() {
		media = SHARE_MEDIA.QQ;
		/*mController.getConfig().supportAPlatform((Activity) context,
				"100424468", "http://www.umeng.com/social", "social");*/
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.addToSocialSDK();
		oauth();
	}

	private void oauth() {
		mController.doOauthVerify(context, media, this);
	}

	@Override
	public void onCancel(SHARE_MEDIA arg0) {
		// TODO Auto-generated method stub

	}

	private Result params;

	@Override
	public void onComplete(Bundle arg0, SHARE_MEDIA arg1) {
		mController.getUserInfo(context, this);
		if (arg1 == SHARE_MEDIA.TENCENT) {
			params = new Result(arg0.getString("uid"),SHARE_MEDIA.TENCENT);
		} else if (arg1 == SHARE_MEDIA.QQ) {
			params = new Result(arg0.getString("uid"),SHARE_MEDIA.QQ);
		
		} else if (arg1 == SHARE_MEDIA.SINA) {
			params = new Result(arg0.getString("uid"),SHARE_MEDIA.SINA);
		} else if (arg1 == SHARE_MEDIA.RENREN) {
			params = new Result(arg0.getString("uid"),SHARE_MEDIA.RENREN);
		}
	}

	@Override
	public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(SHARE_MEDIA arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(int arg0, SocializeUser user) {
		SnsAccount snsAccount = null;
		if (user != null && user.mAccounts != null) {
			for (SnsAccount account : user.mAccounts) {
				if (media == SHARE_MEDIA.SINA
						&& "sina".equals(account.getPlatform())) {
					snsAccount = account;
					break;
				} else if (media == SHARE_MEDIA.TENCENT
						&& "tencent".equals(account.getPlatform())) {
					snsAccount = account;

					break;
				} else if (media == SHARE_MEDIA.QQ
						&& "qq".equals(account.getPlatform())) {
					snsAccount = account;
					break;
				} else if (media == SHARE_MEDIA.RENREN
						&& "renren".equals(account.getPlatform())) {
					snsAccount = account;
					break;
				}

			}
		}
		
		if (callback != null) {
			callback.onSuccess(params);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

	}

}
