package com.avatlantik.asmp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.activity.AnimalActivity;
import com.avatlantik.asmp.adapter.FarrowingCycleRecyclerAdapter;
import com.avatlantik.asmp.model.db.FarrowingCycle;

import java.util.List;

public class AnimalCyclesFarrowingFragment extends Fragment {

    private static  final int LAYOUT = R.layout.fragment_animal_history;

    private View view;

    private RecyclerView recyclerView;
    private List<FarrowingCycle> list;
    private FarrowingCycleRecyclerAdapter adapter;

    public static AnimalCyclesFarrowingFragment getInstance() {
        Bundle args = new Bundle();
        AnimalCyclesFarrowingFragment fragment = new AnimalCyclesFarrowingFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_animal_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            listView.setNestedScrollingEnabled(true);
//        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        list = ((AnimalActivity)getActivity()).getFarrowingCyclesByAnimalId();

        adapter = new FarrowingCycleRecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

    }
}
