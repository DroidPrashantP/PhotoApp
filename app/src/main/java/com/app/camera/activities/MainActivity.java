package com.app.camera.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.camera.R;
import com.app.camera.utils.Constants;
import com.app.camera.utils.PermissionChecker;
import com.app.camera.utils.Toaster;
import com.app.camera.utils.UriToUrl;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Uri imageUri;
    LinearLayout mGalleryLayout, mCameraLayout,mCollegeLayout,mMirrorLayout,mBlurLayout,mRateLayout;
    private Animation animation;
    private LinearLayout top_holder;
    private LinearLayout bottom_holder;
    private boolean click_status = true;
    private PermissionChecker mPermissionChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionChecker = new PermissionChecker(this);
        setContentView(R.layout.activity_main);
        findViewbyIds();

    }

    private void findViewbyIds() {
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
        if (!mPermissionChecker.isRequiredPermissionGranted()) {
            Intent intent = mPermissionChecker.createRequiredPermissionIntent();
            startActivityForResult(intent, PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE);
        } else {
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
//                Intent intent = new Intent();
//                intent.setType("image/jpeg");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, Constants.REQUEST_GALLERY);
//            } else {
//                Toaster.make(getApplicationContext(), R.string.no_media);
//            }
            // Create intent to Open Image applications like Gallery, Google Photos
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
//            view.setType("image/*");
//            view.setAction("android.intent.action.GET_CONTENT");
//            startActivityForResult(Intent.createChooser(view, "Select Picture"), SELECT_IMAGE_SQUARE);
//            startActivityForResult(galleryIntent, Constants.REQUEST_GALLERY);

            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), Constants.REQUEST_GALLERY);
        }
    }

    private void displayCamera() {
        // Check dynamic permission for API 6
        if (!mPermissionChecker.isRequiredPermissionGranted()) {
            Intent intent = mPermissionChecker.createRequiredPermissionIntent();
            startActivityForResult(intent, PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE);
        } else {
            imageUri = getOutputMediaFile();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, Constants.REQUEST_CAMERA);
        }
    }

    private Uri getOutputMediaFile(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Camera Pro");
        values.put(MediaStore.Images.Media.DESCRIPTION, "www.appsroid.org");
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void displayPhotoActivity(int source_id) {
        Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
        intent.setData(imageUri);
        startActivity(intent);
        overridePendingTransition(0, 0);
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
            if (requestCode == Constants.REQUEST_CAMERA) {
                try {
                    if (resultCode == RESULT_OK) {
                        if (data!= null) {
                            displayPhotoActivity(1);
                        }
                    } else {
                        UriToUrl.deleteUri(getApplicationContext(), imageUri);
                    }
                } catch (Exception e) {
                    Toaster.make(getApplicationContext(), R.string.error_img_not_found);
                }
            } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_GALLERY) {
                try {
                    imageUri = data.getData();
                    if (imageUri!= null) {
                        displayPhotoActivity(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toaster.make(getApplicationContext(), R.string.error_img_not_found);
                }
            }else if (requestCode == PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE) {
                if (!mPermissionChecker.isRequiredPermissionGranted()) {
                    Toast.makeText(this, "Required permission is not granted. Please restart the app and grant required permission.", Toast.LENGTH_LONG).show();
                } else {

                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if (mGalleryLayout == v){
            displayGallery();
        }
        if (mCameraLayout == v){
            displayCamera();
        }
        if (mCollegeLayout == v){
            displayGallery();
        }
        if (mMirrorLayout == v){
            displayGallery();
        }
        if (mBlurLayout == v){
            displayGallery();
        }
        if (mRateLayout == v){

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
                method.invoke(this, new Object[] {});
            } catch (Exception e) {}
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

}
