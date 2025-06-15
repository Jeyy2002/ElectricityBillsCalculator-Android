package com.example.electricitybillscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize TextViews
        TextView tvDetailMonth = findViewById(R.id.tvDetailMonth);
        TextView tvDetailUnitUsed = findViewById(R.id.tvDetailUnitUsed);
        TextView tvDetailRebate = findViewById(R.id.tvDetailRebate);
        TextView tvDetailTotalCharges = findViewById(R.id.tvDetailTotalCharges);
        TextView tvDetailFinalCost = findViewById(R.id.tvDetailFinalCost);

        // Get the BillRecord object passed from HistoryActivity
        BillRecord billRecord = (BillRecord) getIntent().getSerializableExtra("billRecord");

        if (billRecord != null) {
            // Format numbers to 2 decimal places
            DecimalFormat df = new DecimalFormat("0.00");

            // Populate TextViews with bill details
            tvDetailMonth.setText("Month: " + billRecord.getMonth());
            tvDetailUnitUsed.setText("Units Used: " + df.format(billRecord.getUnitUsed()) + " kWh");
            tvDetailRebate.setText("Rebate Percentage: " + df.format(billRecord.getRebatePercentage()) + "%");
            tvDetailTotalCharges.setText("Total Charges: RM " + df.format(billRecord.getTotalCharges()));
            tvDetailFinalCost.setText("Final Cost: RM " + df.format(billRecord.getFinalCost()));
        } else {
            Toast.makeText(this, "Error: Bill details not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close this activity if no data is found
        }
    }
}
