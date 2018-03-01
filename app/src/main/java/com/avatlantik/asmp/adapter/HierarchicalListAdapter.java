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
import com.avatlantik.asmp.adapter.treelistview.Element;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.avatlantik.asmp.adapter.treelistview.Element.NO_PARENT;
import static com.avatlantik.asmp.adapter.treelistview.Element.TOP_LEVEL;
import static com.avatlantik.asmp.common.Consts.CLEAR_GUID;

public class HierarchicalListAdapter extends BaseAdapter{

    private ArrayList<Element> elementsParent, elementsData;
    private List<Element> elementsOriginal, elementsFiltered;
    private int indentionBase;
    private LayoutInflater layoutInflater;
    private String startId;

    public HierarchicalListAdapter(Context context, ArrayList<Element> elementsOriginal, String startId) {
        this.elementsOriginal = elementsOriginal;
        this.elementsFiltered = new ArrayList<Element>(elementsOriginal);
        this.elementsParent = new ArrayList<Element>();
        this.elementsData = new ArrayList<Element>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.indentionBase = 20;
        this.startId = (startId == null || startId.isEmpty() ? CLEAR_GUID : startId);

        addingElement(this.startId, TOP_LEVEL);
    }

    public ArrayList<Element> getElements() {
        return elementsParent;
    }

    public ArrayList<Element> getElementsData() {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_hierarchical_element, null);
            holder.mCardView = (RelativeLayout) convertView.findViewById(R.id.element_rl);
            holder.disclosureImg = (ImageView) convertView.findViewById(R.id.element_imageview);
            holder.mTextView = (TextView) convertView.findViewById(R.id.element_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Element element = elementsParent.get(position);
        int level = element.getLevel();

        holder.mCardView.setPadding(
                indentionBase * level,
                holder.mCardView.getPaddingTop(),
                holder.mCardView.getPaddingRight(),
                holder.mCardView.getPaddingBottom());

        String housingCode = element.getExternalId();
        String housingName = element.getContentText();

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
        return convertView;
    }

    static class ViewHolder{
        ImageView disclosureImg;
        TextView mTextView;
        RelativeLayout mCardView;
    }

    public void setFilter(CharSequence constraint) {

        elementsFiltered.clear();
        elementsParent.clear();
        elementsData.clear();

        if (constraint == null || constraint.length() == 0) {
            elementsFiltered = new ArrayList<Element>(elementsOriginal);
        } else {

            Locale locale = Locale.getDefault();
            constraint = constraint.toString().toLowerCase(locale);

            for (Element element : elementsOriginal) {
                if (element != null) {

                    String data = element.getContentText();

                    if (data.toLowerCase(locale).contains(constraint.toString())) {
                        elementsFiltered.add(element);
                        addingParentElements(element.getExternalparentId(), elementsFiltered);
                        addingChildElements(element.getExternalId(), elementsFiltered);
                    }
                }
            }
        }

        addingElement(startId, TOP_LEVEL);

        notifyDataSetChanged();
    }

    private void addingElement(String externalId, int level){


        List<Element> elementList = getElementsByParentId(externalId);

        if(elementList!=null) {

            if (elementList.size() == 0) return;

            for (Element element : elementList) {

                Boolean notHaveParent = element.getExternalparentId().equals(startId);
                Boolean haveChild = getElementsByParentId(element.getExternalId()).size() > 0;

                int idParent = getIdInFilteredElementsAllByElement(element.getExternalparentId());
                int idElement = getIdInFilteredElementsAllByElement(element.getExternalId());
                Element newElement = new Element(element.getContentText(),
                        TOP_LEVEL + level,
                        idElement,
                        idParent,
                        haveChild,
                        false,
                        element.getExternalId(),
                        element.getExternalparentId(),
                        element.getObject());

                newElement.setFullNameImage(element.getFullNameImage());

                if (notHaveParent) {
                    elementsParent.add(newElement);
                } else {
                    elementsData.add(newElement);
                }
                addingElement(element.getExternalId(), (level + 1));
            }
        }

    }

    private List<Element> getElementsByParentId(final String externalId){

        return getElementsListbyParentId(externalId, elementsFiltered);

    }

    private int getIdInFilteredElementsAllByElement(final String externalId) {

        List<Element> elementList = getElementsListbyId(externalId, elementsFiltered);

        for (Element elementParent : elementList) {
            if (externalId.equals(elementParent.getExternalId())) {
                return elementsFiltered.indexOf(elementParent);
            }
        }

        return NO_PARENT;
    }

    private void addingParentElements(String externalId, List<Element> animalsList){

        final Element elementParent = getElementById(externalId, elementsOriginal);

        if (elementParent!=null){

            List<Element> addedElements = getElementsListbyId(elementParent.getExternalId(), animalsList);

            if(addedElements.size()==0) {
                animalsList.add(elementParent);
                addingParentElements(elementParent.getExternalparentId(), animalsList);
            }
        }

    }

    private void addingChildElements(String externalId, List<Element> elementsList) {

        List<Element> listChild = getElementsListbyParentId(externalId, elementsOriginal);

        for (final Element childItem : listChild) {
            List<Element> addedElements = getElementsListbyId(childItem.getExternalId(), elementsList);
            if(addedElements.size()==0) {
                elementsList.add(childItem);
                addingChildElements(childItem.getExternalId(), elementsList);
            }
        }
    }

    private Element getElementById(final String externalId, final List<Element> elementsList) {

        List<Element> elements = ((List<Element>) CollectionUtils.select(elementsList, new Predicate() {
            public boolean evaluate(Object sample) {
                return ((Element) sample).getExternalId().equals(externalId);
            }}));
        for (final Element element : elements) {
            return element;
        }
        return null;
    }

    private List<Element> getElementsListbyId(final String externalId, final List<Element> elementsList) {

        return  (List<Element>) CollectionUtils.select(elementsList, new Predicate() {
            public boolean evaluate(Object sample) {
                return ((Element) sample).getExternalId().equals(externalId);
            }});
    }

    private List<Element> getElementsListbyParentId(final String externalId, final List<Element> elementsList) {

        return  (List<Element>) CollectionUtils.select(elementsList, new Predicate() {
            public boolean evaluate(Object sample) {
                return ((Element) sample).getExternalparentId().equals(externalId);
            }});
    }
}