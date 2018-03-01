package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;

import java.util.List;

public class VetExertiseListAdapter extends BaseAdapter{

    private List<ServiceDoneVetExercise> list;
    private LayoutInflater layoutInflater;
    private Context context;


    public VetExertiseListAdapter(Context context, List<ServiceDoneVetExercise> list) {
        this.list = list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_veterinary, parent, false);
        }
        ServiceDoneVetExercise serviceDoneVetExercise = getServiceData(position);

        final EditText vetExercise = (EditText) view.findViewById(R.id.list_service_done_vet_exercise);
        vetExercise.setText(serviceDoneVetExercise.getVetExercise()==null ? "" : serviceDoneVetExercise.getVetExercise().getName());
        vetExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((ServiceActivity) context).showeQuestionSelectVetExercise(selectedService, vetExercise);
            }
        });

        final EditText vetPreparat = (EditText) view.findViewById(R.id.list_service_done_vet_preparat);
        vetPreparat.setText(serviceDoneVetExercise.getVetPreparat()==null ? "" : serviceDoneVetExercise.getVetPreparat().getName());
        vetPreparat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((ServiceActivity) context).showeQuestionSelectVetPreparat(selectedService, vetPreparat);
            }
        });

        return view;
    }

    private ServiceDoneVetExercise getServiceData(int position) {
        return (ServiceDoneVetExercise) getItem(position);
    }
}
