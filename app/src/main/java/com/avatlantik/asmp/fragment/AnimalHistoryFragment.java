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
import com.avatlantik.asmp.adapter.AnimalHistoryRecyclerAdapter;
import com.avatlantik.asmp.model.db.AnimalHistory;

import java.util.List;

public class AnimalHistoryFragment extends Fragment {

    private static  final int LAYOUT = R.layout.fragment_animal_history;

    private View view;

    private RecyclerView recyclerView;
    private List<AnimalHistory> list;
    private AnimalHistoryRecyclerAdapter adapter;

    public static AnimalHistoryFragment getInstance() {

        Bundle args = new Bundle();
        AnimalHistoryFragment fragment = new AnimalHistoryFragment();
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

        list = ((AnimalActivity)getActivity()).getAnimalHistoryList();

        adapter = new AnimalHistoryRecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

    }
}
