package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.model.db.Animal;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScanListAdapter extends BaseAdapter{

    private List<Animal> list;
    private LayoutInflater layoutInflater;
    private Context context;


    public ScanListAdapter(Context context, List<Animal> list) {
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.animal_item, parent, false);
        }
        Animal animal = getAnimal(position);

        ViewHolder holder = new ViewHolder();

        holder.mCardView = (RelativeLayout) convertView.findViewById(R.id.element_rl);

        convertView.setTag(holder);

        holder.mTextView = (TextView) convertView.findViewById(R.id.list_animal_type);
        holder.mCodeNameTV = (TextView) convertView.findViewById(R.id.list_animal_code_name);
        holder.mCodeTV = (TextView) convertView.findViewById(R.id.list_animal_code);
        holder.mRfidTV = (TextView) convertView.findViewById(R.id.list_animal_rfid);
        holder.mAvatar  = (CircleImageView) convertView.findViewById(R.id.avatar);


        if(animal!=null) {
            holder.mTextView.setText(animal.getFullNameType(context));
            holder.mRfidTV.setText(animal.getRfid());
            if(animal.isGroupAnimal()){
                holder.mCodeNameTV.setText(context.getResources().getString(R.string.animal_group_number_short));
                holder.mCodeTV.setText(String.valueOf(animal.getNumber()));
            }else{
                holder.mCodeNameTV.setText(context.getResources().getString(R.string.service_item_number_symbol));
                holder.mCodeTV.setText(animal.getCode());
            }
        }


        Glide.with(holder.mAvatar.getContext())
                .load(animal.getPhotoFile() == null ? R.drawable.ic_pig_photo : animal.getPhotoFile())
                .fitCenter()
                .into(holder.mAvatar);

        return convertView;
    }

    private Animal getAnimal(int position) {
        return (Animal) getItem(position);
    }

    static class ViewHolder {
        TextView mTextView;
        RelativeLayout mCardView;
        TextView mCodeNameTV;
        TextView mCodeTV;
        TextView mRfidTV;
        CircleImageView mAvatar;
    }
}
