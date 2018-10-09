package com.github.hieupt.recyclerviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by HieuPT on 10/9/2018.
 */
public class GridAdapter extends ArrayAdapter<String> {

    public GridAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }
}
