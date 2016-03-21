package com.app.camera.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.app.camera.AppController;
import com.app.camera.R;
import com.app.camera.bitmap.BitmapResizer;
import com.app.camera.collagelib.CollageActivity;
import com.app.camera.collagelib.CollageHelper;
import com.app.camera.gallerylib.GalleryFragment;
import com.app.camera.imagesavelib.ImageLoader;
import com.app.camera.mirror.MirrorNewActivity;
import com.app.camera.sticker.Utility;
import com.app.camera.utils.Constants;
import com.app.camera.utils.PermissionChecker;
import com.app.camera.utils.Toaster;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "ResizePhotoApp";
    private static final int REQUEST_GET_CAMERA = 0;
    private static final int REQUEST_GET_GALALRY = 1;
    private static final int REQUEST_GET_MIRROR = 2;
    private static final String TAG = MainActivity.class.getName();
    private static final String FILES_AUTHORITY = "file";
    private static final int TAKE_PICTURE_COLLAGE = 42;

    private Uri fileUri;
    public static final int REQUEST_CODE = 1;
    private Uri imageUri;
    private LinearLayout mGalleryLayout, mCameraLayout, mCollegeLayout, mMirrorLayout, mBlurLayout, mRateLayout;
    private Animation animation;
    private LinearLayout top_holder;
    private LinearLayout bottom_holder;
    private boolean click_status = true;
    private PermissionChecker mPermissionChecker;
    private String CurrentSelectionTab = "";
    private RelativeLayout mMainLayout;
    private GalleryFragment galleryFragment;
    private InterstitialAd interstitial;
    private ImageLoader imageLoader;
    private int SELECT_IMAGE_SQUARE = 5;

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
        } else if (CurrentSelectionTab == Constants.CurrentFunction.RATE) {
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
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), Constants.REQUEST_GALLERY);
        }
    }

    private void displayMirror() {
        // Check dynamic permission for API 6
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestGallaryPermission();
        } else {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction("android.intent.action.GET_CONTENT");
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
            Snackbar.make(mMainLayout, R.string.camera_permission_required_msg,
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
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

    private Uri getOutputMediaFile() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Camera Pro");
        values.put(MediaStore.Images.Media.DESCRIPTION, "www.appsroid.org");
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void displayPhotoActivity(int source_id) {
        if (CurrentSelectionTab.equalsIgnoreCase(Constants.CurrentFunction.CAMERA) || CurrentSelectionTab.equalsIgnoreCase(Constants.CurrentFunction.GALLERY)) {
            Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
            intent.setData(fileUri);
            startActivity(intent);
//            int maxSize = Utility.maxSizeForDimension(this, 1, 1500.0f);
//            Intent  shaderIntent = new Intent(getApplicationContext(), SquareActivity.class);
//            shaderIntent.putExtra("selectedImagePath", fileUri.getPath());
//            shaderIntent.putExtra("isSession", false);
//            shaderIntent.putExtra("MAX_SIZE", maxSize);
//            Utility.logFreeMemory(this);
//            startActivity(shaderIntent);
            overridePendingTransition(0, 0);
        } else if (CurrentSelectionTab.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
            Intent intent = new Intent(getApplicationContext(), BlurActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
            intent.setData(fileUri);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (CurrentSelectionTab.equalsIgnoreCase(Constants.CurrentFunction.MIRROR)) {
            Intent intent = new Intent(getApplicationContext(), MirrorActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
            intent.setData(fileUri);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
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
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage();
                    displayPhotoActivity(1);
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }
            } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_GALLERY) {
                try {
                    fileUri = data.getData();
                    if (fileUri != null) {
                        previewCapturedImage();
                        displayPhotoActivity(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toaster.make(getApplicationContext(), R.string.error_img_not_found);
                }
            } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_MIRROR) {
                try {
//                    fileUri = data.getData();
//                    if (fileUri!= null) {
//                        previewCapturedImage();
//                        displayPhotoActivity(3);
//                    }
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
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "pic.jpg"));
    }

    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            ((AppController) getApplication()).MainBitmap = bitmap;

            if (bitmap != null) {
                Log.e("Got0", "Bitmap");
            } else {
                Log.e("Got0", "null");
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
            displayGallery();
        }
        if (mRateLayout == v) {
            CurrentSelectionTab = Constants.CurrentFunction.RATE;
            Toast.makeText(this, "Comming soon", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE_SQUARE);
        }
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
        flyOut("finish");
        super.onBackPressed();
    }

    private void flyIn() {
        click_status = true;

        animation = AnimationUtils.loadAnimation(this, R.anim.holder_top);
        top_holder.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.holder_bottom);
        bottom_holder.startAnimation(animation);

    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

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


}
