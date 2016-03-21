package com.app.camera.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.app.camera.Domain.StickerTab;
import com.app.camera.Domain.StickerTabSelection;
import com.app.camera.R;
import com.app.camera.adapters.CustomVerticalTabAdapter;
import com.app.camera.fragments.StickerFragment;

import java.util.ArrayList;

public class StickerActivity extends AppCompatActivity implements View.OnClickListener{

    private static int STRICKER_RESULT = 10;
    private int ResID ;
    private GridView mFontGridView;
    private RecyclerView mRecyclerView;
    private CustomVerticalTabAdapter mCustomVerticalTabAdapter;
    private Button mCancelBtn;
    private Button mApplyBtn;
    private ArrayList<StickerTab> mTabArrayList = new ArrayList<StickerTab>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stricker);

        findIDS();
        setToolbar();
        setListenerOnView();
        setTabData();
        intiTabRecyclerView();
        SelectedTab(0);

    }

    /**
     * initialize tab recyclerView and set adapter
     */
    private void intiTabRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.tabRecycleView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mCustomVerticalTabAdapter = new CustomVerticalTabAdapter(this, mTabArrayList);
        mRecyclerView.setAdapter(mCustomVerticalTabAdapter);
    }

    /**
     * set Tab data
     */
    public void setTabData() {
        String[] mTabNameArray = getResources().getStringArray(R.array.store_filter_tabs);
        for (int i = 0; i < mTabNameArray.length; i++) {
            if (i == 0) {
                StickerTab tabObj = new StickerTab(mTabNameArray[i], true);
                mTabArrayList.add(i, tabObj);
            }else {
                StickerTab tabObj = new StickerTab(mTabNameArray[i], false);
                mTabArrayList.add(i, tabObj);
            }
        }
    }

    /**
     * mapping ui item by ids
     */
    private void findIDS() {
        mCancelBtn = (Button) findViewById(R.id.clearBtn);
        mApplyBtn = (Button) findViewById(R.id.applyBtn);

        mCancelBtn.setOnClickListener(this);
        mApplyBtn.setOnClickListener(this);
    }

    /**
     * set listener on view
     */
    private void setListenerOnView() {
        mCancelBtn.setOnClickListener(this);
        mApplyBtn.setOnClickListener(this);
    }

    public void SelectedTab(int tabPosition) {
        Fragment fragment = null;
        String fragmentTag = null;
        if (tabPosition == 0) {
            fragmentTag = "CoupleSticker";
            fragment = new StickerFragment(fragmentTag);
        }else if (tabPosition == 1) {
            fragmentTag = "EmojiSticker";
            fragment = new StickerFragment(fragmentTag);
        }else if (tabPosition == 2) {
            fragmentTag = "LoveOneSticker";
            fragment = new StickerFragment(fragmentTag);
        }
        else if (tabPosition == 3) {
            fragmentTag = "LoveTwoSticker";
            fragment = new StickerFragment(fragmentTag);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, fragmentTag)
                .commit();
        notifyTabAdapter(tabPosition);
    }

    private void notifyTabAdapter(int tabPosition) {
        for (int i = 0; i < mTabArrayList.size(); i++) {
            if (i == tabPosition) {
                StickerTab tab = mTabArrayList.get(tabPosition);
                tab.isSelected = true;
                mTabArrayList.set(tabPosition, tab);
            } else {
                StickerTab tab = new StickerTab(mTabArrayList.get(i).Title, false);
                mTabArrayList.set(i, tab);
            }
        }
        mCustomVerticalTabAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mApplyBtn == v) {
            if (StickerTabSelection.getInstance().getSelectedStickerList().size() > 0) {
                ResID = StickerTabSelection.getInstance().getSelectedStickerList().get(0);
                Intent i = new Intent();
                i.putExtra("resID",ResID);
                setResult(RESULT_OK, i);
                finish();
            }

        }
        if (mCancelBtn == v) {
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * Set Toolbar : Add app name as title and app logo as toolbar logo
     */
    private void setToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Stickers");
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
