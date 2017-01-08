package com.app.camera.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.camera.AppController;
import com.app.camera.R;
import com.app.camera.bitmap.BitmapLoader;
import com.app.camera.cropImages.CropImageView;
import com.app.camera.utils.Constants;
import com.app.camera.utils.Toaster;
import com.app.camera.utils.UriToUrl;


public class CropActivity extends AppCompatActivity {

    private String imageUrl;
    private Uri imageUri;
    // Views ///////////////////////////////////////////////////////////////////////////////////////
    private CropImageView mCropView;
    private RelativeLayout mRootLayout;

    // Image file index(1 ~ 5)
    private int mImageIndex = 5;

    // Bundle key for Save/Restore state ///////////////////////////////////////////////////////////
    private static final String KEY_IMG_INDEX = "img_index";
    private int source_id;

    // Lifecycle Method ////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        // bind Views
        findViews();
        initiView();
    }

    private void initiView() {
        source_id = getIntent().getExtras().getInt(Constants.EXTRA_KEY_IMAGE_SOURCE);
        imageUri = getIntent().getData();
        new BitmapWorkerTask().execute();
//        Originalpath = intent.getExtras().getString("Pic_url");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_IMG_INDEX, mImageIndex);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mImageIndex = savedInstanceState.getInt(KEY_IMG_INDEX);
    }

    // Handle button event /////////////////////////////////////////////////////////////////////////

    private final View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.crop_layout:
                    Log.e("ResultCode", "crop");
                    ((AppController) getApplication()).cropped = mCropView.getCroppedBitmap();
                    Intent intent = new Intent ();
                    intent.putExtra("data","crop");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    break;
                case R.id.closeScreen:
                    finish();
                    break;
                case R.id.buttonFitImage:
                    mCropView.setCropMode(CropImageView.CropMode.RATIO_FIT_IMAGE);
                    break;
                case R.id.button1_1:
                    mCropView.setCustomRatio(1, 1);
                    break;
                case R.id.button2_1:
                    mCropView.setCustomRatio(2, 1);
                    break;
                case R.id.button1_2:
                    mCropView.setCustomRatio(1, 2);
                    break;
                case R.id.button3_2:
                    mCropView.setCustomRatio(3, 2);
                    break;
                case R.id.button2_3:
                    mCropView.setCustomRatio(2, 3);
                    break;
                case R.id.button4_3:
                    mCropView.setCustomRatio(4, 3);
                    break;
                case R.id.button3_4:
                    mCropView.setCustomRatio(3, 4);
                    break;
                case R.id.button4_5:
                    mCropView.setCustomRatio(4, 5);
                    break;
                case R.id.button5_7:
                    mCropView.setCustomRatio(5, 7);
                    break;
                case R.id.button16_9:
                    mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
                    break;
                case R.id.buttonCircle:
                    mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
                    break;
                case R.id.buttonRotateImage:
                    mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                    break;
            }
        }
    };

    // Bind views //////////////////////////////////////////////////////////////////////////////////

    private void findViews() {
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        findViewById(R.id.crop_layout).setOnClickListener(btnListener);
        findViewById(R.id.buttonFitImage).setOnClickListener(btnListener);
        findViewById(R.id.button1_1).setOnClickListener(btnListener);
        findViewById(R.id.button1_2).setOnClickListener(btnListener);
        findViewById(R.id.button2_1).setOnClickListener(btnListener);
        findViewById(R.id.button2_3).setOnClickListener(btnListener);
        findViewById(R.id.button3_2).setOnClickListener(btnListener);
        findViewById(R.id.button3_4).setOnClickListener(btnListener);
        findViewById(R.id.button4_3).setOnClickListener(btnListener);
        findViewById(R.id.button4_5).setOnClickListener(btnListener);
        findViewById(R.id.button5_7).setOnClickListener(btnListener);
        findViewById(R.id.button16_9).setOnClickListener(btnListener);
        findViewById(R.id.buttonRotateImage).setOnClickListener(btnListener);
        findViewById(R.id.buttonCircle).setOnClickListener(btnListener);
        findViewById(R.id.closeScreen).setOnClickListener(btnListener);
        mRootLayout = (RelativeLayout) findViewById(R.id.layout_root);
    }

    // Switch image files //////////////////////////////////////////////////////////////////////////

    private void incrementImageIndex() {
        mImageIndex++;
        if (mImageIndex > 5) mImageIndex -= 5;
    }

    public Bitmap getImageForIndex(int index) {
        String fileName = "sample" + index;
        int resId = getResources().getIdentifier(fileName, "mipmap", getPackageName());
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    private class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
        DisplayMetrics metrics;
        BitmapLoader bitmapLoader;

        public BitmapWorkerTask() {
            metrics = getResources().getDisplayMetrics();
            imageUrl = UriToUrl.get(getApplicationContext(), imageUri);
            bitmapLoader = new BitmapLoader();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Void... arg0) {
            try {
                return bitmapLoader.load(getApplicationContext(), new int[]{metrics.widthPixels, metrics.heightPixels}, imageUrl);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mCropView.setImageBitmap(bitmap);
            } else {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }


}
