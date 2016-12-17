package com.example.daykm.daggerexample.features.weather.view.model;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.example.daykm.daggerexample.R;
import com.example.daykm.daggerexample.data.remote.City;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityModel extends EpoxyModelWithHolder<CityModel.VH> {

    private City city;

    private CityModel() {
    }

    private CityModel(long id) {
    }

    public CityModel(City city) {
        this.city = city;
    }

    @Override
    protected VH createNewHolder() {
        return new VH();
    }

    @Override
    @LayoutRes
    protected int getDefaultLayout() {
        return R.layout.list_item_city;
    }

    @Override
    public void bind(VH holder) {
        holder.name.setText(city.name);
        holder.coords.setText(city.coordinate.lat + " " + city.coordinate.lon);
        holder.id.setText(String.format(Locale.getDefault(), "%d", city.id));
        holder.country.setText(city.country);
    }

    @Override
    public void unbind(VH holder) {
        ButterKnife.apply(holder.views, new ButterKnife.Action<TextView>() {
            @Override
            public void apply(@NonNull TextView view, int index) {
                view.setText(null);
            }
        });
    }

    class VH extends EpoxyHolder {

        @BindView(R.id.example_city_name)
        TextView name;
        @BindView(R.id.example_city_coords)
        TextView coords;
        @BindView(R.id.example_city_id)
        TextView id;
        @BindView(R.id.example_city_country)
        TextView country;

        List<TextView> views;

        @Override
        protected void bindView(View itemView) {
            ButterKnife.bind(this, itemView);
            views = Arrays.asList(name, coords, id, country);
        }

    }

}
