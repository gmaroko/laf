package com.gmaroko.laf.ui.additem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gmaroko.laf.R;
import com.gmaroko.laf.data.local.entity.Item;
import com.gmaroko.laf.viewmodel.ItemViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AddItemActivity extends AppCompatActivity {

    private EditText titleInput, descriptionInput, locationInput, dateInput;
    private Spinner typeSpinner;
    private Button saveButton;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        locationInput = findViewById(R.id.locationInput);
        dateInput = findViewById(R.id.dateInput);
        typeSpinner = findViewById(R.id.typeSpinner);
        saveButton = findViewById(R.id.saveButton);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        saveButton.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String type = typeSpinner.getSelectedItem().toString();
        String status = "active";

        if(title.isEmpty() || description.isEmpty() || location.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "guest";
        String userEmail = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "guest";

        Item newItem = new Item(title, description, location, date, type, "", "active", userId, userEmail);
        viewModel.insert(newItem);

        Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show();
        finish(); // go back to HomeActivity
    }
}