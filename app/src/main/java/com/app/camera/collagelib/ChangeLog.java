package com.app.camera.collagelib;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;
import android.webkit.WebView;

import com.app.camera.R;

public class ChangeLog {
    private static final String EOCL = "END_OF_CHANGE_LOG";
    private static final String TAG = "ChangeLog";
    private static final String VERSION_KEY = "PREFS_VERSION_KEY";
    private final Context context;
    private String lastVersion;
    private Listmode listMode;
    private StringBuffer sb;
    private String thisVersion;

    /* renamed from: com.lyrebirdstudio.collagelib.ChangeLog.1 */
    class C05681 implements OnClickListener {
        C05681() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    }

    /* renamed from: com.lyrebirdstudio.collagelib.ChangeLog.2 */
    class C05692 implements OnClickListener {
        C05692() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            ChangeLog.this.getFullLogDialog().show();
        }
    }

    private enum Listmode {
        NONE,
        ORDERED,
        UNORDERED
    }

    public ChangeLog(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }

    public ChangeLog(Context context, SharedPreferences sp) {
        this.listMode = Listmode.NONE;
        this.sb = null;
        this.context = context;
        this.lastVersion = sp.getString(VERSION_KEY, BuildConfig.FLAVOR);
        Log.d(TAG, "lastVersion: " + this.lastVersion);
        try {
            this.thisVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            this.thisVersion = "?";
            Log.e(TAG, "could not get version name from manifest!");
            e.printStackTrace();
        }
        Log.d(TAG, "appVersion: " + this.thisVersion);
        Editor editor = sp.edit();
        editor.putString(VERSION_KEY, this.thisVersion);
        editor.commit();
    }

    public String getLastVersion() {
        return this.lastVersion;
    }

    public String getThisVersion() {
        return this.thisVersion;
    }

    public boolean firstRun() {
        return !this.lastVersion.equals(this.thisVersion);
    }

    public boolean firstRunEver() {
        return BuildConfig.FLAVOR.equals(this.lastVersion);
    }

    public AlertDialog getLogDialog() {
        return getDialog(false);
    }

    public AlertDialog getFullLogDialog() {
        return getDialog(true);
    }

    private AlertDialog getDialog(boolean full) {
        int i;
        WebView wv = new WebView(this.context);
        wv.setBackgroundColor(0);
        wv.loadDataWithBaseURL(null, getLog(full), "text/html", "UTF-8", null);
        Builder builder = new Builder(this.context);
        Resources resources = this.context.getResources();
        if (full) {
            i = R.string.changelog_full_title;
        } else {
            i = R.string.changelog_title;
        }
        builder.setTitle(resources.getString(i)).setView(wv).setCancelable(false).setPositiveButton(this.context.getResources().getString(R.string.changelog_ok_button), new C05681());
        if (!full) {
            builder.setNegativeButton(R.string.changelog_show_full, new C05692());
        }
        return builder.create();
    }

    public String getLog() {
        return getLog(false);
    }

    public String getFullLog() {
        return getLog(true);
    }

    private String getLog(boolean full) {
        this.sb = new StringBuffer();
//        BufferedReader br = new BufferedReader(new InputStreamReader(this.context.getResources().openRawResource(R.raw.changelog)));
//        boolean advanceToEOVS = false;
//        while (true) {
//            String line = null;
//            try {
//                line = br.readLine();
//            if (line == null) {
//                closeList();
//                br.close();
//                return this.sb.toString();
//            }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            char marker;
//            line = line.trim();
//            if (line.length() > 0) {
//                marker = line.charAt(0);
//            } else {
//                marker = '\u0000';
//            }
//            if (marker == '$') {
//                closeList();
//                String version = line.substring(1).trim();
//                if (!full) {
//                    if (this.lastVersion.equals(version)) {
//                        advanceToEOVS = true;
//                    } else if (version.equals(EOCL)) {
//                        advanceToEOVS = false;
//                    }
//                }
//            } else if (!advanceToEOVS) {
//                switch (marker) {
//                    case R.styleable.Theme_actionModeCopyDrawable /*33*/:
//                        closeList();
//                        this.sb.append("<div class='freetext'>" + line.substring(1).trim() + "</div>\n");
//                        break;
//                    case R.styleable.Theme_actionModeSelectAllDrawable /*35*/:
//                        openList(Listmode.ORDERED);
//                        this.sb.append("<li>" + line.substring(1).trim() + "</li>\n");
//                        break;
//                    case R.styleable.Theme_actionModeFindDrawable /*37*/:
//                        closeList();
//                        this.sb.append("<div class='title'>" + line.substring(1).trim() + "</div>\n");
//                        break;
//                    case R.styleable.Theme_dialogTheme /*42*/:
//                        openList(Listmode.UNORDERED);
//                        this.sb.append("<li>" + line.substring(1).trim() + "</li>\n");
//                        break;
//                    case R.styleable.Theme_buttonBarNegativeButtonStyle /*95*/:
//                        closeList();
//                        this.sb.append("<div class='subtitle'>" + line.substring(1).trim() + "</div>\n");
//                        break;
//                    default:
//                        closeList();
//                        this.sb.append(new StringBuilder(String.valueOf(line)).append("\n").toString());
//                        break;
//                }
//            } else {
//                continue;
//            }
//        }
        //// TODO: 29/2/16  remove sb.tostring
        return sb.toString();
    }

    private void openList(Listmode listMode) {
        if (this.listMode != listMode) {
            closeList();
            if (listMode == Listmode.ORDERED) {
                this.sb.append("<div class='list'><ol>\n");
            } else if (listMode == Listmode.UNORDERED) {
                this.sb.append("<div class='list'><ul>\n");
            }
            this.listMode = listMode;
        }
    }

    private void closeList() {
        if (this.listMode == Listmode.ORDERED) {
            this.sb.append("</ol></div>\n");
        } else if (this.listMode == Listmode.UNORDERED) {
            this.sb.append("</ul></div>\n");
        }
        this.listMode = Listmode.NONE;
    }

    void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }
}
