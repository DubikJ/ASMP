package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.model.db.Animal;

import java.util.List;

public class ServiceGroupProcessingListAdapter extends BaseAdapter{

    private List<Animal> animals;
    private LayoutInflater layoutInflater;
    private Context context;

    public ServiceGroupProcessingListAdapter(Context context, List<Animal> animals) {
        this.context = context;
        this.animals = animals;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int position) {
        return animals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Animal animal = animals.get(position);
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.service_group_processing_item, null);
        }

        return fillView(convertView, animal);
    }

    static class ViewHolder {
        TextView mNameTV;
        TextInputLayout mWeightLL;
        EditText mWeightTV;
        EditText mNumberTV;
        TextInputLayout mWeightTIL;
        TextInputLayout mNumberTIL;
    }

    public View fillView(View convertView, final Animal animal) {

        if (animal!=null) {
            ViewHolder holder = new ViewHolder();
            holder.mNameTV = (TextView) convertView.findViewById(R.id.list_animal_name);
            holder.mWeightLL = (TextInputLayout) convertView.findViewById(R.id.list_animal_weight_ll);
            holder.mWeightTV = (EditText) convertView.findViewById(R.id.list_animal_weight);
            holder.mWeightTV.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {animal.setWeight(getDoubleForString(s));}
            });
            holder.mNumberTV = (EditText) convertView.findViewById(R.id.list_animal_number);
            holder.mNumberTV.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    animal.setNumber(getIntForString(s));
                }
            });

            holder.mNumberTIL = (TextInputLayout) convertView.findViewById(R.id.list_animal_number_ll);
            holder.mNumberTIL.setVisibility(animal.isGroupAnimal() ? View.VISIBLE : View.GONE);
            holder.mWeightTIL = (TextInputLayout) convertView.findViewById(R.id.list_animal_weight_ll);

            holder.mNameTV.setText(animal.getFullNameType(context));
            holder.mWeightTV.setText("");
            holder.mNumberTV.setText(String.valueOf(animal.getNumber()));

        }

        return convertView;

    }

    private int getIntForString(Editable s){
        String text = String.valueOf(s);
        return text==null || text.isEmpty() ? 0 : Integer.valueOf(text);
    }

    private Double getDoubleForString(Editable s){
        String text = String.valueOf(s);
        return text==null || text.isEmpty() ? 0.0 : Double.valueOf(text);
    }


}
