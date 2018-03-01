package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.model.db.FarrowingCycle;

import java.text.SimpleDateFormat;
import java.util.List;

public class FarrowingCycleRecyclerAdapter extends RecyclerView.Adapter {

    private List<FarrowingCycle> list;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat dateFormatter;


    public static class FarrowingCycleViewHolder extends RecyclerView.ViewHolder {

        TextView date, service, result;

        public FarrowingCycleViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.list_history_anim_date);
            this.service = (TextView) itemView.findViewById(R.id.list_history_anim_service);
            this.result = (TextView) itemView.findViewById(R.id.list_history_anim_result);
        }
    }

    public FarrowingCycleRecyclerAdapter(Context context, List<FarrowingCycle> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new FarrowingCycleViewHolder(layoutInflater.inflate(R.layout.animal_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        FarrowingCycle farrowingCycle = getFarrowingCycle(listPosition);
        if (farrowingCycle != null) {
            ((FarrowingCycleViewHolder) holder).date.setText(dateFormatter.format(farrowingCycle.getDate()));

            ((FarrowingCycleViewHolder) holder).service.setText(farrowingCycle.getServiceData());

            ((FarrowingCycleViewHolder) holder).result.setText(farrowingCycle.getResult());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private FarrowingCycle getFarrowingCycle(int position) {
        return (FarrowingCycle) list.get(position);
    }
}
