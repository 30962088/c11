package cn.cntv.cctv11.android.utils;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import com.mengle.lib.utils.Utils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import cn.cntv.cctv11.android.APP;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.widget.IOSPopupWindow;
import cn.cntv.cctv11.android.widget.IOSPopupWindow.OnIOSItemClickListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ShareUtils {

	public static void shareWeixinWeb(Context context, String title,
			String url, SHARE_MEDIA media) {
		int scene = SendMessageToWX.Req.WXSceneSession;
		if (media == SHARE_MEDIA.WEIXIN_CIRCLE) {
			scene = SendMessageToWX.Req.WXSceneTimeline;
		}
		WXWebpageObject localWXWebpageObject = new WXWebpageObject();
		localWXWebpageObject.webpageUrl = url;
		WXMediaMessage localWXMediaMessage = new WXMediaMessage(
				localWXWebpageObject);
		localWXMediaMessage.title = title;
		localWXMediaMessage.description = title;
		localWXMediaMessage.thumbData = getBitmapBytes(drawableToBitmap(context
				.getResources().getDrawable(R.drawable.ic_launcher)), false);
		SendMessageToWX.Req localReq = new SendMessageToWX.Req();
		localReq.scene = scene;
		localReq.transaction = System.currentTimeMillis() + "";
		localReq.message = localWXMediaMessage;
		IWXAPI api = WXAPIFactory.createWXAPI(context, APP.getAppConfig()
				.getWX_APPID(), true);
		api.sendReq(localReq);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
		Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);
		int i;
		int j;
		if (bitmap.getHeight() > bitmap.getWidth()) {
			i = bitmap.getWidth();
			j = bitmap.getWidth();
		} else {
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
		while (true) {
			localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
					80, 80), null);
			if (paramBoolean)
				bitmap.recycle();
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {

			}
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
	}

	public static void shareImage(final Context context, final String image) {
		new IOSPopupWindow(context, new IOSPopupWindow.Params(
				Arrays.asList(new String[] { "保存到相册", "分享给QQ好友", "分享到QQ空间",
						"分享给微信好友", "分享到朋友圈", "分享到新浪微博", "用邮件发送" }),
				new OnIOSItemClickListener() {

					@Override
					public void oniositemclick(int pos, String text) {
						if (pos >= 1 && pos <= 5) {
							pos--;
							SHARE_MEDIA media = new SHARE_MEDIA[] {
									SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
									SHARE_MEDIA.WEIXIN,
									SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA }[pos];
							final UMSocialService mController = UMServiceFactory
									.getUMSocialService("com.umeng.share");
							mController.getConfig().closeToast();
							mController.setShareImage(new UMImage(context,
									image));

							mController.directShare(context, media,
									new SnsPostListener() {

										@Override
										public void onStart() {

										}

										@Override
										public void onComplete(
												SHARE_MEDIA arg0, int arg1,
												SocializeEntity arg2) {

										}
									});
						}
					}

				}));
	}

	public static void shareText(final Context context, final String title,
			final String url) {
		new IOSPopupWindow(context, new IOSPopupWindow.Params(
				Arrays.asList(new String[] { "分享给QQ好友", "分享到QQ空间", "分享给微信好友",
						"分享到朋友圈", "分享到新浪微博" }), new OnIOSItemClickListener() {

					@Override
					public void oniositemclick(int pos, String text) {

						SHARE_MEDIA media = new SHARE_MEDIA[] { SHARE_MEDIA.QQ,
								SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
								SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA }[pos];
						if (media == SHARE_MEDIA.WEIXIN
								|| media == SHARE_MEDIA.WEIXIN_CIRCLE) {
							shareWeixinWeb(context, title, url, media);
						} else {

							final UMSocialService mController = UMServiceFactory
									.getUMSocialService("com.umeng.share");
							mController.getConfig().closeToast();
							switch (media) {
							case QZONE:
							case QQ:

								break;
							case SINA:
								mController.setShareContent(url);
								break;
							default:
								break;
							}

							mController.directShare(context, media,
									new SnsPostListener() {

										@Override
										public void onStart() {

										}

										@Override
										public void onComplete(
												SHARE_MEDIA arg0, int arg1,
												SocializeEntity arg2) {

										}
									});
						}
					}

				}));
	}

}
