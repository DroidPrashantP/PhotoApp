package com.app.camera.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.camera.Domain.Sticker;
import com.app.camera.Domain.StickerTabSelection;
import com.app.camera.R;
import com.app.camera.adapters.StickerCustomAdapter;

import java.util.ArrayList;

public class StickerFragment extends Fragment implements StickerCustomAdapter.StickerFilterListeners {

    private static final String TAG = StickerFragment.class.getName();
    private ArrayList<Integer> mSelectedAlphabetList = new ArrayList<Integer>();
    private ArrayList<Sticker> mStickerList = new ArrayList<Sticker>();
    private int[] couple_stricker = new int[]{R.drawable.couple_a,R.drawable.couple_b,R.drawable.couple_c,R.drawable.couple_d,R.drawable.couple_e,R.drawable.couple_f,R.drawable.couple_g,R.drawable.couple_h,R.drawable.couple_i,R.drawable.couple_j};
    private int[] emoji_stricker = new int[]{R.drawable.emoji_a,R.drawable.emoji_b,R.drawable.emoji_c,R.drawable.emoji_d,R.drawable.emoji_e,R.drawable.emoji_f,R.drawable.emoji_g,R.drawable.emoji_h,R.drawable.emoji_i};
    private int[] love_one_stricker = new int[]{R.drawable.love_one_a,R.drawable.love_one_b,R.drawable.love_one_c,R.drawable.love_one_d,R.drawable.love_one_e,R.drawable.love_one_f,R.drawable.love_one_g};
    private int[] love_two_stricker = new int[]{R.drawable.love_two_a,R.drawable.love_two_b,R.drawable.love_two_c,R.drawable.love_two_d,R.drawable.love_two_e,R.drawable.love_two_f,R.drawable.love_two_g,R.drawable.love_two_h};

    public String mCurrentStricker;
    private StickerCustomAdapter stickerCustomAdapter;

    public StickerFragment() {
    }

    @SuppressLint("ValidFragment")
    public StickerFragment(String currentStricker){
        mCurrentStricker = currentStricker;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStickerData();
    }

    private void setStickerData() {
      //  mSelectedAlphabetList = StickerTabSelection.getInstance().getSelectedStickerList();
          StickerTabSelection.getInstance().getSelectedStickerList().clear();
        int[] mStickerIdArray = null;
        if (mCurrentStricker.equalsIgnoreCase("CoupleSticker"))
            mStickerIdArray = couple_stricker;
        if (mCurrentStricker.equalsIgnoreCase("EmojiSticker"))
            mStickerIdArray = emoji_stricker;
        if (mCurrentStricker.equalsIgnoreCase("LoveOneSticker"))
            mStickerIdArray = love_one_stricker;
        if (mCurrentStricker.equalsIgnoreCase("LoveTwoSticker"))
            mStickerIdArray = love_two_stricker;


        for (int i = 0; i < mStickerIdArray.length; i++) {
                mStickerList.add(i, new Sticker(mStickerIdArray[i], false));
        }

//        for (int i = 0; i < mStickerList.size(); i++) {
//            if (mSelectedAlphabetList != null && mSelectedAlphabetList.size() > 0) {
//                for (int j = 0; j < mSelectedAlphabetList.size(); j++) {
//                    if (mStickerList.get(i).id == mSelectedAlphabetList.get(j)) {
//                        mStickerList.set(i, new Sticker(mStickerList.get(i).id, true));
//                    }
//                }
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sticker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.stickerRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        stickerCustomAdapter = new StickerCustomAdapter(getActivity(), this, mStickerList);
        recyclerView.setAdapter(stickerCustomAdapter);
    }

    @Override
    public void AddSticker(int id) {
        StickerTabSelection.getInstance().addSticker(id);
    }

    @Override
    public void RemoveSticker(int id) {
        StickerTabSelection.getInstance().removeSticker(id);
    }
}
