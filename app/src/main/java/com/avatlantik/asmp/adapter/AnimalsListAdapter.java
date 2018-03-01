package com.avatlantik.asmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avatlantik.asmp.R;
import com.avatlantik.asmp.adapter.treelistview.ElementAnimal;
import com.avatlantik.asmp.model.db.Animal;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnimalsListAdapter extends BaseAdapter{

    private ArrayList<ElementAnimal> elementsParent;
    private ArrayList<ElementAnimal> elementsData;
    private int indentionBase;
    private LayoutInflater layoutInflater;
    private Context context;

    public AnimalsListAdapter(Context context, ArrayList<ElementAnimal> elementsParent,
                               ArrayList<ElementAnimal> elementsData) {
        this.context = context;
        this.elementsParent = elementsParent;
        this.elementsData = elementsData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        indentionBase = 20;
    }


    public ArrayList<ElementAnimal> getElements() {
        return elementsParent;
    }

    public ArrayList<ElementAnimal> getElementsData() {
        return elementsData;
    }

    @Override
    public int getCount() {
        return elementsParent.size();
    }

    @Override
    public Object getItem(int position) {
        return elementsParent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ElementAnimal element = elementsParent.get(position);
        if(element.isHasChildren()) {
            convertView = layoutInflater.inflate(R.layout.fragment_hierarchical_element, null);
        }else {
            convertView = layoutInflater.inflate(R.layout.animal_item, null);
        }

        return fillView(convertView, element);
    }

    static class ViewHolder {
        ImageView disclosureImg;
        TextView mTextView;
        RelativeLayout mCardView;
        TextView mCodeNameTV;
        TextView mCodeTV;
        TextView mRfidTV;
        CircleImageView mAvatar;
    }

    public View fillView(View convertView, final ElementAnimal element) {

        ViewHolder holder = new ViewHolder();

        holder.mCardView = (RelativeLayout) convertView.findViewById(R.id.element_rl);

        convertView.setTag(holder);

        if(element.isHasChildren()) {
            holder.disclosureImg = (ImageView) convertView.findViewById(R.id.element_imageview);
            holder.mTextView = (TextView) convertView.findViewById(R.id.element_textview);
        }else{
            holder.mTextView = (TextView) convertView.findViewById(R.id.list_animal_type);
            holder.mCodeNameTV = (TextView) convertView.findViewById(R.id.list_animal_code_name);
            holder.mCodeTV = (TextView) convertView.findViewById(R.id.list_animal_code);
            holder.mRfidTV = (TextView) convertView.findViewById(R.id.list_animal_rfid);
            holder.mAvatar  = (CircleImageView) convertView.findViewById(R.id.avatar);
        }

        int level = element.getLevel();

        holder.mCardView.setPadding(
                indentionBase * level,
                holder.mCardView.getPaddingTop(),
                holder.mCardView.getPaddingRight(),
                holder.mCardView.getPaddingBottom());

        String housingCode = element.getExternalId();
        String housingName = element.getContentText();

        if(element.isHasChildren()) {
            holder.mTextView.setTag(housingCode);
            holder.mTextView.setText(housingName);
            if (element.isHasChildren() && !element.isExpanded()) {
                holder.disclosureImg.setImageResource(R.drawable.ic_arow_in_circle_right);
                holder.disclosureImg.setVisibility(View.VISIBLE);
            } else if (element.isHasChildren() && element.isExpanded()) {
                holder.disclosureImg.setImageResource(R.drawable.ic_arow_in_circle_down);
                holder.disclosureImg.setVisibility(View.VISIBLE);
            } else if (!element.isHasChildren()) {
                holder.disclosureImg.setImageResource(R.drawable.ic_arow_in_circle_right);
                holder.disclosureImg.setVisibility(View.INVISIBLE);
            }
        }else {
            Animal animalItem = (Animal) element.getAnimal();
            if(animalItem!=null) {
                holder.mTextView.setText(element.getContentText());
                holder.mRfidTV.setText(animalItem.getRfid());
                if(animalItem.isGroupAnimal()){
                    holder.mCodeNameTV.setText(context.getResources().getString(R.string.animal_group_number_short));
                    holder.mCodeTV.setText(String.valueOf(animalItem.getNumber()));
                }else{
                    holder.mCodeNameTV.setText(context.getResources().getString(R.string.service_item_number_symbol));
                    holder.mCodeTV.setText(animalItem.getCode());
                }
            }
            Glide.with(holder.mAvatar.getContext())
                    .load(element.getPhotoFile() == null ? R.drawable.ic_pig_photo : element.getPhotoFile())
                    .fitCenter()
                    .into(holder.mAvatar);
        }

        return convertView;

    }



}
