package com.example.electricitybillscalculator;

import java.io.Serializable;

public class BillRecord implements Serializable {
    private int id;
    private String month;
    private double unitUsed;
    private double rebatePercentage;
    private double totalCharges;
    private double finalCost;

    public BillRecord(String month, double unitUsed, double rebatePercentage, double totalCharges, double finalCost) {
        this.month = month;
        this.unitUsed = unitUsed;
        this.rebatePercentage = rebatePercentage;
        this.totalCharges = totalCharges;
        this.finalCost = finalCost;
    }

    public BillRecord(int id, String month, double unitUsed, double rebatePercentage, double totalCharges, double finalCost) {
        this.id = id;
        this.month = month;
        this.unitUsed = unitUsed;
        this.rebatePercentage = rebatePercentage;
        this.totalCharges = totalCharges;
        this.finalCost = finalCost;
    }

    public int getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }

    public double getUnitUsed() {
        return unitUsed;
    }

    public double getRebatePercentage() {
        return rebatePercentage;
    }

    public double getTotalCharges() {
        return totalCharges;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setUnitUsed(double unitUsed) {
        this.unitUsed = unitUsed;
    }

    public void setRebatePercentage(double rebatePercentage) {
        this.rebatePercentage = rebatePercentage;
    }

    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }
}