package com.hieupt.recyclerviewpager;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class GridRecyclerViewPager extends FrameLayout {

    private ObjectWrapper<IRecyclerViewItemClickListener> mItemClickListenerWrapper;

    private final List<RecyclerView.ItemDecoration> mItemDecorations = new ArrayList<>();

    private final ViewPager mViewPager;

    public GridRecyclerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public GridRecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridRecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemClickListenerWrapper = new ObjectWrapper<>();
        mViewPager = new ViewPager(context);
        mViewPager.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mViewPager);
    }

    public <VH extends RecyclerView.ViewHolder> void setAdapter(RecyclerView.Adapter<VH> adapter, @IntRange(from = 1) int rowCount, @IntRange(from = 1) int columnCount) {
        mViewPager.setAdapter(new GridRecyclerViewPagerAdapter<>(getContext(), adapter,
                rowCount, columnCount, mItemDecorations, mItemClickListenerWrapper));
    }

    public void setOnItemClickListener(IRecyclerViewItemClickListener listener) {
        mItemClickListenerWrapper.set(listener);
    }

    /**
     * {@link #setAdapter(RecyclerView.Adapter, int, int)} needed to call to apply effect
     *
     * @param itemDecoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mItemDecorations.add(itemDecoration);
    }

    /**
     * {@link #setAdapter(RecyclerView.Adapter, int, int)} needed to call to apply effect
     *
     * @param itemDecoration
     */
    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mItemDecorations.remove(itemDecoration);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
