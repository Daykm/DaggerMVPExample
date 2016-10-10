package com.example.daykm.daggerexample.data;


import android.content.Context;
import android.util.Log;

import com.example.daykm.daggerexample.data.remote.City;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okio.BufferedSource;
import okio.Okio;

public class CityRepository {

    private Context context;

    public CityRepository(Context context) {
        adapter = new Moshi.Builder().build().adapter(Types.newParameterizedType(List.class, City.class));
        this.context = context;
    }

    private JsonAdapter<List<City>> adapter;

    public List<City> getCities() {
        try {
            return adapter.fromJson(Okio.buffer(Okio.source(context.getAssets().open("city.list.us.json"))));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
