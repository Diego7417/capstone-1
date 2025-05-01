package com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ledger {
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    public Ledger(LocalDateTime dateTime, String description, String vendor, double amount) {
        this.dateTime = LocalDateTime.now();
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {

        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {

        this.dateTime = dateTime;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String display() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd, yyyy hh:mm a");
        StringBuilder builder = new StringBuilder();
        builder.append(dateTime.format(format)).append("\t").append(vendor).append(" minutes").append("\n")
                .append(description).append("\n")
                .append("__________________________________________________________________________");
        return builder.toString();

    }

    @Override
    public String toString() {
        return "Ledger{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
