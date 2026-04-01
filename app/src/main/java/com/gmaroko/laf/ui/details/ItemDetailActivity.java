package com.gmaroko.laf.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gmaroko.laf.R;
import com.gmaroko.laf.data.local.entity.Item;
import com.gmaroko.laf.viewmodel.ItemViewModel;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView titleText, descriptionText, locationText, dateText, typeText, statusText;
    private Button btnMarkResolved;
    private ItemViewModel viewModel;
    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        titleText = findViewById(R.id.titleText);
        descriptionText = findViewById(R.id.descriptionText);
        locationText = findViewById(R.id.locationText);
        dateText = findViewById(R.id.dateText);
        typeText = findViewById(R.id.typeText);
        statusText = findViewById(R.id.statusText);
        btnMarkResolved = findViewById(R.id.btnMarkResolved);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        // Get item from intent
        Intent intent = getIntent();
        currentItem = (Item) intent.getSerializableExtra("item");

        if(currentItem != null) {
            displayItem(currentItem);
        }

        btnMarkResolved.setOnClickListener(v -> markAsResolved());
    }

    private void displayItem(Item item) {
        titleText.setText(item.getTitle());
        descriptionText.setText(item.getDescription());
        locationText.setText("Location: " + item.getLocation());
        dateText.setText("Date: " + item.getDate());
        typeText.setText("Type: " + item.getType());
        statusText.setText("Status: " + item.getStatus());

        btnMarkResolved.setEnabled(!item.getStatus().equals("resolved"));
    }

    private void markAsResolved() {
        if(currentItem != null) {
            viewModel.updateStatus(currentItem.getId(), "resolved");
            Toast.makeText(this, "Item marked as resolved!", Toast.LENGTH_SHORT).show();
            finish(); // go back to HomeActivity
        }
    }
}