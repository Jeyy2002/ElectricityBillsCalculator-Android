package com.example.electricitybillscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerMonth;
    private TextInputEditText etUnitUsed;
    private RadioGroup rgRebate;
    private TextView tvTotalCharges;
    private TextView tvFinalCost;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        etUnitUsed = findViewById(R.id.etUnitUsed);
        rgRebate = findViewById(R.id.rgRebate);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        tvTotalCharges = findViewById(R.id.tvTotalCharges);
        tvFinalCost = findViewById(R.id.tvFinalCost);
        Button btnViewHistory = findViewById(R.id.btnViewHistory);
        Button btnAbout = findViewById(R.id.btnAbout);

        databaseHelper = new DatabaseHelper(this);

        // Set OnClickListener for Calculate Button
        btnCalculate.setOnClickListener(v -> calculateBill());

        // Set OnClickListener for View History Button
        btnViewHistory.setOnClickListener(v -> {
            // Start HistoryActivity
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for About Button
        btnAbout.setOnClickListener(v -> {
            // Start AboutActivity
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Calculates the electricity bill based on user input,
     * validates inputs, displays results, and saves to the database.
     */
    private void calculateBill() {
        // 1. Get Month
        String selectedMonth = spinnerMonth.getSelectedItem().toString();

        // 2. Get Units Used
        String unitUsedStr = Objects.requireNonNull(etUnitUsed.getText()).toString().trim();
        if (unitUsedStr.isEmpty()) {
            etUnitUsed.setError("Units Used cannot be empty.");
            Toast.makeText(this, "Please enter units used.", Toast.LENGTH_SHORT).show();
            return;
        }
        double unitUsed = Double.parseDouble(unitUsedStr);
        if (unitUsed < 0) {
            etUnitUsed.setError("Units Used cannot be negative.");
            Toast.makeText(this, "Please enter a valid positive number for units used.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Get Rebate Percentage
        int selectedRebateId = rgRebate.getCheckedRadioButtonId();
        double rebatePercentage;
        if (selectedRebateId != -1) {
            RadioButton selectedRebateRb = findViewById(selectedRebateId);
            String rebateText = selectedRebateRb.getText().toString().replace("%", "");
            rebatePercentage = Double.parseDouble(rebateText);
        } else {
            // This case should ideally not happen if a default is checked
            Toast.makeText(this, "Please select a rebate percentage.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. Calculate Total Charges using BillCalculation class
        double totalCharges = BillCalculation.calculateTotalCharges(unitUsed);

        // 5. Calculate Final Cost using BillCalculation class
        double finalCost = BillCalculation.calculateFinalCost(totalCharges, rebatePercentage);

        // Format outputs to 2 decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedTotalCharges = "RM " + df.format(totalCharges);
        String formattedFinalCost = "RM " + df.format(finalCost);

        // Display outputs
        tvTotalCharges.setText(formattedTotalCharges);
        tvFinalCost.setText(formattedFinalCost);

        // 6. Save data to database
        BillRecord newRecord = new BillRecord(selectedMonth, unitUsed, rebatePercentage, totalCharges, finalCost);
        boolean isInserted = databaseHelper.addBillRecord(newRecord);

        if (isInserted) {
            Toast.makeText(this, "Bill saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save bill.", Toast.LENGTH_SHORT).show();
        }
    }
}
