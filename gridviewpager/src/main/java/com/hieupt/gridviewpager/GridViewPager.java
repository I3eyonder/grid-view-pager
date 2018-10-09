package com.hieupt.gridviewpager;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

public class GridViewPager extends FrameLayout {

    private ObjectWrapper<AdapterView.OnItemClickListener> mItemClickListenerWrapper;

    private final ViewPager mViewPager;

    public GridViewPager(@NonNull Context context) {
        this(context, null);
    }

    public GridViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemClickListenerWrapper = new ObjectWrapper<>();
        mViewPager = new ViewPager(context);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mViewPager);
    }

    public void setAdapter(BaseAdapter adapter, @IntRange(from = 1) int rowCount, @IntRange(from = 1) int columnCount) {
        mViewPager.setAdapter(new GridViewPagerAdapter(getContext(), adapter,
                rowCount, columnCount, mItemClickListenerWrapper));
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mItemClickListenerWrapper.set(listener);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
