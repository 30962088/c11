package cn.cntv.cctv11.android.utils;

import java.util.Arrays;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;

import cn.cntv.cctv11.android.widget.IOSPopupWindow;
import cn.cntv.cctv11.android.widget.IOSPopupWindow.OnIOSItemClickListener;
import android.content.Context;

public class ShareUtils {

	public static void shareImage(final Context context,final String image){
		new IOSPopupWindow(context, new IOSPopupWindow.Params(Arrays.asList(new String[]{
				"保存到相册","分享给QQ好友","分享到QQ空间","分享给微信好友","分享到朋友圈","分享到新浪微博","用邮件发送"}),new OnIOSItemClickListener(){

					@Override
					public void oniositemclick(int pos, String text) {
						if(pos >= 1 && pos <=5){
							pos --;
							SHARE_MEDIA media = new SHARE_MEDIA[] { SHARE_MEDIA.QQ,
									SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
									SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA }[pos];
							final UMSocialService mController = UMServiceFactory
									.getUMSocialService("com.umeng.share");
							mController.setShareImage(new UMImage(context, image));
							mController.directShare(context, media,
									new SnsPostListener() {

										@Override
										public void onStart() {
											// TODO Auto-generated method stub

										}

										@Override
										public void onComplete(SHARE_MEDIA arg0,
												int arg1, SocializeEntity arg2) {

										}
									});
						}
					}
			
		}));
	}

	public static void shareText(final Context context, final String title,final String url) {
		new IOSPopupWindow(context, new IOSPopupWindow.Params(
				Arrays.asList(new String[] { "分享给QQ好友", "分享到QQ空间", "分享给微信好友",
						"分享到朋友圈", "分享到新浪微博" }), new OnIOSItemClickListener() {

					@Override
					public void oniositemclick(int pos, String text) {

						SHARE_MEDIA media = new SHARE_MEDIA[] { SHARE_MEDIA.QQ,
								SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
								SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA }[pos];
						final UMSocialService mController = UMServiceFactory
								.getUMSocialService("com.umeng.share");
						mController.setShareContent(title);
						mController.setAppWebSite(url);
						mController.directShare(context, media,
								new SnsPostListener() {

									@Override
									public void onStart() {
										// TODO Auto-generated method stub

									}

									@Override
									public void onComplete(SHARE_MEDIA arg0,
											int arg1, SocializeEntity arg2) {

									}
								});
					}

				}));
	}

}
