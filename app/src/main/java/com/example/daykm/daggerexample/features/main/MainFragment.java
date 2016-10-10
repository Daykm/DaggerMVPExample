package com.example.daykm.daggerexample.features.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daykm.daggerexample.App;
import com.example.daykm.daggerexample.R;
import com.example.daykm.daggerexample.dagger.modules.PresenterModule;
import com.example.daykm.daggerexample.data.remote.City;

import java.util.List;

public class MainFragment extends Fragment implements MainView {

    private MainPresenter presenter;

    private RecyclerView recyclerView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = App.appComponent().plus(new PresenterModule(this)).presenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.example_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override public void setCities(List<City> cities) {
        recyclerView.setAdapter(new CityAdapter(cities));
    }

}
