package com.github.hieupt.recyclerviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.Toast;

import com.hieupt.recyclerviewpager.GridRecyclerViewPager;
import com.hieupt.recyclerviewpager.IRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private GridRecyclerViewPager mGridRecyclerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            items.add("Item " + i);
        }
        mGridRecyclerViewPager = findViewById(R.id.recycler_view_pager);
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(items);
        mGridRecyclerViewPager.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mGridRecyclerViewPager.setAdapter(recyclerAdapter, 90, 3);
        mGridRecyclerViewPager.setOnItemClickListener(new IRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this, recyclerAdapter.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
