package com.app.camera.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.camera.AppController;
import com.app.camera.R;
import com.app.camera.bitmap.BitmapLoader;
import com.app.camera.bitmap.ScalingUtilities;
import com.app.camera.utils.CommonActivity;
import com.app.camera.utils.Constants;
import com.app.camera.utils.CustomViews.CustomImageView;
import com.app.camera.utils.CustomViews.MultiTouchListener;
import com.app.camera.utils.CustomViews.MyViewFlipper;
import com.app.camera.utils.Toaster;
import com.app.camera.utils.UriToUrl;

import com.app.camera.utils.MultiTouchController;
import com.app.camera.utils.MultiTouchController.MultiTouchObjectCanvas;
import com.app.camera.utils.MultiTouchController.PointInfo;
import com.app.camera.utils.MultiTouchController.PositionAndScale;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

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
    //private MultiTouchListener multiTouchListener;

    CustomMultitToughImageview customMultitToughImageview;
    private ArrayList<Uri> mIMAGES = new ArrayList<Uri>();
    RelativeLayout mainImageContainer;
    private boolean isLeftRotate = false;
    private boolean isRightRotate = false;
    private boolean isHorizontalRotate = false;
    private boolean isVerticalRotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        findviewbyIds();
       // multiTouchListener = new MultiTouchListener();

        if (savedInstanceState == null) {
            source_id = getIntent().getExtras().getInt(Constants.EXTRA_KEY_IMAGE_SOURCE);
            imageUri = getIntent().getData();
            effects = new ArrayList<String>();
            try {
                mIMAGES.add(imageUri);
                customMultitToughImageview = new CustomMultitToughImageview(PhotoActivity.this);
                mainImageContainer.addView(customMultitToughImageview);
                //customMultitToughImageview.adddrawable(getRealPathFromURI(this,imageUri),this);
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
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
    }

    public String getRealPathFromURI(Context context, Uri uri) {
        String fileName="unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content")==0)
        {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
            }
        }
        else if (uri.getScheme().compareTo("file")==0)
        {
            fileName = filePathUri.getLastPathSegment().toString();
        }
        else
        {
            fileName = fileName+"_"+filePathUri.getLastPathSegment();
        }
        return fileName;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // customMultitToughImageview.loadImages(this);
    }

    private void findviewbyIds() {
        mainImageContainer = (RelativeLayout) findViewById(R.id.container);
        image_holder = (CustomImageView) findViewById(R.id.source_image);
        background_image = (ImageView) findViewById(R.id.background_image);
        mMainContainer = (RelativeLayout) findViewById(R.id.container);
        image_holder.setOnTouchListener(new MultiTouchListener());

        rotation_holder = (LinearLayout) findViewById(R.id.rotation_holder);
        rotation_holder.setVisibility(View.INVISIBLE);
        blurSeekbarLayout = (RelativeLayout) findViewById(R.id.blurSeekbarLayout);
//        myViewFlipper = (MyViewFlipper) findViewById(R.id.square_view_flipper);
//        myViewFlipper.setDisplayedChild(2);

        blurSection();
    }

    private void blurSection() {
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
                    Bitmap blurredBitmap = CommonActivity.blur(PhotoActivity.this, copyBitmap, progress);
                    background_image.setImageBitmap(blurredBitmap);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                float f = (float) progress / 4F;
                seekbarTextview.setText((new StringBuilder()).append((int) f).toString());
            }
        });

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
//                image_holder.setImageBitmap(bitmap);

                if (android.os.Build.VERSION.SDK_INT > 17) {
                    Bitmap blurredBitmap = CommonActivity.blur(PhotoActivity.this, bmp2, 10);
                    background_image.setImageBitmap(blurredBitmap);
                } else {
                    background_image.setImageBitmap(bitmap);
                }
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

    public Bitmap Fill(Bitmap src) {
        // create new matrix for transformation
        int width = src.getWidth();
        int height = src.getHeight();
        int newWidth = background_image.getWidth();
        int newHeight = background_image.getHeight();
        // calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f);
        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
    }

    public void mToolLayoutHandler(View view) {
        int id = view.getId();
        if (id == R.id.leftrotateLayout) {
            customMultitToughImageview.RotateImage(0);
//            Bitmap bitmap = CommonActivity.RotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 0);
//            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.rightrotateLayout) {
            customMultitToughImageview.RotateImage(1);
//            Bitmap bitmap = CommonActivity.RotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 1);
//            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.horizontalrotateLayout) {
            customMultitToughImageview.RotateImage(2);
//            Bitmap bitmap = CommonActivity.flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 0);
//            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.verticalrotateLayout) {
            customMultitToughImageview.RotateImage(3);
//            Bitmap bitmap = CommonActivity.flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 1);
//            image_holder.setImageBitmap(bitmap);
        }
        if (id == R.id.insiderotateLayout) {
            try {
                if (FillCount == 0) {
                    Bitmap currentImage = ((BitmapDrawable) image_holder.getDrawable()).getBitmap();
                    Bitmap bitmap = currentImage.copy(currentImage.getConfig(), true);
                    Bitmap copyBitmap = Fill(bitmap);
                    image_holder.setScaleType(ImageView.ScaleType.FIT_XY);
                    image_holder.setImageBitmap(copyBitmap);
                    FillCount++;
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        if (id == R.id.fixrotateLayout) {
            Bitmap currentImage = ((BitmapDrawable) image_holder.getDrawable()).getBitmap();
//            Bitmap bitmap = currentImage.copy(currentImage.getConfig(), true);
//            Bitmap copyBitmap = Fill(bitmap);
            Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(currentImage, 600,
                    900, ScalingUtilities.ScalingLogic.FIT);
            //image_holder.setImageBitmap(originalBgBitmap);
        }
    }

    public void MainLayoutContainer(View view) {
        int id = view.getId();
        if (id == R.id.toolLayout) {
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
        if (id == R.id.blurLayout) {
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
        if (id == R.id.fxLayout) {

        }
        if (id == R.id.textLayout) {

        }
        if (id == R.id.strickerLayout) {

        }
        if (id == R.id.filtersLayout) {

        }
        if (id == R.id.backgroundLayout) {

        }
        if (id == R.id.filtersLayout) {

        }
        if (id == R.id.crop_layout) {
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

    public void SaveImageToGallery(View view) {
        Bitmap bitmap = CommonActivity.getViewBitmap(mMainContainer);
        Originalpath = CommonActivity.SaveImage(this, bitmap);

        if (Originalpath != null) {
            Intent intent = new Intent(this, SaveImageActivity.class);
            intent.putExtra("message", "Photo Resize App");
            intent.putExtra("imagePath", Originalpath);
            startActivity(intent);
        }
    }

    public void CloseScreen(View view) {
        showCloseDialog();
    }

    @Override
    public void onBackPressed() {
        showCloseDialog();
    }

    public void showCloseDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to save image?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                Bitmap bitmap = CommonActivity.getViewBitmap(mMainContainer);
                Originalpath = CommonActivity.SaveImage(PhotoActivity.this, bitmap);
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
            try {
                if (resultCode == RESULT_OK) {
                    // get cropped bitmap from Application
                    Bitmap cropped = ((AppController) getApplication()).cropped;
                    image_holder.setImageBitmap(cropped);
                } else {

                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }

    ////////////////////////////////////////// Multitouch Controller ///////////////////////////

    public class CustomMultitToughImageview extends View implements MultiTouchObjectCanvas<CustomMultitToughImageview.Img> {


        private ArrayList<Img> mImages = new ArrayList<Img>();
        private MultiTouchController<Img> multiTouchController = new MultiTouchController<Img>(this);
        private PointInfo currTouchPoint = new PointInfo();
        private boolean mShowDebugInfo = true;
        private static final int UI_MODE_ROTATE = 1, UI_MODE_ANISOTROPIC_SCALE = 2;
        private int mUIMode = UI_MODE_ROTATE;
        private Paint mLinePaintTouchPointCircle = new Paint();

        // ---------------------------------------------------------------------------------------------------

        public CustomMultitToughImageview(Context context) {
            super(context);
            init(context);
            mLinePaintTouchPointCircle.setColor(Color.rgb(249, 149, 203));
            mLinePaintTouchPointCircle.setStrokeWidth(5);
            mLinePaintTouchPointCircle.setStyle(Paint.Style.STROKE);
            mLinePaintTouchPointCircle.setAntiAlias(true);

        }

        private void init(Context context) {
            Resources res = context.getResources();
            for (int i = 0; i < mIMAGES.size(); i++)
                mImages.add(new Img(mIMAGES.get(i), res));

            mLinePaintTouchPointCircle.setColor(Color.YELLOW);
            mLinePaintTouchPointCircle.setStrokeWidth(5);
            mLinePaintTouchPointCircle.setStyle(Paint.Style.STROKE);
            mLinePaintTouchPointCircle.setAntiAlias(true);
            setBackgroundColor(Color.BLACK);
            loadImages(context);
        }

        public void adddrawable(Uri uri, Context ctx) {
            Resources res = ctx.getResources();
            mImages.add(new Img(uri, res));
            loadImages(ctx);
            invalidate();
        }
        /** Called by activity's onResume() method to load the images */
        public void loadImages(Context context) {
            Resources res = context.getResources();
            int n = mImages.size();
            for (int i = 0; i < n; i++)
                mImages.get(i).load(res,mIMAGES.get(i));
        }

        /** Called by activity's onPause() method to free memory used for loading the images */
        public void unloadImages() {
            int n = mImages.size();
            for (int i = 0; i < n; i++)
                mImages.get(i).unload();
        }

        public void RotateImage(int type) {
            Matrix matrix = new Matrix();
            if (type == 0) {
                isLeftRotate = true;
                isRightRotate = false;
                isHorizontalRotate = false;
                isVerticalRotate = false;
            }
            if (type == 1){
                isLeftRotate = false;
                isRightRotate = true;
                isHorizontalRotate = false;
                isVerticalRotate = false;
            }
            if (type == 2){
                isLeftRotate = false;
                isRightRotate = false;
                isHorizontalRotate = true;
                isVerticalRotate = false;
            }
            if (type == 3){
                isLeftRotate = false;
                isRightRotate = false;
                isHorizontalRotate = false;
                isVerticalRotate = true;
            }

          invalidate();
        }

        // ---------------------------------------------------------------------------------------------------

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int n = mImages.size();
            if (mIMAGES.size() > 0) {
                for (int i = 0; i < n; i++)
                    mImages.get(i).draw(canvas);
                if (mShowDebugInfo)
                    drawMultitouchDebugMarks(canvas);
            }
        }

        // ---------------------------------------------------------------------------------------------------

        public void trackballClicked() {
            mUIMode = (mUIMode + 1) % 3;
            invalidate();
        }

        private void drawMultitouchDebugMarks(Canvas canvas) {
            if (currTouchPoint.isDown()) {
                float[] xs = currTouchPoint.getXs();
                float[] ys = currTouchPoint.getYs();
                float[] pressures = currTouchPoint.getPressures();
                int numPoints = Math.min(currTouchPoint.getNumTouchPoints(), 2);
                for (int i = 0; i < numPoints; i++)
                    canvas.drawCircle(xs[i], ys[i], 50 + pressures[i] * 80, mLinePaintTouchPointCircle);
                if (numPoints == 2)
                    canvas.drawLine(xs[0], ys[0], xs[1], ys[1], mLinePaintTouchPointCircle);
            }
        }

        // ---------------------------------------------------------------------------------------------------

        /** Pass touch events to the MT controller */
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return multiTouchController.onTouchEvent(event);
        }

        /** Get the image that is under the single-touch point, or return null (canceling the drag op) if none */
        public Img getDraggableObjectAtPoint(PointInfo pt) {
            float x = pt.getX(), y = pt.getY();
            int n = mImages.size();
            for (int i = n - 1; i >= 0; i--) {
                Img im = mImages.get(i);
                if (im.containsPoint(x, y))
                    return im;
            }
            return null;
        }

        /**
         * Select an object for dragging. Called whenever an object is found to be under the point (non-null is returned by getDraggableObjectAtPoint())
         * and a drag operation is starting. Called with null when drag op ends.
         */
        public void selectObject(Img img, PointInfo touchPoint) {
            currTouchPoint.set(touchPoint);
            if (img != null) {
                // Move image to the top of the stack when selected
                mImages.remove(img);
                mImages.add(img);
            } else {
                // Called with img == null when drag stops.
            }
            invalidate();
        }

        /** Get the current position and scale of the selected image. Called whenever a drag starts or is reset. */
        public void getPositionAndScale(Img img, PositionAndScale objPosAndScaleOut) {
            // FIXME affine-izem (and fix the fact that the anisotropic_scale part requires averaging the two scale factors)
            objPosAndScaleOut.set(img.getCenterX(), img.getCenterY(), (mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0,
                    (img.getScaleX() + img.getScaleY()) / 2, (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0, img.getScaleX(), img.getScaleY(),
                    (mUIMode & UI_MODE_ROTATE) != 0, img.getAngle());
        }

        /** Set the position and scale of the dragged/stretched image. */
        public boolean setPositionAndScale(Img img, PositionAndScale newImgPosAndScale, PointInfo touchPoint) {
            currTouchPoint.set(touchPoint);
            boolean ok = img.setPos(newImgPosAndScale);
            if (ok)
                invalidate();
            return ok;
        }

        // ----------------------------------------------------------------------------------------------

        class Img {
            private Uri imagePath;

            private Drawable drawable;

            private boolean firstLoad;

            private int width, height, displayWidth, displayHeight;

            private float centerX, centerY, scaleX, scaleY, angle;

            private float minX, maxX, minY, maxY;

            private static final float SCREEN_MARGIN = 100;

            public Img(Uri imagepath, Resources res) {
                this.imagePath = imagepath;
                this.firstLoad = true;
                getMetrics(res);
            }

            private void getMetrics(Resources res) {
                DisplayMetrics metrics = res.getDisplayMetrics();
                // The DisplayMetrics don't seem to always be updated on screen rotate, so we hard code a portrait
                // screen orientation for the non-rotated screen here...
                // this.displayWidth = metrics.widthPixels;
                // this.displayHeight = metrics.heightPixels;
                this.displayWidth = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? Math.max(metrics.widthPixels,
                        metrics.heightPixels) : Math.min(metrics.widthPixels, metrics.heightPixels);
                this.displayHeight = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? Math.min(metrics.widthPixels,
                        metrics.heightPixels) : Math.max(metrics.widthPixels, metrics.heightPixels);
            }

            /** Called by activity's onResume() method to load the images */
            public void load(Resources res,Uri uri) {
                getMetrics(res);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    this.drawable = Drawable.createFromStream(inputStream, uri.toString() );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                this.width = drawable.getIntrinsicWidth();
                this.height = drawable.getIntrinsicHeight();
                float cx = 0, cy = 0, sx, sy;
                if (firstLoad) {
//                    cx = SCREEN_MARGIN + (float) (Math.random() * (displayWidth - 2 * SCREEN_MARGIN));
//                    cy = SCREEN_MARGIN + (float) (Math.random() * (displayHeight - 2 * SCREEN_MARGIN));
//                    float sc = (float) (Math.max(displayWidth, displayHeight) / (float) Math.max(width, height) * Math.random() * 0.3 + 0.2);
//                    sx = sy = sc;
//                    firstLoad = false;

                    /// get screen size
                    Display display = getWindowManager().getDefaultDisplay();
                    Point screesize = new Point();
                    display.getSize(screesize);
                    int width = screesize.x;
                    int height = screesize.y;
                    ////////////////////////////////


                    if(width >480 && height >800){
                        cx = width/2;
                        cy = SCREEN_MARGIN + 300;

                    }else if(width <540 && height <960){
                        cx = width/2;
                        cy = SCREEN_MARGIN +250;

                    }
                    float sc = 2;
                    sx = sy = sc;
                    firstLoad = false;
                } else {
                    // Reuse position and scale information if it is available
                    // FIXME this doesn't actually work because the whole activity is torn down and re-created on rotate
                    cx = this.centerX;
                    cy = this.centerY;
                    sx = this.scaleX;
                    sy = this.scaleY;
                    // Make sure the image is not off the screen after a screen rotation
                    if (this.maxX < SCREEN_MARGIN)
                        cx = SCREEN_MARGIN;
                    else if (this.minX > displayWidth - SCREEN_MARGIN)
                        cx = displayWidth - SCREEN_MARGIN;
                    if (this.maxY > SCREEN_MARGIN)
                        cy = SCREEN_MARGIN;
                    else if (this.minY > displayHeight - SCREEN_MARGIN)
                        cy = displayHeight - SCREEN_MARGIN;
                }
                setPos(cx, cy, sx, sy, 0.0f);
            }

            /** Called by activity's onPause() method to free memory used for loading the images */
            public void unload() {
                this.drawable = null;
            }

            /** Set the position and scale of an image in screen coordinates */
            public boolean setPos(PositionAndScale newImgPosAndScale) {
                return setPos(newImgPosAndScale.getXOff(), newImgPosAndScale.getYOff(), (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
                        .getScaleX() : newImgPosAndScale.getScale(), (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale.getScaleY()
                        : newImgPosAndScale.getScale(), newImgPosAndScale.getAngle());
                // FIXME: anisotropic scaling jumps when axis-snapping
                // FIXME: affine-ize
                // return setPos(newImgPosAndScale.getXOff(), newImgPosAndScale.getYOff(), newImgPosAndScale.getScaleAnisotropicX(),
                // newImgPosAndScale.getScaleAnisotropicY(), 0.0f);
            }

            /** Set the position and scale of an image in screen coordinates */
            private boolean setPos(float centerX, float centerY, float scaleX, float scaleY, float angle) {
                float ws = (width / 2) * scaleX, hs = (height / 2) * scaleY;
                float newMinX = centerX - ws, newMinY = centerY - hs, newMaxX = centerX + ws, newMaxY = centerY + hs;
                if (newMinX > displayWidth - SCREEN_MARGIN || newMaxX < SCREEN_MARGIN || newMinY > displayHeight - SCREEN_MARGIN
                        || newMaxY < SCREEN_MARGIN)
                    return false;
                this.centerX = centerX;
                this.centerY = centerY;
                this.scaleX = scaleX;
                this.scaleY = scaleY;
                this.angle = angle;
                this.minX = newMinX;
                this.minY = newMinY;
                this.maxX = newMaxX;
                this.maxY = newMaxY;
                return true;
            }

            /** Return whether or not the given screen coords are inside this image */
            public boolean containsPoint(float scrnX, float scrnY) {
                // FIXME: need to correctly account for image rotation
                return (scrnX >= minX && scrnX <= maxX && scrnY >= minY && scrnY <= maxY);
            }

            public void draw(Canvas canvas) {
                canvas.save();
                float dx = (maxX + minX) / 2;
                float dy = (maxY + minY) / 2;
                drawable.setBounds((int) minX, (int) minY, (int) maxX, (int) maxY);
                canvas.translate(dx, dy);
                if (isLeftRotate) {
                    canvas.rotate(90f);
                }else if (isRightRotate){
                    canvas.rotate(-90f);
                }
                else if (isHorizontalRotate){

                }
                else if (isVerticalRotate){

                }else {
                    canvas.rotate(angle * 180.0f / (float) Math.PI);
                }

                canvas.translate(-dx, -dy);
                drawable.draw(canvas);
                canvas.restore();
            }

            public Drawable getDrawable() {
                return drawable;
            }

            public int getWidth() {
                return width;
            }

            public int getHeight() {
                return height;
            }

            public float getCenterX() {
                return centerX;
            }

            public float getCenterY() {
                return centerY;
            }

            public float getScaleX() {
                return scaleX;
            }

            public float getScaleY() {
                return scaleY;
            }

            public float getAngle() {
                return angle;
            }

            // FIXME: these need to be updated for rotation
            public float getMinX() {
                return minX;
            }

            public float getMaxX() {
                return maxX;
            }

            public float getMinY() {
                return minY;
            }

            public float getMaxY() {
                return maxY;
            }
        }
    }
}
