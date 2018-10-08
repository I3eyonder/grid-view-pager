package com.hieupt.recyclerviewpager;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class GridRecyclerViewPagerAdapter<VH extends RecyclerView.ViewHolder> extends PagerAdapter {

    private final Context mContext;

    private final int mRowCount;

    private final int mColumnCount;

    private final int mItemsPerPage;

    private final RecyclerView.Adapter<VH> mRecyclerViewAdapter;

    private final RecyclerView.AdapterDataObserver mAdapterObserver;

    private final ObjectWrapper<IRecyclerViewItemClickListener> mItemClickListenerWrapper;

    private final List<RecyclerView.ItemDecoration> mItemDecorations = new ArrayList<>();

    GridRecyclerViewPagerAdapter(@NonNull Context context, @NonNull RecyclerView.Adapter<VH> adapter,
                                 @IntRange(from = 1) int rowCount, @IntRange(from = 1) int columnCount,
                                 List<RecyclerView.ItemDecoration> itemDecorations,
                                 @NonNull ObjectWrapper<IRecyclerViewItemClickListener> itemClickListenerWrapper) {
        mContext = context;
        mItemClickListenerWrapper = itemClickListenerWrapper;
        if (itemDecorations != null && !itemDecorations.isEmpty()) {
            mItemDecorations.addAll(itemDecorations);
        }
        mRecyclerViewAdapter = adapter;
        mRowCount = rowCount;
        mColumnCount = columnCount;
        mItemsPerPage = rowCount * columnCount;
        mAdapterObserver = new AdapterObserver(this);
        mRecyclerViewAdapter.registerAdapterDataObserver(mAdapterObserver);
    }

    @Override
    public int getCount() {
        if (mRecyclerViewAdapter.getItemCount() % mItemsPerPage == 0) {
            return mRecyclerViewAdapter.getItemCount() / mItemsPerPage;
        } else {
            return mRecyclerViewAdapter.getItemCount() / mItemsPerPage + 1;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        if (!mItemDecorations.isEmpty()) {
            for (RecyclerView.ItemDecoration itemDecoration : mItemDecorations) {
                recyclerView.addItemDecoration(itemDecoration);
            }
        }
        PagingAdapter pagingAdapter = new PagingAdapter<>(mRecyclerViewAdapter, position, mItemsPerPage, mItemClickListenerWrapper);
        recyclerView.setAdapter(pagingAdapter);
        container.addView(recyclerView);
        return recyclerView;
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

    static class PagingAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

        private final int mPage;

        private final int mItemsPerPage;

        private final RecyclerView.Adapter<VH> mDelegateAdapter;

        private final ObjectWrapper<IRecyclerViewItemClickListener> mItemClickListenerWrapper;

        PagingAdapter(@NonNull RecyclerView.Adapter<VH> delegateAdapter, int page, int itemsPerPage,
                      @NonNull ObjectWrapper<IRecyclerViewItemClickListener> itemClickListenerWrapper) {
            mDelegateAdapter = delegateAdapter;
            mPage = page;
            mItemsPerPage = itemsPerPage;
            mItemClickListenerWrapper = itemClickListenerWrapper;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VH vh = mDelegateAdapter.onCreateViewHolder(parent, viewType);
            vh.itemView.setOnClickListener(new OnViewClickListener<>(mPage, mItemsPerPage, vh, mItemClickListenerWrapper));
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            mDelegateAdapter.onBindViewHolder(holder, position + (mPage * mItemsPerPage));
        }

        @Override
        public int getItemCount() {
            return Math.min(mItemsPerPage, mDelegateAdapter.getItemCount() - mItemsPerPage * mPage);
        }
    }

    static class OnViewClickListener<VH extends RecyclerView.ViewHolder> implements View.OnClickListener {

        private final int mPage;

        private final int mItemsPerPage;

        private final VH mViewHolder;

        private final ObjectWrapper<IRecyclerViewItemClickListener> mItemClickListenerWrapper;

        OnViewClickListener(int page, int itemsPerPage, @NonNull VH viewHolder,
                            @NonNull ObjectWrapper<IRecyclerViewItemClickListener> itemClickListenerWrapper) {
            mPage = page;
            mItemsPerPage = itemsPerPage;
            mViewHolder = viewHolder;
            mItemClickListenerWrapper = itemClickListenerWrapper;
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListenerWrapper.get() != null) {
                mItemClickListenerWrapper.get().onItemClick(v,
                        mViewHolder.getAdapterPosition() + mPage * mItemsPerPage);
            }
        }
    }

    static class AdapterObserver extends RecyclerView.AdapterDataObserver {

        private final PagerAdapter mPagerAdapter;

        AdapterObserver(@NonNull PagerAdapter pagerAdapter) {
            mPagerAdapter = pagerAdapter;
        }

        @Override
        public void onChanged() {
            mPagerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mPagerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mPagerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mPagerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mPagerAdapter.notifyDataSetChanged();
        }
    }
}
