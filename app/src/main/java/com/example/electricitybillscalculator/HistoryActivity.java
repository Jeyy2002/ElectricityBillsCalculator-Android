package com.example.electricitybillscalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBills;
    private TextView tvNoRecords;
    private BillAdapter billAdapter;
    private DatabaseHelper databaseHelper;
    private List<BillRecord> billRecordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerViewBills = findViewById(R.id.recyclerViewBills);
        tvNoRecords = findViewById(R.id.tvNoRecords);

        databaseHelper = new DatabaseHelper(this);

        // Set up RecyclerView
        recyclerViewBills.setLayoutManager(new LinearLayoutManager(this));

        loadBillRecords();
    }

    /**
     * Loads all bill records from the database and displays them in the RecyclerView.
     */
    private void loadBillRecords() {
        billRecordsList = databaseHelper.getAllBillRecords();

        if (billRecordsList.isEmpty()) {
            // Show "No records" message if the list is empty
            tvNoRecords.setVisibility(View.VISIBLE);
            recyclerViewBills.setVisibility(View.GONE);
            Toast.makeText(this, "No bill records found.", Toast.LENGTH_SHORT).show();
        } else {
            // Hide "No records" message and show RecyclerView
            tvNoRecords.setVisibility(View.GONE);
            recyclerViewBills.setVisibility(View.VISIBLE);

            // Initialize and set the adapter
            billAdapter = new BillAdapter(this, billRecordsList);
            recyclerViewBills.setAdapter(billAdapter);

            // Set item click listener for the adapter
            billAdapter.setOnItemClickListener(new BillAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BillRecord billRecord) {
                    // Handle item click: open DetailActivity
                    Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                    // Pass the BillRecord object to the DetailActivity
                    intent.putExtra("billRecord", billRecord);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        loadBillRecords();
    }
}