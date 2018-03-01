package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.ui.ViewModel;

import java.util.List;

public class ListGridAdapter extends BaseAdapter {

    private List<ViewModel> list;
    private LayoutInflater layoutInflater;
    private Context context;


    public ListGridAdapter(Context context, List<ViewModel> list) {
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
            view = layoutInflater.inflate(R.layout.animal_groupe_item, parent, false);
        }
        ViewModel viewModel = getViewModel(position);

        TextView animalGroupe = (TextView) view.findViewById(R.id.list_anim_groupe_name);
        animalGroupe.setText(viewModel.getText());

        ImageView animalGroupeImage = (ImageView) view.findViewById(R.id.animal_groupe_image);
        animalGroupeImage.setImageDrawable(context.getResources().getDrawable(viewModel.getImage()));

        return view;
    }

    private ViewModel getViewModel(int position) {
        return (ViewModel) getItem(position);
    }

}
