package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.model.db.AnimalHistory;

import java.text.SimpleDateFormat;
import java.util.List;

public class AnimalHistoryRecyclerAdapter extends RecyclerView.Adapter {

    private List<AnimalHistory> list;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat dateFormatter;


    public static class HistoryeViewHolder extends RecyclerView.ViewHolder {

        TextView date, service, result;

        public HistoryeViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.list_history_anim_date);
            this.service = (TextView) itemView.findViewById(R.id.list_history_anim_service);
            this.result = (TextView) itemView.findViewById(R.id.list_history_anim_result);
        }
    }

    public AnimalHistoryRecyclerAdapter(Context context, List<AnimalHistory> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new HistoryeViewHolder(layoutInflater.inflate(R.layout.animal_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        AnimalHistory animalHistory = getAnimalHistory(listPosition);
        if (animalHistory != null) {
            ((HistoryeViewHolder) holder).date.setText(dateFormatter.format(animalHistory.getDate()));

            ((HistoryeViewHolder) holder).service.setText(animalHistory.getServiceData());

            ((HistoryeViewHolder) holder).result.setText(animalHistory.getResult());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private AnimalHistory getAnimalHistory(int position) {
        return (AnimalHistory) list.get(position);
    }
}
