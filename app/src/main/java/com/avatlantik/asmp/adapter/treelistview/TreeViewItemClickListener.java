package com.avatlantik.asmp.adapter.treelistview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.avatlantik.asmp.adapter.HierarchicalListAdapter;

import java.util.ArrayList;

public class TreeViewItemClickListener implements OnItemClickListener{
    private HierarchicalListAdapter treeViewAdapter;
    private ListItemClick listItemClick;

    public TreeViewItemClickListener(HierarchicalListAdapter treeViewAdapter, final ListItemClick listItemClick) {
        this.treeViewAdapter = treeViewAdapter;
        this.listItemClick = listItemClick;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        position -= 1;
        Element element = (Element) treeViewAdapter.getItem(position);
        ArrayList<Element> elements = treeViewAdapter.getElements();
        ArrayList<Element> elementsData = treeViewAdapter.getElementsData();
        if (!element.isHasChildren()) {
            listItemClick.onItemClik(element);
            return;
        }

        if (element.isExpanded()) {
            element.setExpanded(false);
            ArrayList<Element> elementsToDel = new ArrayList<Element>();
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
            for (Element e : elementsData) {
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

        void onItemClik(Element element);

    }



}