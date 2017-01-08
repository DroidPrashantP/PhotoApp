package com.app.camera.canvastext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.camera.R;
import com.app.camera.colorPicker.RainbowPickerDialog;
import com.app.camera.common_lib.OnItemSelected;

import java.util.ArrayList;

public class CustomRelativeLayout extends RelativeLayout implements OnItemSelected, View.OnClickListener {
    private static final String TAG = "CustomRelativeLayout";
    ApplyTextInterface applyListener;
    ArrayList<CanvasTextView> canvasTextViewList;
    Context context;
    int currentCanvasTextIndex;
    LayoutParams levelParams;
    RelativeLayout mainLayout;
    public OnClickListener onClickListener;
    Bitmap removeBitmap;
    RemoveTextListener removeTextListener;
    Bitmap scaleBitmap;
    SingleTap singleTapListener;
    ArrayList<TextData> textDataList;
    ArrayList<TextData> textDataListOriginal;
    ViewSelectedListener viewSelectedListner;

    @Override
    public void itemSelected(int color) {
        if (!CustomRelativeLayout.this.canvasTextViewList.isEmpty()) {
            ((CanvasTextView) CustomRelativeLayout.this.canvasTextViewList.get(CustomRelativeLayout.this.currentCanvasTextIndex)).setTextColor(color);
        }
    }

    public void onClick(View v) {
        CustomRelativeLayout.this.hideSoftKeyboard((Activity) CustomRelativeLayout.this.context);
        int id = v.getId();
        if (id == R.id.button_text_color) {
            RainbowPickerDialog dialog = new RainbowPickerDialog(CustomRelativeLayout.this.context);
            dialog.setListener(this);
            dialog.show();
        } else if (id == R.id.button_apply_action) {
            int i = 0;
            while (i < CustomRelativeLayout.this.textDataList.size()) {
                if (((TextData) CustomRelativeLayout.this.textDataList.get(i)).message.compareTo(TextData.defaultMessage) == 0) {
                    CustomRelativeLayout.this.textDataList.remove(i);
                    i--;
                }
                i++;
            }
            CustomRelativeLayout.this.applyListener.onOk(CustomRelativeLayout.this.textDataList);
        } else if (id == R.id.button_cancel_action) {
            CustomRelativeLayout.this.textDataList.clear();
            for (int i = 0; i < CustomRelativeLayout.this.textDataListOriginal.size(); i++) {
                CustomRelativeLayout.this.textDataList.add((TextData) CustomRelativeLayout.this.textDataListOriginal.get(i));
            }
            CustomRelativeLayout.this.applyListener.onCancel();
        } else if (id == R.id.button_add_text_action) {
            CustomRelativeLayout.this.addTextView(null);
        }
    }

    public interface RemoveTextListener {
        void onRemove();
    }

    class RemoveText implements RemoveTextListener {
        RemoveText() {
        }

        public void onRemove() {
            if (!CustomRelativeLayout.this.canvasTextViewList.isEmpty()) {
                CanvasTextView canvasTextView = (CanvasTextView) CustomRelativeLayout.this.canvasTextViewList.get(CustomRelativeLayout.this.currentCanvasTextIndex);
                CustomRelativeLayout.this.mainLayout.removeView(canvasTextView);
                CustomRelativeLayout.this.canvasTextViewList.remove(canvasTextView);
                CustomRelativeLayout.this.textDataList.remove(canvasTextView.textData);
                CustomRelativeLayout.this.currentCanvasTextIndex = CustomRelativeLayout.this.canvasTextViewList.size() - 1;
                if (!CustomRelativeLayout.this.canvasTextViewList.isEmpty()) {
                    ((CanvasTextView) CustomRelativeLayout.this.canvasTextViewList.get(CustomRelativeLayout.this.currentCanvasTextIndex)).setTextSelected(true);
                }
            }
        }
    }

    class ViewSelector implements ViewSelectedListener {
        ViewSelector() {
        }

        public void setSelectedView(CanvasTextView cvt) {
            CustomRelativeLayout.this.currentCanvasTextIndex = CustomRelativeLayout.this.canvasTextViewList.indexOf(cvt);
            for (int i = 0; i < CustomRelativeLayout.this.canvasTextViewList.size(); i++) {
                if (CustomRelativeLayout.this.currentCanvasTextIndex != i) {
                    ((CanvasTextView) CustomRelativeLayout.this.canvasTextViewList.get(i)).setTextSelected(false);
                }
            }
        }
    }

    void loadBitmaps() {
        if (this.removeBitmap == null || this.removeBitmap.isRecycled()) {
            this.removeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.close);
        }
        if (this.scaleBitmap == null || this.scaleBitmap.isRecycled()) {
            this.scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_accept);
        }
    }

    public CustomRelativeLayout(Context c, ArrayList<TextData> textDataListParam, Matrix canvasMatrix, SingleTap l) {
        super(c);
        int i;
        this.currentCanvasTextIndex = 0;
        this.removeTextListener = new RemoveText();
        this.viewSelectedListner = new ViewSelector();
        this.context = c;
        this.singleTapListener = l;
        loadBitmaps();
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_canvas, this);
        this.mainLayout = (RelativeLayout) findViewById(R.id.canvas_relative_layout);
        this.levelParams = new LayoutParams( LayoutParams.MATCH_PARENT,  LayoutParams.MATCH_PARENT);
        this.levelParams.addRule(15,-1);
        this.levelParams.addRule(14, -1);
        this.canvasTextViewList = new ArrayList();
        this.textDataList = new ArrayList();
        this.textDataListOriginal = new ArrayList();
        for (i = 0; i < textDataListParam.size(); i++) {
            this.textDataListOriginal.add(new TextData(textDataListParam.get(i)));
            this.textDataList.add(new TextData(textDataListParam.get(i)));
        }
        for (i = 0; i < this.textDataList.size(); i++) {
            CanvasTextView canvasTextView = new CanvasTextView(this.context, this.textDataList.get(i), this.removeBitmap, this.scaleBitmap);
            canvasTextView.setSingleTapListener(this.singleTapListener);
            canvasTextView.setViewSelectedListener(this.viewSelectedListner);
            canvasTextView.setRemoveTextListener(new RemoveText());
            this.mainLayout.addView(canvasTextView, this.levelParams);
            this.canvasTextViewList.add(canvasTextView);
            MyMatrix textMatrix = new MyMatrix();
            textMatrix.set((textDataList.get(i)).imageSaveMatrix);
            textMatrix.postConcat(canvasMatrix);
            canvasTextView.setMatrix(textMatrix);
        }
        if (!this.canvasTextViewList.isEmpty()) {
            canvasTextViewList.get(this.canvasTextViewList.size() - 1).setTextSelected(true);
            this.currentCanvasTextIndex = this.canvasTextViewList.size() - 1;
        }
//        ((LinearLayout) findViewById(R.id.text_header)).bringToFront();
//        ((LinearLayout) findViewById(R.id.text_footer)).bringToFront();
        TextView okButton = (TextView)findViewById(R.id.button_apply_action);
        TextView cancelButton = (TextView)findViewById(R.id.button_cancel_action);
        ImageButton addTextButton = (ImageButton) findViewById(R.id.button_add_text_action);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        addTextButton.setOnClickListener(this);
    }

    public void setSingleTapListener(SingleTap l) {
        this.singleTapListener = l;
    }

    public void setApplyTextListener(ApplyTextInterface l) {
        this.applyListener = l;
    }

    public void addTextView(TextData textData) {
        if (this.textDataList.contains(textData)) {
            Log.e(TAG, "textDataList.contains(textData)");
            canvasTextViewList.get(this.currentCanvasTextIndex).setNewTextData(textData);
            return;
        }
        for (int i = 0; i < this.canvasTextViewList.size(); i++) {
            canvasTextViewList.get(i).setTextSelected(false);
        }
        this.currentCanvasTextIndex = this.canvasTextViewList.size();
        loadBitmaps();
        CanvasTextView canvasTextView = new CanvasTextView(this.context, textData, this.removeBitmap, this.scaleBitmap);
        canvasTextView.setSingleTapListener(this.singleTapListener);
        canvasTextView.setViewSelectedListener(this.viewSelectedListner);
        canvasTextView.setRemoveTextListener(new RemoveText());
        this.canvasTextViewList.add(canvasTextView);
        this.mainLayout.addView(canvasTextView);
        this.textDataList.add(canvasTextView.textData);
        canvasTextView.setTextSelected(true);
        canvasTextView.singleTapped();
    }

    public void addTextDataEx(TextData textData) {
        if (this.textDataList.contains(textData)) {
            Log.e(TAG, "textDataList.contains(textData)");
            ((CanvasTextView) this.canvasTextViewList.get(this.currentCanvasTextIndex)).setNewTextData(textData);
            return;
        }
        for (int i = 0; i < this.canvasTextViewList.size(); i++) {
            ((CanvasTextView) this.canvasTextViewList.get(i)).setTextSelected(false);
        }
        this.currentCanvasTextIndex = this.canvasTextViewList.size();
        CanvasTextView canvasTextView = new CanvasTextView(this.context, textData, this.removeBitmap, this.scaleBitmap);
        canvasTextView.setSingleTapListener(this.singleTapListener);
        canvasTextView.setViewSelectedListener(this.viewSelectedListner);
        canvasTextView.setRemoveTextListener(new RemoveText());
    }

    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard((Activity) this.context);
        return true;
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
