package com.aoeng.play.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

/**
 * 图片工具类，对图片进行一些处理
 * 
 * @author renyangyang
 * 
 */
public class ImageUtil {

	public static Bitmap parseBitmap(String path, int size) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int max = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
		if (max > size) {
			int sam = max / size;
			options.inSampleSize = sam;
			int height = options.outHeight / sam;
			Log.e("height", "---" + height);
			int width = options.outWidth / sam;
			options.outWidth = width;
			options.outHeight = height;

		}
		/* 这样才能真正的返回一个Bitmap给你 */
		options.inJustDecodeBounds = false;
		return getBitmapByPath(path, options);
	}

	public static Bitmap parseBitmapToLittle(String path) {
		return parseBitmap(path, 320);
	}

	public static Bitmap parseHeadBitmapToLittle(String path) {
		return parseBitmap(path, 120);
	}

	/**
	 * 获取bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath) {
		return getBitmapByPath(filePath, null);
	}

	public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
		if (StringUtils.isEmpty(filePath))
			return null;
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 根据Uri获取文件的路径
	 * 
	 * @Title: getUriString
	 * @param uri
	 * @return String
	 */
	public static String getUriString(Uri uri, ContentResolver cr) {
		String imgPath = null;
		if (uri != null) {
			String uriString = uri.toString();
			// 小米手机的适配问题，小米手机的uri以file开头，其他的手机都以content开头
			// 以content开头的uri表明图片插入数据库中了，而以file开头表示没有插入数据库
			// 所以就不能通过query来查询，否则获取的cursor会为null。
			if (uriString.startsWith("file")) {
				// uri的格式为file:///mnt....,将前七个过滤掉获取路径
				imgPath = uriString.substring(7, uriString.length());
				return imgPath;
			}
			Cursor cursor = cr.query(uri, null, null, null, null);
			cursor.moveToFirst();
			imgPath = cursor.getString(1); // 图片文件路径

		}
		return imgPath;
	}

	/**
	 * 写图片文件到SD卡
	 * 
	 * @throws IOException
	 */
	public static void saveImageToSD(final String filePath, final Bitmap bitmap) {
		new Thread() {
			public void run() {
				try {
					if (bitmap != null) {
						FileOutputStream fos = new FileOutputStream(filePath);
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(CompressFormat.PNG, 100, stream);
						byte[] bytes = stream.toByteArray();
						fos.write(bytes);
						fos.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();

	}

	/**
	 * 压缩图片 上传图片时调用
	 * 
	 * @param imgPath
	 * @return
	 */
	public static Bitmap compressImg(String imgPath, int maxSize) {
		Bitmap resizeBitmap = null;
		if (StringUtils.isEmpty(imgPath)) {
			try {
				resizeBitmap = parseBitmap(imgPath, 1024);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				ExifInterface sourceExif = new ExifInterface(imgPath);
				int result = sourceExif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
				int rotate = 0;
				switch (result) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				}

				if (resizeBitmap != null) {
					if (rotate > 0) {
						Matrix matrix = new Matrix();
						matrix.setRotate(rotate);

						Bitmap rotateBitmap = Bitmap.createBitmap(resizeBitmap, 0, 0, resizeBitmap.getWidth(), resizeBitmap.getHeight(), matrix, true);
						if (rotateBitmap != null) {
							resizeBitmap.recycle();
							resizeBitmap = rotateBitmap;
						}
					}
					int options = 90;
					resizeBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
					while (baos.toByteArray().length > maxSize) { // 循环判断如果压缩后图片是否大于200K,大于继续压缩
						baos.reset();// 重置baos即清空baos
						resizeBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
						options -= 10;// 每次都减少10
					}
					// bmp.recycle();

					return resizeBitmap;
				}

			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static InputStream compressForUpload(String imgPath, int maxSize) {
		Bitmap resizeBitmap = null;
		if (StringUtils.isEmpty(imgPath)) {
			try {
				resizeBitmap = parseBitmap(imgPath, 1024);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				ExifInterface sourceExif = new ExifInterface(imgPath);
				int result = sourceExif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
				int rotate = 0;
				switch (result) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				}

				if (resizeBitmap != null) {
					if (rotate > 0) {
						Matrix matrix = new Matrix();
						matrix.setRotate(rotate);

						Bitmap rotateBitmap = Bitmap.createBitmap(resizeBitmap, 0, 0, resizeBitmap.getWidth(), resizeBitmap.getHeight(), matrix, true);
						if (rotateBitmap != null) {
							resizeBitmap.recycle();
							resizeBitmap = rotateBitmap;
						}
					}
					int options = 90;
					resizeBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
					while (baos.toByteArray().length > maxSize) { // 循环判断如果压缩后图片是否大于200K,大于继续压缩
						baos.reset();// 重置baos即清空baos
						resizeBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
						options -= 10;// 每次都减少10
					}
					// bmp.recycle();
					InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
					return sbs;
				}

			} catch (OutOfMemoryError e) {
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
