package com.app.camera;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.camera.bitmap.BitmapLoader;
import com.app.camera.utils.CommonActivity;
import com.app.camera.utils.Constants;
import com.app.camera.utils.CustomViews.CustomImageView;
import com.app.camera.utils.CustomViews.MultiTouchListener;
import com.app.camera.utils.CustomViews.MyViewFlipper;
import com.app.camera.utils.FastBlur;
import com.app.camera.utils.Toaster;
import com.app.camera.utils.UriToUrl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

public class PhotoActivity extends Activity implements View.OnClickListener {


    private Animation animation;
    private CustomImageView image_holder;
    private ImageView background_image;
    private Bitmap last_bitmap;
    private int source_id;
    private Uri imageUri;
    private String imageUrl;
    private LinearLayout btn_holder;
    private ImageView undo_btn;
    private ImageView save_btn;
    private ArrayList<String> effects;
    private LinearLayout holder_target;
    private LinearLayout rotation_holder;
    private RelativeLayout toolbox;
    private boolean toolLayoutflag = false;
    private boolean blurLayoutflag = false;
    private RelativeLayout blurSeekbarLayout;
    private MyViewFlipper myViewFlipper;
    int count = 1;
    //Set the radius of the Blur. Supported range 0 < radius <= 25
    private static final float BLUR_RADIUS = 25f;
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    private SeekBar mSeekbar;

    Bitmap originalBitmap = null;
    Bitmap originalBgBitmap = null;
    String Originalpath;
    RelativeLayout mMainContainer;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    int FillCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        image_holder = (CustomImageView) findViewById(R.id.source_image);
        background_image = (ImageView) findViewById(R.id.background_image);
        mMainContainer = (RelativeLayout) findViewById(R.id.container);
        image_holder.setOnTouchListener(new MultiTouchListener());
        findviewbyIds();

        if (savedInstanceState == null) {
            source_id = getIntent().getExtras().getInt(Constants.EXTRA_KEY_IMAGE_SOURCE);
            imageUri = getIntent().getData();
            effects = new ArrayList<String>();
            try {
                loadImage();
            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);

            }
        } else {
            effects = savedInstanceState.getStringArrayList(Constants.KEY_EFFECTS_LIST);
            imageUrl = savedInstanceState.getString(Constants.KEY_URL);
            source_id = savedInstanceState.getInt(Constants.KEY_SOURCE_ID);
            setImage((Bitmap) savedInstanceState.getParcelable(Constants.KEY_BITMAP));
        }

        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slideRightOut = AnimationUtils.loadAnimation(this,R.anim.slide_out_right);
    }

    private void findviewbyIds() {
        rotation_holder = (LinearLayout) findViewById(R.id.rotation_holder);
        rotation_holder.setVisibility(View.INVISIBLE);
        blurSeekbarLayout = (RelativeLayout) findViewById(R.id.blurSeekbarLayout);
//        myViewFlipper = (MyViewFlipper) findViewById(R.id.square_view_flipper);
//        myViewFlipper.setDisplayedChild(2);

        mSeekbar = (SeekBar) findViewById(R.id.blurSeekbar);
        final TextView seekbarTextview = (TextView) findViewById(R.id.seekBarProgressText);
        mSeekbar.setMax(25);
        mSeekbar.setProgress(14);
        seekbarTextview.setText("" + 14);


        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress;
                if (seekBar.getProgress() == 0) {
                    progress = 1;
                } else {
                    progress = seekBar.getProgress();
                }

                seekbarTextview.setText((new StringBuilder()).append(seekBar.getProgress()).toString());
                if (android.os.Build.VERSION.SDK_INT > 17) {
                    Bitmap copyBitmap = originalBgBitmap.copy(originalBgBitmap.getConfig(), true);
                    Bitmap blurredBitmap = blur(copyBitmap, progress);
                    background_image.setImageBitmap(blurredBitmap);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //add here your implementation
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                float f = (float) progress / 4F;
                seekbarTextview.setText((new StringBuilder()).append((int) f).toString());

//                int value = seekBar.getProgress(); //this is the value of the progress bar (1-100)
//                //value = progress; //this should also work
//                String valueString = value + ""; //this is the string that will be put above the slider
//                seekBar.setThumb(writeOnDrawable(R.drawable.blur_48x48,""+ value));
            }
        });
    }

    public BitmapDrawable writeOnDrawable(int drawableId, String text){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED); //Change this if you want other color of text
        paint.setTextSize(20); //Change this if you want bigger/smaller font

        Canvas canvas = new Canvas(bm);
        canvas.drawText(text, 0, bm.getHeight() / 2, paint); //Change the position of the text here

        return new BitmapDrawable(bm);
    }

    private static Bitmap LeftrotateImage(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(-90F);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap1;
    }

    private static Bitmap RightrotateImage(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90F);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap1;
    }

    private void loadImage() throws Exception {
        BitmapWorkerTask bitmaporker = new BitmapWorkerTask();
        bitmaporker.execute();
    }

    @Override
    public void onClick(View v) {
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
                setImage(bitmap);
            } else {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        overridePendingTransition(0, 0);
        super.onStop();
    }


    private Bitmap bitmap() {
        try {
            return ((BitmapDrawable) image_holder.getDrawable()).getBitmap();
        } catch (Exception e) {
            return null;
        }
    }

    private void setImage(Bitmap bitmap) {
        try {
            if (bitmap != null) {
                originalBitmap = bitmap;
                originalBgBitmap = bitmap;
                Bitmap copyBitmap = bitmap;
                Bitmap bmp2 = bitmap.copy(bitmap.getConfig(), true);

                image_holder.setImageBitmap(bitmap);

                if(android.os.Build.VERSION.SDK_INT > 17) {
                    Bitmap blurredBitmap = blur(bmp2, 10);
                    background_image.setImageBitmap(blurredBitmap);
                }else {
                    background_image.setImageBitmap(bitmap);
                }
                //  image_holder.invalidate();
            } else {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);

            }
        } catch (Exception e) {
            Toaster.make(getApplicationContext(), R.string.error_img_not_found);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.KEY_URL, imageUrl);
        outState.putInt(Constants.KEY_SOURCE_ID, source_id);
        outState.putStringArrayList(Constants.KEY_EFFECTS_LIST, effects);
        outState.putParcelable(Constants.KEY_BITMAP, bitmap());
    }

    @SuppressLint("NewApi")
    public Bitmap blur(Bitmap image, int radius) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(radius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    public Bitmap flipImage(Bitmap src, int type) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        // if vertical
        if (type == FLIP_VERTICAL) {
            // y = y * -1
            matrix.preScale(1.0f, -1.0f);
        }
        // if horizonal
        else if (type == FLIP_HORIZONTAL) {
            // x = x * -1
            matrix.preScale(-1.0f, 1.0f);
            // unknown type
        } else {
            return null;
        }

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public Bitmap Fill(Bitmap src, int type) {

        // create new matrix for transformation
        int width = src.getWidth();
        int height = src.getHeight();
        int newWidth = background_image.getWidth();
        int newHeight  = background_image.getHeight();

        // calculate the scale - in this case = 0.4f

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(1.5f, 1.5f );
        // return transformed image
        return Bitmap.createBitmap(src, 0, 0,width, height, matrix, true);
    }

    public void mToolLayoutHandler(View view){
        int id = view.getId();
        if (id == R.id.leftrotateLayout){
            Bitmap bitmap = LeftrotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap());
            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.rightrotateLayout){
            Bitmap bitmap = RightrotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap());
            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.horizontalrotateLayout){
            Bitmap bitmap = flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), FLIP_HORIZONTAL);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.verticalrotateLayout){
            Bitmap bitmap = flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), FLIP_VERTICAL);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.insiderotateLayout){
            try {
                if (FillCount == 0) {
                    Bitmap currentImage = ((BitmapDrawable) image_holder.getDrawable()).getBitmap();
                    Bitmap bitmap = currentImage.copy(currentImage.getConfig(), true);
                    Bitmap copyBitmap = Fill(bitmap, FLIP_VERTICAL);
                    image_holder.setScaleType(ImageView.ScaleType.FIT_XY);
                    image_holder.setImageBitmap(copyBitmap);
                    FillCount++;
                }
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            }
        }
        if (id == R.id.fixrotateLayout){
            Bitmap currentImage = ((BitmapDrawable) image_holder.getDrawable()).getBitmap();
            Bitmap bitmap = currentImage.copy(currentImage.getConfig(), true);
            Bitmap copyBitmap = Fill(bitmap, FLIP_VERTICAL);
            image_holder.setScaleType(ImageView.ScaleType.FIT_XY);
            image_holder.setImageBitmap(copyBitmap);
        }
    }

    public void MainLayoutContainer(View view){
        int id = view.getId();
        if (id == R.id.toolLayout){
            //myViewFlipper.bringToFront();
            if (toolLayoutflag == false) {
                rotation_holder.startAnimation(slideLeftIn);
                rotation_holder.setVisibility(View.VISIBLE);
                blurSeekbarLayout.setVisibility(View.GONE);
                toolLayoutflag = true;
                blurLayoutflag = false;
            } else {
                rotation_holder.setVisibility(View.INVISIBLE);
                blurSeekbarLayout.setVisibility(View.GONE);
                toolLayoutflag = false;
                blurLayoutflag = false;
            }
        }
        if (id == R.id.blurLayout){
            // myViewFlipper.bringToFront();
            if (blurLayoutflag == false) {
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.VISIBLE);
                blurLayoutflag = true;
                toolLayoutflag = false;
            } else {
                rotation_holder.setVisibility(View.INVISIBLE);
                blurSeekbarLayout.setVisibility(View.GONE);
                blurLayoutflag = false;
                toolLayoutflag = false;
            }
        }
        if (id == R.id.fxLayout){

        }
        if (id == R.id.textLayout){

        }
        if (id == R.id.strickerLayout){

        }
        if (id == R.id.filtersLayout){

        }
        if (id == R.id.backgroundLayout){

        }
        if (id == R.id.filtersLayout){

        }
        if (id == R.id.crop_layout){
            try {
                Intent intent = new Intent(this, CropActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
                intent.setData(imageUri);
                startActivityForResult(intent, Constants.CROP_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String SaveImage(Bitmap saveBM) {
        // String root = Environment.getExternalStorageDirectory().toString();
        String[] allsdcardpath = CommonActivity.getStorageDirectories();
        File myDir;
        // if (allsdcardpath[1] != null) {
        // myDir = new File(allsdcardpath[1] + "/DCIM/Kissify"); // sdcard1
        // } else {
        myDir = new File(allsdcardpath[0] + "/DCIM/CameraApp"); // sdcard0
        // }
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "image-" + n + ".jpg";

        // File file = new File(myDir, fname);
        File file = null;
        file = new File(myDir, fname);
        Originalpath = file.getPath();
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            saveBM.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            ContentValues image = new ContentValues();
            image.put(MediaStore.Images.Media.TITLE, "CameraApp");
            image.put(MediaStore.Images.Media.DISPLAY_NAME, fname);
            image.put(MediaStore.Images.Media.DESCRIPTION, "App Image");
            image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
            image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            image.put(MediaStore.Images.Media.ORIENTATION, 0);
            File parent = file.getParentFile();
            image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                    .toLowerCase().hashCode());
            image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                    .toLowerCase());
            image.put(MediaStore.Images.Media.SIZE, file.length());
            image.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            Uri result = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);

//            Toast.makeText(getApplicationContext(),
//                    "File is Saved in  " + file, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast savedToast = Toast.makeText(getApplicationContext(),
                    "not working", Toast.LENGTH_SHORT);
            savedToast.show();
        }
        return file.getAbsolutePath();
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("Log", "failed getViewBitmap(" + v + ")",
                    new RuntimeException());
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public  void SaveImageToGallery(View view){
        Bitmap bitmap = getViewBitmap(mMainContainer);
        SaveImage(bitmap);

        if (Originalpath!= null) {
            Intent intent = new Intent(this, SaveImageActivity.class);
            intent.putExtra("message", "Photo Resize App");
            intent.putExtra("imagePath", Originalpath);
            startActivity(intent);
        }
    }

    public  void CloseScreen(View view){
        showCloseDialog();
    }

    @Override
    public void onBackPressed() {
        showCloseDialog();
    }
    public void showCloseDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to save image?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                Bitmap bitmap = getViewBitmap(mMainContainer);
                SaveImage(bitmap);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CROP_IMAGE) {
            try{
                if (resultCode == RESULT_OK) {
                    // get cropped bitmap from Application
                    Bitmap cropped = ((AppController)getApplication()).cropped;
                    image_holder.setImageBitmap(cropped);
                } else {

                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }
}
