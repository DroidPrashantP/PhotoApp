package com.app.paddycameraeditior.imagesavelib;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.app.paddycameraeditior.BuildConfig;
import com.app.paddycameraeditior.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageLoader {
    static boolean DEBUG;
    String TAG;
    Context context;
    int count;
    Cursor cursorBackup;
    public String filemanagerstring;
    ImageLoaded imageLoadedListener;
    String loadImageMessage;
    public String selectedImagePath;

    public ImageLoader(Context context) {
        this.TAG = "ImageLoader";
        this.count = 0;
        this.loadImageMessage = "Loading image!";
        this.context = context;
    }

    /* renamed from: com.lyrebirdstudio.imagesavelib.ImageLoader.1 */
    class C05851 implements OnClickListener {
        C05851() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    }

    public interface ImageLoaded {
        void callFileSizeAlertDialogBuilder();
    }

    private class LoadImage19Task extends AsyncTask<Uri, Void, Void> {
        String path;
        ProgressDialog saveImageDialog;
        Uri uri;

        private LoadImage19Task() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            ImageLoader.this.loadImageMessage = ImageLoader.this.context.getString(R.string.loading_image);
            try {
                this.saveImageDialog = new ProgressDialog(ImageLoader.this.context);
                this.saveImageDialog.setMessage(ImageLoader.this.loadImageMessage);
                this.saveImageDialog.show();
            } catch (Exception e) {
            }
        }

        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            ImageLoader.this.selectedImagePath = this.path;
            ImageLoader.this.startActivityFromUri(this.uri);
            try {
                this.saveImageDialog.dismiss();
            } catch (Exception e) {
            }
        }

        protected Void doInBackground(Uri... arg0) {
            if (arg0 != null) {
                try {
                    this.uri = arg0[0];
                    if (this.uri != null) {
                        this.path = ImageLoader.this.getRealPathFromURI19(this.uri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public void setListener(ImageLoaded l) {
        this.imageLoadedListener = l;
    }

    public void getImageFromIntent(Intent intent) {
        Uri selectedImageUri = intent.getData();
        if (selectedImageUri == null) {
            selectedImageUri = (Uri) intent.getExtras().get("android.intent.extra.STREAM");
        }
        if (VERSION.SDK_INT >= 19) {
            this.selectedImagePath = getPathForKitKat(selectedImageUri);
            Log.w(this.TAG, "getPathForKitKat " + this.selectedImagePath);
            if (this.selectedImagePath == null) {
                new LoadImage19Task().execute(selectedImageUri);
                return;
            } else {
                startActivityFromUri(selectedImageUri);
                return;
            }
        }
        this.selectedImagePath = getRealPathFromURI(selectedImageUri);
        Log.e(this.TAG, "getImageFromIntent selectedImagePath  " + this.selectedImagePath);
        startActivityFromUri(selectedImageUri);
    }

    void startActivityFromUri(Uri selectedImageUri) {
        this.filemanagerstring = selectedImageUri.getPath();
        Log.w(this.TAG, "startActivityFromUri selectedImagePath" + this.selectedImagePath);
        if (this.selectedImagePath == null) {
            this.selectedImagePath = this.filemanagerstring;
            Log.w(this.TAG, "null selectedImagePath " + this.selectedImagePath);
        }
        if (this.selectedImagePath == null || this.selectedImagePath.length() == 0 || this.selectedImagePath.toLowerCase().contains("http")) {
            new LoadImage19Task().execute(selectedImageUri);
            this.count++;
        } else if (checkFileExtension(this.selectedImagePath)) {
            this.imageLoadedListener.callFileSizeAlertDialogBuilder();
        } else {
            Builder builder = new Builder(this.context);
            builder.setMessage("Image Format Error").setCancelable(false).setNegativeButton("Ok", new C05851());
            builder.create().show();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = new String[]{"_data"};
        Cursor cursor = this.context.getContentResolver().query(contentUri, proj, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                res = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            }
            cursor.close();
        } catch (Exception e) {
        }
        if (cursor == null || res == null) {
            this.cursorBackup = this.context.getContentResolver().query(contentUri, proj, null, null, null);
            try {
                if (this.cursorBackup != null) {
                    int column_index = this.cursorBackup.getColumnIndexOrThrow("_data");
                    this.cursorBackup.moveToFirst();
                    res = this.cursorBackup.getString(column_index);
                }
            } catch (Exception e2) {
            }
        }
        return res;
    }

    public String getRealPathFromURI19(Uri contentUri) throws IOException {
        return saveImageToTemp(getBitmapFromUri(contentUri));
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.context.getContentResolver().openFileDescriptor(uri, "r");
        Bitmap image = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
        parcelFileDescriptor.close();
        return image;
    }

    private String saveImageToTemp(Bitmap bitmap) throws FileNotFoundException {
        String resultPath = new StringBuilder(String.valueOf(ImageUtility.getPrefferredDirectoryPath(this.context, false, true, false))).append("temp/dump.dump").toString();
        File f = new File(resultPath);
        f.getParentFile().mkdirs();
        Log.i(this.TAG, "resultPath " + resultPath);
        bitmap.compress(CompressFormat.JPEG, 90, new FileOutputStream(resultPath));
        bitmap.recycle();
        Log.i(this.TAG, "is file exist " + f.exists());
        return resultPath;
    }

    private String getFileExtension(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        int dotPos = str.lastIndexOf(".");
        String extension = BuildConfig.FLAVOR;
        if (dotPos > 0) {
            return str.substring(dotPos);
        }
        return extension;
    }

    private boolean checkFileExtension(String str) {
        String extension = getFileExtension(str).toLowerCase();
        if (extension.contains("jpg") || extension.contains("png") || extension.contains("jpeg") || extension.contains("gif") || extension.contains("bmp") || extension.contains("webp") || extension.contains("dump")) {
            return true;
        }
        return false;
    }

    public void closeCursor() {
        if (this.cursorBackup != null) {
            this.cursorBackup.close();
        }
    }

    static {
        DEBUG = true;
    }

    @SuppressLint({"NewApi"})
    public String getPathForKitKat(Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isLocalStorageDocument(Uri uri) {
        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            if (DEBUG) {
                DatabaseUtils.dumpCursor(cursor);
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            if (cursor == null) {
                return string;
            }
            cursor.close();
            return string;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return column;
    }
}
