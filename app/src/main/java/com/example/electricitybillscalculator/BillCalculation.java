package com.example.electricitybillscalculator;

public class BillCalculation {

    public static double calculateTotalCharges(double unitUsed) {
        double totalCharges;

        if (unitUsed <= 200) {
            totalCharges = unitUsed * 0.218;
        } else if (unitUsed <= 300) {
            totalCharges = (200 * 0.218) + ((unitUsed - 200) * 0.334);
        } else if (unitUsed <= 600) {
            totalCharges = (200 * 0.218) + (100 * 0.334) + ((unitUsed - 300) * 0.516);
        } else {
            totalCharges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((unitUsed - 600) * 0.546);
        }

        return totalCharges;
    }

    public static double calculateFinalCost(double totalCharges, double rebatePercentage) {
        return totalCharges - (totalCharges * (rebatePercentage / 100.0));
    }
}
