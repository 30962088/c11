package cn.cntv.cctv11.android.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.cntv.cctv11.android.APP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CropImageUtils {
	private static Bitmap resizeBitmap(String photoPath, int targetW,
			int targetH) {
		Bitmap bitmap = getBitmapFromFile(new File(photoPath), targetW, targetH);

		return crop(bitmap, targetW, targetH);
	}

	public static Bitmap crop(Bitmap bitmap, int twidth, int theight) {
		int width = bitmap.getWidth(), height = bitmap.getHeight();
		int cropWidth = 0, cropHeight = 0;
		if (width == height) {
			cropWidth = width;
			cropHeight = height;
		} else if (width > height) {
			cropHeight = height;
			cropWidth = height;
		} else {
			cropWidth = width;
			cropHeight = width;
		}

		int x = (width - cropWidth) / 2;

		int y = (height - cropHeight) / 2;

		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, x, y, cropWidth,
				cropHeight);

		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, twidth, theight,
				true);

		bitmap1.recycle();

		return bitmap2;

	}

	public static File cropImage(File image, int width, int height) {
		FileOutputStream out = null;
		File outputDir = APP.getInstance().getCacheDir();
		File outputFile = null;
		Bitmap bitmap = null;
		try {
			outputFile = File.createTempFile("prefix", ".jpg", outputDir);
			bitmap = resizeBitmap(image.toString(), width, height);
			out = new FileOutputStream(outputFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outputFile;
	}
	

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	public static Bitmap getBitmapFromFile(File dst, int width, int height) {
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
