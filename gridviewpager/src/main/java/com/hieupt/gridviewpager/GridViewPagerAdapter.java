package com.hieupt.gridviewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

class GridViewPagerAdapter extends PagerAdapter {

    private final Context mContext;

    private final int mRowCount;

    private final int mColumnCount;

    private final int mItemsPerPage;

    private final BaseAdapter mGridViewAdapter;

    private final ObjectWrapper<AdapterView.OnItemClickListener> mItemClickListenerWrapper;

    GridViewPagerAdapter(@NonNull Context context, @NonNull BaseAdapter adapter,
                         @IntRange(from = 1) int rowCount, @IntRange(from = 1) int columnCount,
                         @NonNull ObjectWrapper<AdapterView.OnItemClickListener> itemClickListenerWrapper) {
        mContext = context;
        mItemClickListenerWrapper = itemClickListenerWrapper;
        mGridViewAdapter = adapter;
        mRowCount = rowCount;
        mColumnCount = columnCount;
        mItemsPerPage = rowCount * columnCount;
        mGridViewAdapter.registerDataSetObserver(new AdapterObserver(this));
    }

    @Override
    public int getCount() {
        if (mGridViewAdapter.getCount() % mItemsPerPage == 0) {
            return mGridViewAdapter.getCount() / mItemsPerPage;
        } else {
            return mGridViewAdapter.getCount() / mItemsPerPage + 1;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        GridView gridView = new GridView(mContext);
        gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        gridView.setNumColumns(mColumnCount);
        gridView.setOnItemClickListener(new OnItemClickListener(position, mItemsPerPage, mItemClickListenerWrapper));
        PagingAdapter pagingAdapter = new PagingAdapter(mGridViewAdapter, position, mItemsPerPage);
        gridView.setAdapter(pagingAdapter);
        container.addView(gridView);
        return gridView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        RecyclerView recyclerView = (RecyclerView) object;
        container.removeView(recyclerView);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    static class PagingAdapter extends BaseAdapter {

        private final int mPage;

        private final int mItemsPerPage;

        private final BaseAdapter mDelegateAdapter;

        PagingAdapter(@NonNull BaseAdapter delegateAdapter, int page, int itemsPerPage) {
            mDelegateAdapter = delegateAdapter;
            mPage = page;
            mItemsPerPage = itemsPerPage;
        }

        @Override
        public int getCount() {
            return Math.min(mItemsPerPage, mDelegateAdapter.getCount() - mItemsPerPage * mPage);
        }

        @Override
        public Object getItem(int position) {
            return mDelegateAdapter.getItem(position + mPage * mItemsPerPage);
        }

        @Override
        public long getItemId(int position) {
            return position + mPage * mItemsPerPage;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mDelegateAdapter.getView(position + mPage * mItemsPerPage, convertView, parent);
        }
    }

    static class OnItemClickListener implements AdapterView.OnItemClickListener {

        private final int mPage;

        private final int mItemsPerPage;

        private final ObjectWrapper<AdapterView.OnItemClickListener> mItemClickListenerWrapper;

        OnItemClickListener(int page, int itemsPerPage,
                            @NonNull ObjectWrapper<AdapterView.OnItemClickListener> itemClickListenerWrapper) {
            mPage = page;
            mItemsPerPage = itemsPerPage;
            mItemClickListenerWrapper = itemClickListenerWrapper;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mItemClickListenerWrapper.get() != null) {
                mItemClickListenerWrapper.get().onItemClick(parent, view,
                        position + mPage * mItemsPerPage, id);
            }
        }
    }

    static class AdapterObserver extends DataSetObserver {

        private final PagerAdapter mPagerAdapter;

        AdapterObserver(@NonNull PagerAdapter pagerAdapter) {
            mPagerAdapter = pagerAdapter;
        }

        @Override
        public void onChanged() {
            mPagerAdapter.notifyDataSetChanged();
        }
    }
}
