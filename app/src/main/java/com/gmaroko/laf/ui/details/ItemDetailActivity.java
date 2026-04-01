package com.gmaroko.laf.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gmaroko.laf.R;
import com.gmaroko.laf.data.local.entity.Item;
import com.gmaroko.laf.viewmodel.ItemViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView titleText, descriptionText, locationText, dateText, typeText, statusText, postedByText;
    private Button btnMarkResolved, btnContactOwner;
    private ItemViewModel viewModel;
    private Item currentItem;
    private FirebaseAuth auth;

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
        postedByText = findViewById(R.id.logByText);
        btnMarkResolved = findViewById(R.id.btnMarkResolved);
        btnContactOwner = findViewById(R.id.btnContactOwner);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        auth = FirebaseAuth.getInstance();

        // Get item from intent
        Intent intent = getIntent();
        currentItem = (Item) intent.getSerializableExtra("item");

        if (currentItem != null) {
            displayItem(currentItem);
        }

        btnMarkResolved.setOnClickListener(v -> markAsResolved());
        btnContactOwner.setOnClickListener(v -> contactOwner());
    }

    private void displayItem(Item item) {
        titleText.setText(item.getTitle());
        descriptionText.setText(item.getDescription());
        locationText.setText("Location: " + item.getLocation());
        dateText.setText("Date: " + item.getDate());
        typeText.setText("Type: " + item.getType());
        statusText.setText("Status: " + item.getStatus());
        postedByText.setText("Posted by: " + (item.getUserEmail() != null ? item.getUserEmail() : "Guest"));

        btnMarkResolved.setEnabled(!"resolved".equals(item.getStatus()));
    }

    private void markAsResolved() {
        if (currentItem != null && currentItem.getDocId() != null) {
            viewModel.updateStatus(currentItem.getDocId(), "resolved");
            Toast.makeText(this, "Item marked as resolved!", Toast.LENGTH_SHORT).show();
            finish(); // go back to HomeActivity
        }
    }

    private void contactOwner() {
        if (currentItem != null && currentItem.getUserEmail() != null && !"guest".equals(currentItem.getUserId())) {
            if (auth.getCurrentUser() != null) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + currentItem.getUserEmail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Regarding your lost/found item: " + currentItem.getTitle());
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            } else {
                Toast.makeText(this, "Please login to contact the item owner", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, com.gmaroko.laf.ui.auth.LoginActivity.class));
            }
        } else {
            Toast.makeText(this, "Owner contact not available", Toast.LENGTH_SHORT).show();
        }
    }
}
