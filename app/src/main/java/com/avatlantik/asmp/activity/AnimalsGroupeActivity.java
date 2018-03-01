package com.avatlantik.asmp.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.AnimalGroupeRecyclerViewAdapter;
import com.avatlantik.asmp.app.ASMPApplication;
import com.avatlantik.asmp.model.db.AnimalType;
import com.avatlantik.asmp.repository.DataRepository;
import com.avatlantik.asmp.ui.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_OPEN_FOR_RESULT;
import static com.avatlantik.asmp.activity.AnimalsActivity.ANIMALS_ACTIVITY_TYPE_ANIMAL;
import static com.avatlantik.asmp.common.Consts.TYPE_GROUP_ANIMAL_ALL;

public class AnimalsGroupeActivity extends AppCompatActivity{

    @Inject
    DataRepository dataRepository;

    private List<AnimalType> animalTypeList;
    private RecyclerView recyclerView;
    private AnimalGroupeRecyclerViewAdapter adapter;
    private static List<ViewModel> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.groupe_animals_name));
        setContentView(R.layout.activity_animals_groupe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        ((ASMPApplication) getApplication()).getComponent().inject(this);

        animalTypeList = dataRepository.getAnimalTypeList();

        if(animalTypeList.size() < 1){
            Intent intent = new Intent(AnimalsGroupeActivity.this, AnimalsActivity.class);
            intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, TYPE_GROUP_ANIMAL_ALL);
            intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, false);
            startActivity(intent);
            finish();
        }

        initRecyclerView();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(recyclerView);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {

        items.clear();

        for (AnimalType animalType : animalTypeList) {
            items.add(new ViewModel(animalType.getName(), animalType.getDravableTypeAnimal(),  animalType.getTypeAnimal()));
        }

        adapter = new AnimalGroupeRecyclerViewAdapter(AnimalsGroupeActivity.this, items);
        adapter.setOnItemClickListener(new AnimalGroupeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewModel viewModel) {
                Intent intent = new Intent(AnimalsGroupeActivity.this, AnimalsActivity.class);
                intent.putExtra(ANIMALS_ACTIVITY_TYPE_ANIMAL, viewModel.getId());
                intent.putExtra(ANIMALS_ACTIVITY_OPEN_FOR_RESULT, false);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        if(adapter == null) {
            setRecyclerAdapter(recyclerView);
            recyclerView.scheduleLayoutAnimation();
        }
    }
}
