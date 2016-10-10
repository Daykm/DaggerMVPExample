package com.example.daykm.daggerexample.features.example;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daykm.daggerexample.R;
import com.example.daykm.daggerexample.data.remote.City;

import java.util.List;
import java.util.Locale;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cities;

    public CityAdapter(List<City> cities) {
        this.cities = cities;
    }

    @Override public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_city_list_item, parent, false));
    }

    @Override public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(cities.get(position));
    }

    @Override public int getItemCount() {
        return cities.size();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {

        TextView name, coords, id, country;

        public CityViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.example_city_name);
            coords = (TextView) itemView.findViewById(R.id.example_city_coords);
            id = (TextView) itemView.findViewById(R.id.example_city_id);
            country = (TextView) itemView.findViewById(R.id.example_city_country);

            itemView.setOnClickListener(view ->
                    Log.i("CityViewHolder", String.format(Locale.US, "Position : %d", getAdapterPosition())));
        }

        public void bind(City city) {
            name.setText(city.name);
            coords.setText(String.format(Locale.US, "%f. %f", city.coordinate.lat, city.coordinate.lon));
            id.setText(String.format(Locale.US, "%d", city.id));
            country.setText(city.country);
        }
    }

}
