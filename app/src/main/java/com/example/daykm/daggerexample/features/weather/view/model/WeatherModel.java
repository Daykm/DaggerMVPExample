package com.example.daykm.daggerexample.features.weather.view.model;

import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.example.daykm.daggerexample.R;
import com.example.daykm.daggerexample.data.remote.CurrentWeather;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherModel extends EpoxyModelWithHolder<WeatherModel.VH> {

    CurrentWeather weather;

    public WeatherModel(CurrentWeather weather) {
        this.weather = weather;
    }

    @Override
    protected VH createNewHolder() {
        return new VH();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.list_item_weather;
    }

    @Override
    public void bind(VH holder) {
        holder.textView.setText(weather.weather[0].description);
    }

    class VH extends EpoxyHolder {

        @BindView(R.id.weather)
        TextView textView;

        @Override
        protected void bindView(View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }

}
