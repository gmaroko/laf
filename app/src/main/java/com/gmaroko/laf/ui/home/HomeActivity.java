package com.gmaroko.laf.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmaroko.laf.R;
import com.gmaroko.laf.ui.additem.AddItemActivity;
import com.gmaroko.laf.ui.auth.LoginActivity;
import com.gmaroko.laf.viewmodel.ItemViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private ItemViewModel viewModel;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        viewModel.getAllItems().observe(this, items -> {
            adapter.setItems(items);
        });

        boolean isGuest = getIntent().getBooleanExtra("guest", false);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        if (isGuest) {
            fab.setOnClickListener(v -> {
                Toast.makeText(this, "Login required to add items", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            });
        } else {
            fab.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, AddItemActivity.class));
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        boolean loggedIn = auth.getCurrentUser() != null;

        menu.findItem(R.id.menu_logout).setVisible(loggedIn);
        menu.findItem(R.id.menu_my_listings).setVisible(loggedIn);
        menu.findItem(R.id.menu_login).setVisible(!loggedIn);
        menu.findItem(R.id.menu_register).setVisible(!loggedIn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_my_listings) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                viewModel.getItemsByUser(auth.getCurrentUser().getUid())
                        .observe(this, items -> adapter.setItems(items));
            } else {
                Toast.makeText(this, "Login required to view your listings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, com.gmaroko.laf.ui.auth.LoginActivity.class));
            }
            return true;
        } else if (id == R.id.menu_all_items) {
            viewModel.getAllItems().observe(this, items -> adapter.setItems(items));
            return true;
        } else if (id == R.id.menu_login) {
            startActivity(new Intent(this, com.gmaroko.laf.ui.auth.LoginActivity.class));
            return true;
        } else if (id == R.id.menu_register) {
            startActivity(new Intent(this, com.gmaroko.laf.ui.auth.RegisterActivity.class));
            return true;
        } else if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, com.gmaroko.laf.ui.auth.LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}