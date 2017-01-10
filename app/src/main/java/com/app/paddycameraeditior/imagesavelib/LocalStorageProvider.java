package com.app.paddycameraeditior.imagesavelib;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MatrixCursor.RowBuilder;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.provider.DocumentsProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.app.paddycameraeditior.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressLint({"NewApi"})
public class LocalStorageProvider extends DocumentsProvider {
    public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";
    private static final String[] DEFAULT_DOCUMENT_PROJECTION;
    private static final String[] DEFAULT_ROOT_PROJECTION;

    static {
        DEFAULT_ROOT_PROJECTION = new String[]{"root_id", "summary", "flags", "title", "document_id", "icon", "available_bytes"};
        DEFAULT_DOCUMENT_PROJECTION = new String[]{"document_id", "_display_name", "flags", "mime_type", "_size", "last_modified"};
    }

    private RowBuilder row;
    private IOException e2;
    private IOException e222;

    public Cursor queryRoots(String[] projection) throws FileNotFoundException {
        if (projection == null) {
            projection = DEFAULT_ROOT_PROJECTION;
        }
        MatrixCursor result = new MatrixCursor(projection);
        File homeDir = Environment.getExternalStorageDirectory();
        if (TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
            RowBuilder row = result.newRow();
            row.add("root_id", homeDir.getAbsolutePath());
            row.add("document_id", homeDir.getAbsolutePath());
            row.add("title", getContext().getString(R.string.select_more_apps));
            row.add("flags", Integer.valueOf(3));
            row.add("icon", Integer.valueOf(R.drawable.ic_launcher_lg));
            row.add("summary", homeDir.getAbsolutePath());
            row.add("available_bytes", Long.valueOf(new StatFs(homeDir.getAbsolutePath()).getAvailableBytes()));
        }
        File sdCard = new File("/storage/extSdCard");
        String storageState = Environment.getStorageState(sdCard);
        if (TextUtils.equals(storageState, "mounted") || TextUtils.equals(storageState, "mounted_ro")) {
            row = result.newRow();
            row.add("root_id", sdCard.getAbsolutePath());
            row.add("document_id", sdCard.getAbsolutePath());
            row.add("title", getContext().getString(R.string.select_more_apps));
            row.add("flags", Integer.valueOf(2));
            row.add("icon", Integer.valueOf(R.drawable.save));
            row.add("summary", sdCard.getAbsolutePath());
            row.add("available_bytes", Long.valueOf(new StatFs(sdCard.getAbsolutePath()).getAvailableBytes()));
        }
        return result;
    }

    public String createDocument(String parentDocumentId, String mimeType, String displayName) throws FileNotFoundException {
        File newFile = new File(parentDocumentId, displayName);
        try {
            newFile.createNewFile();
            return newFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e(LocalStorageProvider.class.getSimpleName(), "Error creating new file " + newFile);
            return null;
        }
    }

    public AssetFileDescriptor openDocumentThumbnail(String documentId, Point sizeHint, CancellationSignal signal) throws FileNotFoundException {
        IOException e;
        Throwable th;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(documentId, options);
        int targetHeight = sizeHint.y * 2;
        int targetWidth = sizeHint.x * 2;
        int height = options.outHeight;
        int width = options.outWidth;
        options.inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (true) {
                if (halfHeight / options.inSampleSize <= targetHeight && halfWidth / options.inSampleSize <= targetWidth) {
                    break;
                }
                options.inSampleSize *= 2;
            }
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(documentId, options);
        FileOutputStream out = null;
        try {
            File tempFile = File.createTempFile("thumbnail", null, getContext().getCacheDir());
            FileOutputStream out2 = new FileOutputStream(tempFile);
            try {
                bitmap.compress(CompressFormat.PNG, 90, out2);
                if (out2 != null) {
                    try {
                        out2.close();
                    } catch (IOException e2) {
                        Log.e(LocalStorageProvider.class.getSimpleName(), "Error closing thumbnail", e2);
                    }
                }
                out = out2;
                return new AssetFileDescriptor(ParcelFileDescriptor.open(tempFile, 268435456), 0, -1);
            } catch (IOException e3) {
                e2 = e3;
                out = out2;
                try {
                    Log.e(LocalStorageProvider.class.getSimpleName(), "Error writing thumbnail", e2);
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e22) {
                            Log.e(LocalStorageProvider.class.getSimpleName(), "Error closing thumbnail", e22);
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e222) {
                            Log.e(LocalStorageProvider.class.getSimpleName(), "Error closing thumbnail", e222);
                        }
                    }
                    try {
                        throw th;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                if (out != null) {
                    out.close();
                }
                try {
                    throw th;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        } catch (IOException e4) {
            e222 = e4;
            Log.e(LocalStorageProvider.class.getSimpleName(), "Error writing thumbnail", e222);
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }
        return null;
    }

    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        if (projection == null) {
            projection = DEFAULT_DOCUMENT_PROJECTION;
        }
        MatrixCursor result = new MatrixCursor(projection);
        for (File file : new File(parentDocumentId).listFiles()) {
            if (!file.getName().startsWith(".")) {
                includeFile(result, file);
            }
        }
        return result;
    }

    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        if (projection == null) {
            projection = DEFAULT_DOCUMENT_PROJECTION;
        }
        MatrixCursor result = new MatrixCursor(projection);
        includeFile(result, new File(documentId));
        return result;
    }

    private void includeFile(MatrixCursor result, File file) throws FileNotFoundException {
        int flags;
        int i = 0;
        RowBuilder row = result.newRow();
        row.add("document_id", file.getAbsolutePath());
        row.add("_display_name", file.getName());
        String mimeType = getDocumentType(file.getAbsolutePath());
        row.add("mime_type", mimeType);
        if (file.canWrite()) {
            if (mimeType.equals("vnd.android.document/directory")) {
                i = 8;
            }
            flags = i | 6;
        } else {
            flags = 0;
        }
        if (mimeType.startsWith("image/")) {
            flags |= 1;
        }
        row.add("flags", Integer.valueOf(flags));
        row.add("_size", Long.valueOf(file.length()));
        row.add("last_modified", Long.valueOf(file.lastModified()));
    }

    public String getDocumentType(String documentId) throws FileNotFoundException {
        File file = new File(documentId);
        if (file.isDirectory()) {
            return "vnd.android.document/directory";
        }
        int lastDot = file.getName().lastIndexOf(46);
        if (lastDot >= 0) {
            String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(lastDot + 1));
            if (mime != null) {
                return mime;
            }
        }
        return "application/octet-stream";
    }

    public void deleteDocument(String documentId) throws FileNotFoundException {
        new File(documentId).delete();
    }

    public ParcelFileDescriptor openDocument(String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {
        File file = new File(documentId);
        if (mode.indexOf(119) != -1) {
            return ParcelFileDescriptor.open(file, 805306368);
        }
        return ParcelFileDescriptor.open(file, 268435456);
    }

    public boolean onCreate() {
        return true;
    }
}
