package com.prototype.architecture.mvvm.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, U extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<U> {

    private static final String TAG = BaseRecyclerViewAdapter.class.getSimpleName();

    private List<T> data;
    private Context context;
    private LayoutInflater inflater;

    private SparseBooleanArray selectedItems;

    protected BaseRecyclerViewAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    protected BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.selectedItems = new SparseBooleanArray();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void add(T element) {
        data.add(element);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elements) {
        data.addAll(elements);
        notifyDataSetChanged();
    }

    public void remove(T element) {
        data.remove(element);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public boolean contains(T element) {
        return data.contains(element);
    }

    public int indexOf(T element) {
        return data.indexOf(element);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        return data.get(position);
    }

    protected Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public List<T> getData() {
        return data;
    }

    /**
     * Indicates if the item at position position is selected
     *
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItemsPositions().contains(position);
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param position Position of the item to toggle the selection status for
     */
    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    /**
     * Set a selection status of an item at a given position
     *
     * @param position Position of an item to set a selection status for
     */
    public void setSelection(int position) {
        clearSelection();
        toggleSelection(position);
        notifyItemChanged(position);
    }

    /**
     * Select all items
     *
     */
    public void selectAll() {
        for (int i = 0; i < data.size(); i++) {
            selectedItems.put(i, true);
            notifyItemChanged(i);
        }
    }

    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItemsPositions();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        Log.d(TAG,"getSelectedItemCount=" + selectedItems.size());
        return selectedItems.size();
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItemsPositions() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public Integer getSelectedItemPosition() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items.get(0);
    }

    public List<T> getSelectedItems() {
        List<T> selection = new ArrayList<T>();
        for (int i = 0; i < data.size(); i++) {
            if(isSelected(i)){
                selection.add(data.get(i));
            }
        }
        return selection;
    }

    /**
     * For an element give its position
     * @param element any data element from the list
     * @return The index for the element in the list
     */
    public int getItemPosition(T element){
        int position = 0;

        if(contains(element)){
            for (T e : this.data) {
                if(e.equals(element)){
                    return position;
                }
                position++;
            }
        }

        return position;
    }
}
