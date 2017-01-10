package com.app.paddycameraeditior.bitmap;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class BitmapWriter extends AsyncTask<Void, Void, Boolean> {

	private final int BUFFER_SIZE = 1024 * 10;
	private File file;
	private Bitmap bitmap;
	private Context context;

	public BitmapWriter(File input_file, Bitmap input_bitmap,Context cxt) {
		file = input_file;
		bitmap = input_bitmap;
		context = cxt;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file, true);
			final BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);
			bitmap.compress(CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			fos.close();

			ContentValues values = new ContentValues();

			values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
			values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
			values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());

			context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		try {
			bitmap.recycle();
			bitmap = null;
		} catch (Exception e) {}
	}
}