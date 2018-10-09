package com.github.hieupt.recyclerviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hieupt.gridviewpager.GridViewPager;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private GridViewPager mGridViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            items.add("Item " + i);
        }
        mGridViewPager = findViewById(R.id.grid_view_pager);
        final GridAdapter recyclerAdapter = new GridAdapter(this, android.R.layout.simple_list_item_1, items);
        mGridViewPager.setAdapter(recyclerAdapter, 70, 6);
        mGridViewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridViewActivity.this, recyclerAdapter.getItem(position) + " - " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
