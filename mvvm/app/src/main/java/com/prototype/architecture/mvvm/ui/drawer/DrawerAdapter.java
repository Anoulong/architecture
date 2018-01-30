package com.prototype.architecture.mvvm.ui.drawer;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.mvvm.core.common.utils.StringUtils;
import com.mvvm.core.local.ModuleEntity;
import com.prototype.architecture.mvvm.R;
import com.prototype.architecture.mvvm.ui.base.BaseRecyclerViewAdapter;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends BaseRecyclerViewAdapter<DrawerAdapter.DrawerItem, RecyclerView.ViewHolder> {

    private static final String TAG = DrawerAdapter.class.getSimpleName();


    private DrawerAdapterListener mListener;
    private Map<String, Integer> itemIdList;


    public interface DrawerAdapterListener {
        void onDrawerItemleSelected(View v, DrawerItem item, int position);
    }

    public DrawerAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (DrawerItem.ItemType.values()[viewType]) {
            case header:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_header, parent, false);
                return new DrawerHeaderViewHolder(view);
            case module:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_module, parent, false);
                return new DrawerModuleViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_module, parent, false);
                return new DrawerModuleViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        final DrawerItem item = getItem(position);
        return item.getItemType().ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DrawerItem item = getItem(position);

        switch (item.getItemType()) {
            case module:
                DrawerModuleViewHolder moduleViewHolder = (DrawerModuleViewHolder) holder;
                if (moduleViewHolder != null && item != null) {
                    setupDrawerModule(moduleViewHolder, item);
                }
                break;
            case settings:
                DrawerModuleViewHolder settingViewHolder = (DrawerModuleViewHolder) holder;
                if (settingViewHolder != null && item != null) {
                    setupDrawerSetting(settingViewHolder, item);
                }
                break;
        }
    }

    private void setupDrawerModule(DrawerModuleViewHolder moduleViewHolder, DrawerItem item) {
        ModuleEntity module = item.getModuleEntity();

        moduleViewHolder.title.setText(module.getTitle());
        moduleViewHolder.title.setTextColor( ContextCompat.getColor(getContext(), isSelected(moduleViewHolder.getAdapterPosition()) ? R.color.colorPrimary : R.color.colorPrimaryDark));

        moduleViewHolder.itemView.setOnClickListener(v -> {
            mListener.onDrawerItemleSelected(v, item, moduleViewHolder.getAdapterPosition());
        });

    }

    private void setupDrawerSetting(DrawerModuleViewHolder settingViewHolder, DrawerItem item) {
        if(StringUtils.isNotBlank(item.getContentText())) {
            settingViewHolder.title.setText(item.getContentText());
        }
        settingViewHolder.title.setTextColor( ContextCompat.getColor(getContext(), isSelected(settingViewHolder.getAdapterPosition()) ? R.color.colorPrimary : R.color.colorPrimaryDark));

        settingViewHolder.itemView.setOnClickListener(v -> {
            mListener.onDrawerItemleSelected(v, item, settingViewHolder.getAdapterPosition());
        });
    }

    public void setListener(DrawerAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void setData(List<DrawerItem> data) {
        itemIdList = new HashMap<>();
        Stream.of(data)
                .indexed()
                .filter(pair -> pair.getSecond().getItemType().equals(DrawerItem.ItemType.module))
                .forEach(pair -> itemIdList.put(pair.getSecond().getModuleEntity().getEid(), pair.getFirst()));
        super.setData(data);
    }

    public int getPositionForModule(String moduleEid) {
        if (itemIdList.containsKey(moduleEid)) {
            return itemIdList.get(moduleEid);
        }
        return -1;
    }

    /*********************************************************************************
     *  Object that holds data for recyclerview item for DrawerAdapter
     ********************************************************************************/
    public static class DrawerItem implements Serializable {
        public enum ItemType {
            header,
            module,
            settings,
        }

        private ItemType itemType;
        private ModuleEntity moduleEntity;
        private String contentText;

        public DrawerItem(ItemType itemType) {
            this.itemType = itemType;
        }

        public DrawerItem(ItemType itemType, String contentText) {
            this.itemType = itemType;
            this.contentText = contentText;
        }

        public DrawerItem(ItemType itemType, ModuleEntity moduleEntity) {
            this.itemType = itemType;
            this.moduleEntity = moduleEntity;
        }

        public ItemType getItemType() {
            return itemType;
        }

        public ModuleEntity getModuleEntity() {
            return moduleEntity;
        }

        public String getContentText() {
            return contentText;
        }
    }

    public static class DrawerHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;

        public DrawerHeaderViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    public static class DrawerModuleViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public DrawerModuleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

}