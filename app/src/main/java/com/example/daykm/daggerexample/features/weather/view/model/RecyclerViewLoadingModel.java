package com.example.daykm.daggerexample.features.weather.view.model;

import android.widget.ProgressBar;

import com.airbnb.epoxy.EpoxyModel;
import com.example.daykm.daggerexample.R;

public class RecyclerViewLoadingModel extends EpoxyModel<ProgressBar> {

    @Override
    protected int getDefaultLayout() {
        return R.layout.list_loading;
    }

}
