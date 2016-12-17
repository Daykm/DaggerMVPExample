package com.example.daykm.daggerexample.features.weather.view.model;

import android.widget.ProgressBar;

import com.airbnb.epoxy.EpoxyModel;
import com.example.daykm.daggerexample.R;

public class ItemLoadingModel extends EpoxyModel<ProgressBar> {

    @Override
    protected int getDefaultLayout() {
        return R.layout.list_item_loading;
    }

}
