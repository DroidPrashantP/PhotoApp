package com.app.camera.sticker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.camera.R;

import java.util.ArrayList;
import java.util.List;

public class StickerGalleryFragment extends Fragment implements OnItemClickListener {

    private static final String TAG = "GalleryActivity";
    final int STICKER_LIMIT;
    Activity activity;
    Context context;
    int currentListIndex;
    DrawerLayout drawerLayout;
    StickerGalleryListener galleryListener;
    StickerGridAdapter gridAdapter;
    GridView gridView;
    TextView headerText;
    int initialTogglePos;
    StickerGalleryFragment mFragment;
    float moveFactor;
    ListView navList;
    OnClickListener onClickListener;
    List<Integer> selectedImageIdList;
    Animation slideIn;
    Animation slideOut;
    StickerGridItem[][] stickerItemList;
    ImageView toggleButton;
    int totalImage;



    class C06141 implements OnClickListener {
        C06141() {
        }

        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.textView_header) {
                StickerGalleryFragment.this.backtrace();
            }
            if (id == R.id.sticker_gallery_ok) {
                int i;
                int outerSize = StickerGalleryFragment.this.stickerItemList.length;
                for (int j = 0; j < outerSize; j++) {
                    for (StickerGridItem stickerGridItem : StickerGalleryFragment.this.stickerItemList[j]) {
                        stickerGridItem.selectedItemCount = 0;
                    }
                }
                int size = StickerGalleryFragment.this.selectedImageIdList.size();
                int[] arrr = new int[size];
                for (i = 0; i < size; i++) {
                    arrr[i] = ((Integer) StickerGalleryFragment.this.selectedImageIdList.get(i)).intValue();
                }
                StickerGalleryFragment.this.selectedImageIdList.clear();
                if (StickerGalleryFragment.this.galleryListener == null) {
                    StickerGalleryFragment.this.getActivity().getSupportFragmentManager().beginTransaction().hide(StickerGalleryFragment.this.mFragment).commit();
                } else {
                    StickerGalleryFragment.this.galleryListener.onGalleryOkImageArray(arrr);
                }
            }
        }
    }

    class C06152 implements OnTouchListener {
        C06152() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            StickerGalleryFragment.this.drawerLayout.openDrawer(StickerGalleryFragment.this.navList);
            return true;
        }
    }

    class C06163 implements Runnable {
        C06163() {
        }

        public void run() {
            StickerGalleryFragment.this.calculateTogglePos();
        }
    }

    class C06174 implements OnItemClickListener {
        C06174() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            StickerGalleryFragment.this.drawerLayout.closeDrawer(StickerGalleryFragment.this.navList);
            if (StickerGalleryFragment.this.currentListIndex != pos) {
                if (StickerGalleryFragment.this.stickerItemList == null) {
                    StickerGalleryFragment.this.createItemList();
                }
                StickerGalleryFragment.this.gridAdapter.setItems(StickerGalleryFragment.this.stickerItemList[pos]);
                StickerGalleryFragment.this.gridView.smoothScrollToPosition(0);
                StickerGalleryFragment.this.gridAdapter.notifyDataSetChanged();
            }
            StickerGalleryFragment.this.currentListIndex = pos;
        }
    }

    class C06186 implements Runnable {
        C06186() {
        }

        public void run() {
            StickerGalleryFragment.this.drawerLayout.openDrawer(StickerGalleryFragment.this.navList);
        }
    }

    class C06197 implements DialogInterface.OnClickListener {
        C06197() {
        }

        public void onClick(DialogInterface arg0, int arg1) {
        }
    }

    class C11765 extends SimpleDrawerListener {
        C11765() {
        }

        @SuppressLint({"NewApi"})
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @SuppressLint({"NewApi"})
        public void onDrawerOpened(View drawerView) {
        }

        @SuppressLint({"NewApi"})
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (StickerGalleryFragment.this.initialTogglePos <= 0) {
                StickerGalleryFragment.this.calculateTogglePos();
            }
            StickerGalleryFragment.this.moveFactor = (-slideOffset) * ((float) StickerGalleryFragment.this.initialTogglePos);
            if (VERSION.SDK_INT >= 11) {
                StickerGalleryFragment.this.toggleButton.setX(StickerGalleryFragment.this.moveFactor);
            }
        }

        public void onDrawerStateChanged(int newState) {
            if (newState == DrawerLayout.STATE_SETTLING) {
                Log.e(StickerGalleryFragment.TAG, "toggleButton is visible ");
                if (StickerGalleryFragment.this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    Log.e(StickerGalleryFragment.TAG, "CLOSING");
                } else {
                    Log.e(StickerGalleryFragment.TAG, "OPENING");
                }
            }
        }
    }

    public StickerGalleryFragment() {
        this.currentListIndex = 0;
        this.moveFactor = 0.0f;
        this.initialTogglePos = 0;
        this.totalImage = 0;
        this.selectedImageIdList = new ArrayList();
        this.STICKER_LIMIT = 12;
        this.mFragment = this;
        this.onClickListener = new C06141();
    }

    public void setGalleryListener(StickerGalleryListener l) {
        this.galleryListener = l;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.sticker_fragment_gallery, container, false);
        this.headerText = (TextView) fragmentView.findViewById(R.id.textView_header);
        this.headerText.setOnClickListener(this.onClickListener);
        ((ImageButton) fragmentView.findViewById(R.id.sticker_gallery_ok)).setOnClickListener(this.onClickListener);
        this.toggleButton = (ImageView) fragmentView.findViewById(R.id.toggle_button);
        this.slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        this.slideOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        this.slideIn.setFillAfter(true);
        this.slideOut.setFillAfter(true);
        this.toggleButton.setOnTouchListener(new C06152());
        this.toggleButton.post(new C06163());
        NavigationDrawerListAdapter listAdapter = new NavigationDrawerListAdapter(getActivity());
        this.drawerLayout = (DrawerLayout) fragmentView.findViewById(R.id.layout_gallery_fragment_drawer);
        this.navList = (ListView) fragmentView.findViewById(R.id.drawer);
        this.navList.setAdapter(listAdapter);
        this.navList.setItemChecked(0, true);
        this.navList.setOnItemClickListener(new C06174());
        this.drawerLayout.setDrawerListener(new C11765());
        return fragmentView;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    void calculateTogglePos() {
        int[] loc = new int[2];
        this.toggleButton.getLocationOnScreen(loc);
        this.initialTogglePos = this.toggleButton.getWidth() + loc[0];
        Log.e(TAG, "initialTogglePos " + this.initialTogglePos);
    }

    public void onAttach(Activity act) {
        super.onAttach(act);
        this.context = getActivity();
        this.activity = getActivity();
    }

    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.gridView != null && !hidden) {
            Log.e(TAG, "onHiddenChanged gridView");
            this.gridAdapter.notifyDataSetChanged();
            this.gridView.invalidateViews();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setGridAdapter();
        if (this.drawerLayout != null) {
            this.drawerLayout.postDelayed(new C06186(), 600);
        }
    }

    private void setGridAdapter() {
        this.gridView = (GridView) getView().findViewById(R.id.gridView);
        if (this.stickerItemList == null) {
            createItemList();
        }
        this.gridAdapter = new StickerGridAdapter(this.context, this.stickerItemList[0], this.gridView);
        this.gridView.setAdapter(this.gridAdapter);
        this.gridView.setOnItemClickListener(this);
    }

    private void createItemList() {
        int outerSize = Utility.stickerResIdList.length;
        this.stickerItemList = new StickerGridItem[outerSize][];
        for (int j = 0; j < outerSize; j++) {
            int size = Utility.stickerResIdList[j].length;
            this.stickerItemList[j] = new StickerGridItem[size];
            for (int i = 0; i < size; i++) {
                this.stickerItemList[j][i] = new StickerGridItem(Utility.stickerResIdList[j][i]);
            }
        }
    }

    public void onBackPressed() {
        backtrace();
    }

    public void backtrace() {
        if (this.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            int outerSize = this.stickerItemList.length;
            for (int j = 0; j < outerSize; j++) {
                for (StickerGridItem stickerGridItem : this.stickerItemList[j]) {
                    stickerGridItem.selectedItemCount = 0;
                }
            }
            this.selectedImageIdList.clear();
            this.galleryListener.onGalleryCancel();
            return;
        }
        this.drawerLayout.openDrawer(this.navList);
    }

    public void setTotalImage(int size) {
        this.totalImage = size;
        if (this.headerText != null) {
            this.headerText.setText(new StringBuilder(String.valueOf(this.totalImage + this.selectedImageIdList.size())).append(getString(R.string.sticker_items_selected)).toString());
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int location, long arg3) {
        if (this.totalImage + this.selectedImageIdList.size() >= 12) {
            Builder alertDialogBuilder = new Builder(this.context);
            alertDialogBuilder.setMessage(String.format(getString(R.string.sticker_choose_limit), new Object[]{Integer.valueOf(12)}));
            alertDialogBuilder.setPositiveButton("OK", new C06197());
            alertDialogBuilder.create().show();
            return;
        }
        if (this.gridAdapter.itemList[location].selectedItemCount == 0) {
            StickerGridItem stickerGridItem = this.gridAdapter.itemList[location];
            stickerGridItem.selectedItemCount++;
        } else {
            this.gridAdapter.itemList[location].selectedItemCount = 0;
        }
        ImageView imageItemSelected = (ImageView) arg1.findViewById(R.id.image_view_item_selected);
        if (imageItemSelected.getVisibility() == View.INVISIBLE && this.gridAdapter.itemList[location].selectedItemCount == 1) {
            imageItemSelected.setVisibility(View.VISIBLE);
        }
        if (imageItemSelected.getVisibility() == View.VISIBLE && this.gridAdapter.itemList[location].selectedItemCount == 0) {
            imageItemSelected.setVisibility(View.INVISIBLE);
        }
        int id = this.gridAdapter.itemList[location].resId;
        if (this.gridAdapter.itemList[location].selectedItemCount == 1) {
            this.selectedImageIdList.add(Integer.valueOf(id));
        } else {
            for (int i = 0; i < this.selectedImageIdList.size(); i++) {
                if (((Integer) this.selectedImageIdList.get(i)).intValue() == id) {
                    this.selectedImageIdList.remove(i);
                    break;
                }
            }
        }
        this.headerText.setText(new StringBuilder(String.valueOf(this.totalImage + this.selectedImageIdList.size())).append(getString(R.string.sticker_items_selected)).toString());
    }
}
