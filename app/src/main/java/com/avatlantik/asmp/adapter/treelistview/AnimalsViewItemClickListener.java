package com.avatlantik.asmp.adapter.treelistview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.avatlantik.asmp.adapter.AnimalsListAdapter;

import java.util.ArrayList;

public class AnimalsViewItemClickListener implements OnItemClickListener {
    private AnimalsListAdapter treeViewAdapter;
    private ListItemClick listItemClick;

    public AnimalsViewItemClickListener(AnimalsListAdapter treeViewAdapter, final ListItemClick listItemClick) {
        this.treeViewAdapter = treeViewAdapter;
        this.listItemClick = listItemClick;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        position -= 1;
        ElementAnimal element = (ElementAnimal) treeViewAdapter.getItem(position);
        ArrayList<ElementAnimal> elements = treeViewAdapter.getElements();
        ArrayList<ElementAnimal> elementsData = treeViewAdapter.getElementsData();
        if (!element.isHasChildren()) {
            listItemClick.onItemClik(element);
            return;
        }

        if (element.isExpanded()) {
            element.setExpanded(false);
            ArrayList<ElementAnimal> elementsToDel = new ArrayList<ElementAnimal>();
            for (int i = position + 1; i < elements.size(); i++) {
                if (element.getLevel() >= elements.get(i).getLevel())
                    break;
                elementsToDel.add(elements.get(i));
            }
            elements.removeAll(elementsToDel);
            treeViewAdapter.notifyDataSetChanged();
        } else {
            element.setExpanded(true);
            int i = 1;
            for (ElementAnimal e : elementsData) {
                if (e.getParendId() == element.getId()) {
                    e.setExpanded(false);
                    elements.add(position + i, e);
                    i ++;
                }
            }
            treeViewAdapter.notifyDataSetChanged();
        }
    }


    public interface ListItemClick {

        void onItemClik(ElementAnimal element);
    }



}