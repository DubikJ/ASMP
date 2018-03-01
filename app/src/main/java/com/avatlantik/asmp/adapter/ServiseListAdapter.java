package com.avatlantik.asmp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.activity.ServiceActivity;
import com.avatlantik.asmp.model.db.ServiceData;

import java.util.List;

import static com.avatlantik.asmp.activity.ServiceActivity.SERVICE_ACTIVITY_PARAM_SERVICE_ID;

public class ServiseListAdapter  extends BaseAdapter{

    private List<ServiceData> list;
    private LayoutInflater layoutInflater;
    private Context context;


    public ServiseListAdapter(Context context, List<ServiceData> list) {
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
            view = layoutInflater.inflate(R.layout.list_service_item, parent, false);
        }
        ServiceData serviceData = getServiceData(position);

        TextView info = (TextView) view.findViewById(R.id.list_service_item_text);
        info.setText(serviceData.getName());

        TextView amount = (TextView) view.findViewById(R.id.list_service_number_plane);
        amount.setText(String.valueOf(serviceData.getNumberPlane()));

        TextView done = (TextView) view.findViewById(R.id.list_service_number_done);
        done.setText(String.valueOf(serviceData.getNumberDone()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceData selecteditem = getServiceData(position);

                Intent intent = new Intent(context, ServiceActivity.class);
                intent.putExtra(SERVICE_ACTIVITY_PARAM_SERVICE_ID, selecteditem.getExternalId());
                v.getContext().startActivity(intent);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        return view;
    }

    private ServiceData getServiceData(int position) {
        return (ServiceData) getItem(position);
    }
}
