package com.gmaroko.laf.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmaroko.laf.R;
import com.gmaroko.laf.data.local.entity.Item;
import com.gmaroko.laf.ui.details.ItemDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList = new ArrayList<>();

    public void setItems(List<Item> items) {
        this.itemList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.title.setText(item.getTitle());
        holder.location.setText(item.getLocation());
        holder.date.setText(item.getDate());
        holder.type.setText(item.getType());
        holder.description.setText(item.getDescription());

        // Open detail activity on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
            intent.putExtra("item", item);
            v.getContext().startActivity(intent);
        });
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, location, date, type, description;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            location = itemView.findViewById(R.id.locationText);
            date = itemView.findViewById(R.id.dateText);
            type = itemView.findViewById(R.id.typeText);
            description = itemView.findViewById(R.id.descriptionText);
        }
    }
}