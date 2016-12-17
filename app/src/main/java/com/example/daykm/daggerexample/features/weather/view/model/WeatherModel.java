package com.example.daykm.daggerexample.features.weather.view.model;

import android.view.View;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;

public class WeatherModel extends EpoxyModelWithHolder<WeatherModel.VH> {

    @Override
    protected VH createNewHolder() {
        return null;
    }

    @Override
    protected int getDefaultLayout() {
        return 0;
    }

    class VH extends EpoxyHolder {

        @Override
        protected void bindView(View itemView) {

        }

    }

}
