package com.app.paddycameraeditior.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.analytics.AnalyticBasic;
import com.app.paddycameraeditior.bitmap.BitmapResizer;
import com.app.paddycameraeditior.collagelib.CollageActivity;
import com.app.paddycameraeditior.collagelib.CollageHelper;
import com.app.paddycameraeditior.gallerylib.GalleryFragment;
import com.app.paddycameraeditior.imagesavelib.ImageLoader;
import com.app.paddycameraeditior.mirror.MirrorNewActivity;
import com.app.paddycameraeditior.sticker.Utility;
import com.app.paddycameraeditior.utils.Constants;
import com.app.paddycameraeditior.utils.PermissionChecker;
import com.app.paddycameraeditior.utils.Toaster;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "ResizePhotoApp";
    private static final int REQUEST_GET_CAMERA = 0;
    private static final int REQUEST_GET_GALALRY = 1;
    private static final int SELECT_IMAGE_SQUARE = 5;
    private static final String TAG = MainActivity.class.getName();
    private static final int TAKE_PICTURE_COLLAGE = 42;
    private Uri fileUri;
    private LinearLayout mGalleryLayout, mCameraLayout, mCollegeLayout, mMirrorLayout, mBlurLayout, mRateLayout;
    private Animation animation;
    private LinearLayout top_holder;
    private LinearLayout bottom_holder;
    private boolean click_status = true;
    private PermissionChecker mPermissionChecker;
    private String CurrentSelectionTab = "";
    private RelativeLayout mMainLayout;
    private GalleryFragment galleryFragment;
    private ImageLoader imageLoader;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler;
    private Runnable mRunnable;

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionChecker = new PermissionChecker(this);
        setContentView(R.layout.activity_main);
        findViewbyIds();

        try {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageLoader = new ImageLoader(this);
        imageLoader.setListener(new ImageLoader.ImageLoaded() {
            @Override
            public void callFileSizeAlertDialogBuilder() {
                fileSizeAlertDialogBuilder();
            }
        });
        mRunnable = new C07252();

        AnalyticBasic.hitGoogleAnalytics(this, MainActivity.class.getName());
    }

    private void findViewbyIds() {
        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mGalleryLayout = (LinearLayout) findViewById(R.id.layout_gallery);
        mCameraLayout = (LinearLayout) findViewById(R.id.layout_camera);
        mCollegeLayout = (LinearLayout) findViewById(R.id.layout_college);
        mMirrorLayout = (LinearLayout) findViewById(R.id.layout_mirror);
        mBlurLayout = (LinearLayout) findViewById(R.id.layout_blur);
        mRateLayout = (LinearLayout) findViewById(R.id.layout_rate);
        top_holder = (LinearLayout) findViewById(R.id.top_holder);
        bottom_holder = (LinearLayout) findViewById(R.id.bottom_holder);

        mGalleryLayout.setOnClickListener(this);
        mCameraLayout.setOnClickListener(this);
        mCollegeLayout.setOnClickListener(this);
        mMirrorLayout.setOnClickListener(this);
        mBlurLayout.setOnClickListener(this);
        mRateLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (mGalleryLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.GALLERY;
            displayGallery();
        }
        if (mCameraLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.CAMERA;
            displayCamera();
        }
        if (mCollegeLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.COLLAGE;
            this.galleryFragment = CollageHelper.addGalleryFragment(this, R.id.colmir_gallery_fragment_container, null, false);
        }
        if (mMirrorLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.MIRROR;
            displayMirror();
        }
        if (mBlurLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.BLUR;
            this.galleryFragment = CollageHelper.addGalleryFragment(this, R.id.colmir_gallery_fragment_container, null, false);
            this.galleryFragment.setCollageSingleMode(true);
        }
        if (mRateLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.RATE;
        }
    }

    private void fileSizeAlertDialogBuilder() {
        Point p = BitmapResizer.decodeFileSize(new File(imageLoader.selectedImagePath), Utility.maxSizeForDimension(this, 1, 1500.0f));
        if (p == null || p.x != -1) {
            startShaderActivity();
        } else {
            startShaderActivity();
        }
    }

    private void startShaderActivity() {
        Intent shaderIntent = null;
        int maxSize = Utility.maxSizeForDimension(this, 1, 1500.0f);
        if (CurrentSelectionTab == Constants.CurrentFunction.MIRROR) {
            shaderIntent = new Intent(getApplicationContext(), MirrorNewActivity.class);
        } else if (CurrentSelectionTab == Constants.CurrentFunction.GALLERY || CurrentSelectionTab == Constants.CurrentFunction.CAMERA) {
            shaderIntent = new Intent(getApplicationContext(), SquareActivity.class);
        }

        shaderIntent.putExtra("selectedImagePath", this.imageLoader.selectedImagePath);
        shaderIntent.putExtra("isSession", false);
        shaderIntent.putExtra("MAX_SIZE", maxSize);
        Utility.logFreeMemory(this);
        startActivityForResult(shaderIntent, 422);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayGallery() {
        // Check dynamic permission for API 6
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestGallaryPermission();
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_GALALRY);
        }
    }

    private void displayMirror() {
        // Check dynamic permission for API 6
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestGallaryPermission();
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), Constants.REQUEST_MIRROR);
        }
    }

    /**
     * request permission for google accounts
     */
    private void requestGetAccountsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {
            Snackbar.make(mMainLayout, R.string.camera_permission_required_msg,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.okay_text), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.GET_ACCOUNTS},
                                    REQUEST_GET_CAMERA);
                        }
                    })
                    .show();
        } else {

            // GET_ACCOUNTS permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_GET_CAMERA);
        }
    }

    /**
     * request permission for google accounts
     */
    private void requestGallaryPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(mMainLayout, R.string.galary_permission_required_msg,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.okay_text), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_GET_GALALRY);
                        }
                    })
                    .show();
        } else {

            // GET_ACCOUNTS permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_GET_GALALRY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_GET_CAMERA) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for contacts permission.
            Log.i(TAG, "Received response for Get Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mMainLayout, R.string.camera_permission_granted_msg,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mMainLayout, R.string.camera_permission_not_granted_msg,
                        Snackbar.LENGTH_SHORT).show();

            }
            // END_INCLUDE(permission_result)

        } else if (requestCode == REQUEST_GET_GALALRY) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for contacts permission.
            Log.i(TAG, "Received response for Get gallery permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mMainLayout, "Gallery Permission granted,",
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mMainLayout, "Gallery Permission not granted.",
                        Snackbar.LENGTH_SHORT).show();
            }
            // END_INCLUDE(permission_result)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void displayCamera() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestGetAccountsPermission();
        } else {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestGallaryPermission();
            } else {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    Uri imageUri = getImageUri();
                    intent.putExtra("output", imageUri);
                    Log.e("is imageUri null xx", String.valueOf(imageUri == null));
                    startActivityForResult(intent, REQUEST_GET_CAMERA);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "There is no Camera app to handle this request!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        flyIn();
        super.onStart();
    }

    @Override
    protected void onStop() {
        overridePendingTransition(0, 0);
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_GET_CAMERA && resultCode == RESULT_OK) {
                Uri selectedImage = getImageUri();
                imageLoader.selectedImagePath = selectedImage.getPath();
                if (this.imageLoader.selectedImagePath != null) {
                    if (this.imageLoader.selectedImagePath != null && BitmapResizer.decodeFileSize(new File(this.imageLoader.selectedImagePath), Utility.maxSizeForDimension(this, 1, 1500.0f)) != null) {
                        startShaderActivity();
                    }
//
                }
            } else if (resultCode == RESULT_OK && requestCode == REQUEST_GET_GALALRY) {
                try {
                    this.imageLoader.getImageFromIntent(data);
                    Utility.decodeImageFileSize(imageLoader.selectedImagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toaster.make(getApplicationContext(), R.string.error_img_not_found);
                }
            } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_MIRROR) {
                try {
                    imageLoader.getImageFromIntent(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toaster.make(getApplicationContext(), R.string.error_img_not_found);
                }

            } else if (requestCode == PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE) {
                if (!mPermissionChecker.isRequiredPermissionGranted()) {
                    Toast.makeText(this, "Required permission is not granted. Please restart the app and grant required permission.", Toast.LENGTH_LONG).show();
                } else {

                }
            } else if (requestCode == TAKE_PICTURE_COLLAGE && resultCode == RESULT_OK) {
                String path = getImageUri().getPath();
                if (path != null) {
                    Intent intent = new Intent(this, CollageActivity.class);
                    intent.putExtra("selected_image_path", path);
                    startActivity(intent);
                }
            } else if (requestCode == this.SELECT_IMAGE_SQUARE && resultCode == -1) {
                this.imageLoader.getImageFromIntent(data);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "pic.jpg"));
    }

    private void flyOut(final String method_name) {
        if (click_status) {
            click_status = false;

            animation = AnimationUtils.loadAnimation(this, R.anim.holder_top_back);
            top_holder.startAnimation(animation);

            animation = AnimationUtils.loadAnimation(this, R.anim.holder_bottom_back);
            bottom_holder.startAnimation(animation);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    callMethod(method_name);
                }
            });
        }
    }

    private void callMethod(String method_name) {
        if (method_name.equals("finish")) {
            overridePendingTransition(0, 0);
            finish();
        } else {
            try {
                Method method = getClass().getDeclaredMethod(method_name);
                method.invoke(this, new Object[]{});
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        GalleryFragment galleryFragment = CollageHelper.getGalleryFragment(this);
        if (galleryFragment != null && galleryFragment.isVisible()) {
            galleryFragment.onBackPressed();
        } else if (!checkDoubleClickWhenExit()) {
            //finish();
            backButtonAlertBuilder();

        } else if (this.doubleBackToExitPressedOnce) {
            //finish();
            backButtonAlertBuilder();
        } else {
            this.doubleBackToExitPressedOnce = true;
            this.mHandler = new Handler();
            this.mHandler.postDelayed(this.mRunnable, 2000);
        }
    }

    private boolean checkDoubleClickWhenExit() {
        return false;
    }

    private void flyIn() {
        click_status = true;
        animation = AnimationUtils.loadAnimation(this, R.anim.holder_top);
        top_holder.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.holder_bottom);
        bottom_holder.startAnimation(animation);
    }

    private void backButtonAlertBuilder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit application?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               // flyOut("finish");
                dialog.dismiss();
               finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    class C07252 implements Runnable {
        C07252() {
        }

        public void run() {
            MainActivity.this.doubleBackToExitPressedOnce = false;
        }
    }
}
