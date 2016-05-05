package com.app.camera.cropimages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.app.camera.R;
import com.app.camera.activities.CropActivity;

import java.io.File;

public class FragmentCrop extends Fragment {
    private static final String TAG = "FragmentCrop";
    Activity activity;
    Bitmap btm;
    Bundle bundle;
    Context context;
    CropListener cropListener;
    CropView cropView;
    String inputPath;
    Class intentClass;
    boolean isHDR;
    OnClickListener onClickListener;
    String outputPath;
    Button[] ratioButtonList;

    class C05801 implements OnClickListener {
        C05801() {
        }

        public void onClick(View v) {
            FragmentCrop.this.myClickHandler(v);
        }
    }

    public interface CropListener {
        void cropApply(int i, int i2, int i3, int i4);

        void cropCancelled();
    }

    public FragmentCrop() {
        this.isHDR = false;
        this.onClickListener = new C05801();
    }

    public void setCropListener(CropListener listener) {
        this.cropListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_crop, container, false);
    }

    public void onAttach(Activity act) {
        super.onAttach(act);
        this.context = getActivity();
        this.activity = getActivity();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setBitmap(Bitmap btm) {
        this.btm = btm;
    }

    public void onStart() {
        super.onStart();
        this.inputPath = null;
        this.outputPath = null;
        this.isHDR = true;
        this.cropView = new CropView(this.context, this.inputPath, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, this.btm, 1);
        RelativeLayout rl = (RelativeLayout) getView().findViewById(R.id.crop_view_container);
        LayoutParams rllp = new LayoutParams(-2, -2);
        rllp.addRule(14, -1);
        rllp.addRule(15, -1);
        rl.addView(this.cropView, rllp);
        this.ratioButtonList = new Button[11];
        this.ratioButtonList[0] = (Button) getView().findViewById(R.id.button1);
        this.ratioButtonList[1] = (Button) getView().findViewById(R.id.button1_1);
        this.ratioButtonList[2] = (Button) getView().findViewById(R.id.button2_1);
        this.ratioButtonList[3] = (Button) getView().findViewById(R.id.button1_2);
        this.ratioButtonList[4] = (Button) getView().findViewById(R.id.button3_2);
        this.ratioButtonList[5] = (Button) getView().findViewById(R.id.button2_3);
        this.ratioButtonList[6] = (Button) getView().findViewById(R.id.button4_3);
        this.ratioButtonList[7] = (Button) getView().findViewById(R.id.button3_4);
        this.ratioButtonList[8] = (Button) getView().findViewById(R.id.button4_5);
        this.ratioButtonList[9] = (Button) getView().findViewById(R.id.button5_7);
        this.ratioButtonList[10] = (Button) getView().findViewById(R.id.button16_9);
        for (Button onClickListener : this.ratioButtonList) {
            onClickListener.setOnClickListener(this.onClickListener);
        }
       // ((Button) getView().findViewById(R.id.button_ok)).setOnClickListener(this.onClickListener);
        getView().findViewById(R.id.button_apply_action).setOnClickListener(this.onClickListener);
        getView().findViewById(R.id.button_cancel_action).setOnClickListener(this.onClickListener);
        setRatioButtonListBackgroundColor(0);
        this.cropView.setMode(0);
    }

    public void onBackPressed() {
        this.cropListener.cropCancelled();
    }

    void setRatioButtonListBackgroundColor(int index) {
        for (int i = 0; i < this.ratioButtonList.length; i++) {
            this.ratioButtonList[i].setBackgroundResource(R.drawable.crop_border);
            this.ratioButtonList[i].setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.ratioButtonList[index].setBackgroundResource(R.drawable.crop_border_selected);
        this.ratioButtonList[index].setTextColor(-1);
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        if (id == R.id.button1) {
            this.cropView.setMode(0);
            setRatioButtonListBackgroundColor(0);
        } else if (id == R.id.button1_1) {
            this.cropView.setMode(1);
            setRatioButtonListBackgroundColor(1);
        } else if (id == R.id.button2_1) {
            this.cropView.setMode(2);
            setRatioButtonListBackgroundColor(2);
        } else if (id == R.id.button1_2) {
            this.cropView.setMode(3);
            setRatioButtonListBackgroundColor(3);
        } else if (id == R.id.button3_2) {
            this.cropView.setMode(4);
            setRatioButtonListBackgroundColor(4);
        } else if (id == R.id.button2_3) {
            this.cropView.setMode(5);
            setRatioButtonListBackgroundColor(5);
        } else if (id == R.id.button4_3) {
            this.cropView.setMode(6);
            setRatioButtonListBackgroundColor(6);
        } else if (id == R.id.button3_4) {
            this.cropView.setMode(7);
            setRatioButtonListBackgroundColor(7);
        } else if (id == R.id.button4_5) {
            this.cropView.setMode(8);
            setRatioButtonListBackgroundColor(8);
        } else if (id == R.id.button5_7) {
            this.cropView.setMode(9);
            setRatioButtonListBackgroundColor(9);
        } else if (id == R.id.button16_9) {
            this.cropView.setMode(10);
            setRatioButtonListBackgroundColor(10);
        } else if (id == R.id.button_apply_action) {
            okCrop();
        } else if (id == R.id.button_cancel_action) {
            cancelCrop();
        }
    }

    private void cancelCrop() {
        if (this.isHDR) {
            this.cropListener.cropCancelled();
        }
    }

    private void okCrop() {
        if (this.isHDR) {
            this.cropListener.cropApply(this.cropView.getLeftPos(), this.cropView.getTopPos(), this.cropView.getRightPos(), this.cropView.getBottomPos());
        }
    }
}
