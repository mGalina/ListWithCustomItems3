package com.example.listwithcustomitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ItemsDataAdapter extends BaseAdapter {
    private List<ItemData> items;
    private LayoutInflater inflater;
    private RemoveItemClickListener removeItemClickListener;
    // Чтобы при скролле не создавать лишние объекты. В джаве так лучше
    private View.OnClickListener removeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeItemClickListener.onRemoveItemClicked((int) v.getTag());
        }
    };

    ItemsDataAdapter(Context context, List<ItemData> items, RemoveItemClickListener removeItemClickListener) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        this.removeItemClickListener = removeItemClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(ItemData item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        ItemData itemData = items.get(position);

        ImageView image = view.findViewById(R.id.icon);

        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        Button delete = view.findViewById(R.id.btn_delete);
        Button add = view.findViewById(R.id.btn_add);


        delete.setOnClickListener(removeButtonClickListener);
        delete.setTag(position);

        image.setImageDrawable(itemData.getImage());
        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());
        add.setText(itemData.getUserText());

        return view;
    }
}