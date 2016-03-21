package com.app.camera.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.camera.R;
import com.app.camera.utils.CustomViews.MultiTouchListener;
import com.app.camera.utils.MultiTouchController;
import com.app.camera.utils.MultiTouchController.MultiTouchObjectCanvas;

import java.util.ArrayList;

public class RawActivity extends AppCompatActivity {

    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw);
//        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
//        TextData textData = new TextData();
//
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_down_array);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_down_array);
//        CanvasTextView view = new CanvasTextView(this,textData,bitmap1,bitmap2);
//      //  view.addTextView("Prashant");
//        mainLayout.addView(view);


        TextView usertext = new TextView(this);
        usertext.setText("Hi Hello");
        usertext.setTextSize(22);
        usertext.setBackgroundColor(Color.TRANSPARENT);

        Bitmap testB;

        testB = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(testB);
        usertext.layout(0, 0, 80, 100);
        usertext.draw(c);

        // create imageview in layout file and declare here

        ImageView iv = (ImageView) findViewById(R.id.source_image);
        iv.setImageBitmap(testB);
        iv.setOnTouchListener(new MultiTouchListener());
    }


    public class PhotoSortrView extends View implements MultiTouchObjectCanvas<PhotoSortrView.Img> {

        public static final int FLIP_VERTICAL = 1;
        public static final int FLIP_HORIZONTAL = 2;
        public Boolean viewselectionflag;
        public Boolean Rectselectionflag;
        public Boolean Lastselectionflag;
        public Boolean Alphaselectionflag = false;
        public Boolean Flipselectionflag = false;
        public Boolean FlipselectionStopFlag = false;
        public Boolean DragFlag = false;
        public int Lastindex = 0;
        private ArrayList<Img> mTextViewList = new ArrayList<Img>();
        private ArrayList<Integer> IMAGES = new ArrayList<Integer>();

        private MultiTouchController<Img> multiTouchController = new MultiTouchController<Img>(
                this);
        private MultiTouchController.PointInfo currTouchPoint = new MultiTouchController.PointInfo();
        private boolean mShowDebugInfo = true;
        private static final int UI_MODE_ROTATE = 1,
                UI_MODE_ANISOTROPIC_SCALE = 2;
        private int mUIMode = UI_MODE_ROTATE;
        private Paint mLinePaintTouchPointCircle = new Paint();
        private Paint white_paint = new Paint();
        Context context;

        public PhotoSortrView(Context context) {
            super(context);
            this.context = context;
            mLinePaintTouchPointCircle.setColor(Color.RED);
            mLinePaintTouchPointCircle.setStrokeWidth(5);
            mLinePaintTouchPointCircle.setStyle(Paint.Style.STROKE);
            mLinePaintTouchPointCircle.setAntiAlias(true);


            white_paint.setColor(Color.argb(150, 255, 255, 255));
            white_paint.setStyle(Paint.Style.FILL);
            white_paint.setAntiAlias(true);
        }

        public void addTextView(String text) {
            Rectselectionflag = true;
            Lastselectionflag = false;
            Resources res = getResources();
            mTextViewList.add(new Img(text, res));
            loadImages(RawActivity.this);
            invalidate();
        }

        /**
         * Called by activity's onResume() method to load the images
         */
        public void loadImages(Context context) {
            Resources res = context.getResources();
            int n = mTextViewList.size();
            if (n > 0) {
                for (int i = 0; i < n; i++)
                    mTextViewList.get(i).load(res);

            }
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int n = mTextViewList.size();
            if (n > 0) {
                for (int i = 0; i < n; i++)
                    mTextViewList.get(i).draw(canvas, i);
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
                    canvas.drawCircle(xs[i], ys[i], 30 + pressures[i] * 40,
                            mLinePaintTouchPointCircle);
                if (numPoints == 2)
                    canvas.drawLine(xs[0], ys[0], xs[1], ys[1],
                            mLinePaintTouchPointCircle);
            }
        }

        // ---------------------------------------------------------------------------------------------------

        /**
         * Pass touch events to the MT controller
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            return multiTouchController.onTouchEvent(event);
        }

        @Override
        public boolean onDragEvent(DragEvent event) {
            // TODO Auto-generated method stub
            return super.onDragEvent(event);
        }

        /**
         * Get the image that is under the single-touch point, or return null
         * (canceling the drag op) if none
         */
        public Img getDraggableObjectAtPoint(MultiTouchController.PointInfo pt) {
            float x = pt.getX(), y = pt.getY();
            int n = mTextViewList.size();
            for (int i = n - 1; i >= 0; i--) {
                Img im = mTextViewList.get(i);
                if (im.containsPoint(x, y))
                    return im;
            }
            return null;
        }

        /**
         * Select an object for dragging. Called whenever an object is found to
         * be under the point (non-null is returned by
         * getDraggableObjectAtPoint()) and a drag operation is starting. Called
         * with null when drag op ends.
         */
        public void selectObject(Img img, MultiTouchController.PointInfo touchPoint) {
            currTouchPoint.set(touchPoint);
            if (img != null) {
                // Move image to the top of the stack when selected
                int pos = mTextViewList.indexOf(img);
                mTextViewList.remove(img);

                mTextViewList.set(pos, img);

            } else {
                // Called with img == null when drag stops.
            }
            invalidate();
        }

        /**
         * Get the current position and scale of the selected image. Called
         * whenever a drag starts or is reset.
         */
        public void getPositionAndScale(Img img,
                                        MultiTouchController.PositionAndScale objPosAndScaleOut) {
            // FIXME affine-izem (and fix the fact that the anisotropic_scale
            // part requires averaging the two scale factors)
            objPosAndScaleOut.set(img.getCenterX(), img.getCenterY(),
                    (mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0,
                    (img.getScaleX() + img.getScaleY()) / 2,
                    (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0,
                    img.getScaleX(), img.getScaleY(),
                    (mUIMode & UI_MODE_ROTATE) != 0, img.getAngle());

        }

        /**
         * Set the position and scale of the dragged/stretched image.
         */
        public boolean setPositionAndScale(Img img,
                                           MultiTouchController.PositionAndScale newImgPosAndScale, MultiTouchController.PointInfo touchPoint) {
            currTouchPoint.set(touchPoint);
            img.setToughtPoint(touchPoint);
            boolean ok = img.setPos(newImgPosAndScale);
            if (ok)
                invalidate();
            return ok;
        }

        // ----------------------------------------------------------------------------------------------

        class Img {
            private String text;
            private boolean firstLoad;
            private int width, height, displayWidth, displayHeight;
            private float centerX, centerY, scaleX, scaleY, angle;
            private float minX, maxX, minY, maxY;
            private static final float SCREEN_MARGIN = 50;
            public MultiTouchController.PointInfo pointinfo;
            Paint paint = new Paint();

            public Img(String text, Resources res) {
                this.firstLoad = true;
                this.text = text;
                getMetrics(res);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                paint.setTextSize(40);
                paint.setTextScaleX(1.f);
                paint.setAlpha(0);
                paint.setAntiAlias(true);
            }

            private void getMetrics(Resources res) {
                DisplayMetrics metrics = res.getDisplayMetrics();
                // The DisplayMetrics don't seem to always be updated on screen
                // rotate, so we hard code a portrait
                // screen orientation for the non-rotated screen here...
                // this.displayWidth = metrics.widthPixels;
                // this.displayHeight = metrics.heightPixels;
                this.displayWidth = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? Math
                        .max(metrics.widthPixels, metrics.heightPixels) : Math
                        .min(metrics.widthPixels, metrics.heightPixels);
                this.displayHeight = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? Math
                        .min(metrics.widthPixels, metrics.heightPixels) : Math
                        .max(metrics.widthPixels, metrics.heightPixels);
            }

            /**
             * Called by activity's onResume() method to load the images
             */
            public void load(Resources res) {
                getMetrics(res);

                this.width = 100;
                this.height = 100;
                float cx = 0, cy = 0, sx, sy;
                if (firstLoad) {

                    /// get screen size
                    Display display = getWindowManager().getDefaultDisplay();
                    Point screesize = new Point();
                    display.getSize(screesize);
                    int width = screesize.x;
                    int height = screesize.y;
                    ////////////////////////////////


                    if (width > 480 && height > 800) {
                        cx = width / 2;
                        cy = SCREEN_MARGIN + 300;

                    } else if (width < 540 && height < 960) {
                        cx = width / 2;
                        cy = SCREEN_MARGIN + 250;

                    }
                    float sc = 2;
                    sx = sy = sc;
                    firstLoad = false;
                } else {
                    // Reuse position and scale information if it is available
                    // FIXME this doesn't actually work because the whole
                    // activity is torn down and re-created on rotate
                    cx = this.centerX;
                    cy = this.centerY;
                    sx = this.scaleX;
                    sy = this.scaleY;
                    // Make sure the image is not off the screen after a screen
                    // rotation
                    // if (this.maxX < SCREEN_MARGIN)
                    // cx = SCREEN_MARGIN;
                    // else if (this.minX > displayWidth - SCREEN_MARGIN)
                    // cx = displayWidth - SCREEN_MARGIN;
                    // if (this.maxY > SCREEN_MARGIN)
                    // cy = SCREEN_MARGIN;
                    // else if (this.minY > displayHeight - SCREEN_MARGIN)
                    // cy = displayHeight - SCREEN_MARGIN;
                }
                setPos(cx, cy, sx, sy, 0.0f);
            }

            /**
             * Set the position and scale of an image in screen coordinates
             */
            public boolean setPos(MultiTouchController.PositionAndScale newImgPosAndScale) {
                return setPos(
                        newImgPosAndScale.getXOff(),
                        newImgPosAndScale.getYOff(),
                        (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
                                .getScaleX() : newImgPosAndScale.getScale(),
                        (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
                                .getScaleY() : newImgPosAndScale.getScale(),
                        newImgPosAndScale.getAngle());
            }

            /**
             * Set the position and scale of an image in screen coordinates
             */
            private boolean setPos(float centerX, float centerY, float scaleX,
                                   float scaleY, float angle) {
                float ws = (width / 2) * scaleX, hs = (height / 2) * scaleY;
                float newMinX = centerX - ws, newMinY = centerY - hs, newMaxX = centerX
                        + ws, newMaxY = centerY + hs;
                if (newMinX > displayWidth - SCREEN_MARGIN
                        || newMaxX < SCREEN_MARGIN
                        || newMinY > displayHeight - SCREEN_MARGIN
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

            /**
             * Return whether or not the given screen coords are inside this
             * image
             */
            public boolean containsPoint(float scrnX, float scrnY) {
                // FIXME: need to correctly account for image rotation
                return (scrnX >= minX && scrnX <= maxX && scrnY >= minY && scrnY <= maxY);
            }

            public void draw(Canvas canvas, int pos) {
              //  canvas.save();
//                float dx = (maxX + minX) / 2;
//                float dy = (maxY + minY) / 2;
//                canvas.translate(dx, dy);
//                canvas.rotate(angle * 180.0f / (float) Math.PI);
                canvas.drawText("Your text", centerX, centerY, mLinePaintTouchPointCircle);
             //   drawable.draw(canvas);
               // canvas.restore();
//				invalidate();
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

            public void setToughtPoint(MultiTouchController.PointInfo point) {
                this.pointinfo = point;
            }


            public MultiTouchController.PointInfo getTouchPoint() {
                return pointinfo;
            }

        }
    }


}
