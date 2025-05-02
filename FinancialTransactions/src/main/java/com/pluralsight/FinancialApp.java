package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FinancialApp {
    private static final Scanner scanner = new Scanner(System.in);
    DateTimeFormatter format;

    {
        format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args) {

        boolean running = true;
        while (running) {

            displayHomeScreen();
            int displayHomeScreen = scanner.nextInt();
            scanner.nextLine();

            switch (displayHomeScreen) {
                case 1:
                    makeDeposit();
                    break;
                case 2:
                    makeAPayment();
                    break;
                case 3:
                    viewLedger();
                    break;
                case 4:
                    System.out.println("Have a great day :)");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again");


            }

        }

    }


    private static void displayHomeScreen() {
        System.out.println("\nMain Menu");
        System.out.println("1. Make a Deposit");
        System.out.println("2. Make a Payment");
        System.out.println("3. View Ledger");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

    }

    private static void promptReturnToMenu() {
        System.out.println("\nPress Enter to Return to the Main Menu");
        scanner.nextLine();

    }

    public static void makeDeposit() {
        System.out.println("\nDeposit");


        System.out.println("Enter Description");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String date = dateTime.toLocalDate().toString();
        String time = dateTime.toLocalTime().withNano(0).toString();





        try {
            FileWriter fileWriter = new FileWriter("data/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);
            bufferedWriter.newLine();
            bufferedWriter.write(line);


            bufferedWriter.close();
            System.out.println("Deposit recorded successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        promptReturnToMenu();
    }

    public static void makeAPayment() {
        System.out.println("\nMake Payment");

        System.out.println("Enter Description");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor");
        String vendor = scanner.nextLine();

        System.out.println("Enter Amount");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        amount = -Math.abs(amount);

        LocalDateTime dateTime = LocalDateTime.now();

        String date = dateTime.toLocalDate().toString();
        String time = dateTime.toLocalTime().withNano(0).toString();

        try {
            FileWriter fileWriter = new FileWriter("data/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);
            bufferedWriter.newLine();
            bufferedWriter.write(line);


            bufferedWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Payment received");


        promptReturnToMenu();
    }

    public static void viewLedger() {
        System.out.println("---View Ledger Menu---");
        System.out.println("Display All");
        System.out.println("Display Deposit");
        System.out.println("Display Payments");
        System.out.println("Display Reports");
        System.out.println("Go Home");
        System.out.println("Enter your choice");


        boolean running = true;

        while (running) {


            int displayLedger = scanner.nextInt();
            scanner.nextLine();

            switch (displayLedger) {
                case 1:
                    displayAllTransactions();
                    promptReturnToMenu();
                    break;
                case 2:
                    displayDeposit();
                    promptReturnToMenu();
                    break;
                case 3:
                    displayPayments();
                    promptReturnToMenu();
                    break;
                case 4:
                    displayReports();
                    promptReturnToMenu();
                    break;
                case 5:
                    running = false;
                    promptReturnToMenu();
                    break;
                default:
                    System.out.println("Invalid option.Please try again");
            }


        }


    }

    public static void displayAllTransactions() {
        System.out.println("\n---All Transactions---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\|");

                LocalDateTime dateTime = LocalDateTime.parse(tokens[0] + "T" + tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                Ledger ledger = new Ledger(dateTime, description, vendor, amount);
                System.out.println(ledger.display());

            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void displayDeposit() {
        System.out.println("\n---Deposits---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    double amount = Double.parseDouble(parts[4]);
                    if (amount >= 0) {
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayPayments() {
        System.out.println("\n---Payments---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    double amount = Double.parseDouble(parts[4]);
                    if (amount < 0) {
                        System.out.println(line);
                    }

                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayReports() {
        boolean running = true;

        while (running) {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Month To Date");
            System.out.println("2. Previous Month");
            System.out.println("3. Year To Date");
            System.out.println("4. Previous Year");
            System.out.println("5. Search by Vendor");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    filterByMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
                    break;
                case 2:
                    LocalDate lastMonth = LocalDate.now().minusMonths(1);
                    filterByMonth(lastMonth.getYear(), lastMonth.getMonthValue());
                    break;
                case 3:
                    filterByYear(LocalDate.now().getYear());
                    break;
                case 4:
                    filterByYear(LocalDate.now().getYear() - 1);
                    break;
                case 5:
                    System.out.print("Enter vendor name to search: ");
                    String vendor = scanner.nextLine();
                    filterByVendor(vendor);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            promptReturnToMenu();
        }
    }
    public static void filterByMonth(int year, int month){
        System.out.println("\n--- Transactions for " + year + "-" + String.format("%02d", month) + " ---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split("\\|");
                if (parts.length >= 5){
                    LocalDate date = LocalDate.parse(parts[0]);
                    if (date.getYear() == year && date.getMonthValue() == month){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void filterByYear(int year){
        System.out.println("\n--- Transactions for Year: " + year + " ---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split("\\|");
                if (parts.length >= 5){
                    LocalDate date = LocalDate.parse(parts[0]);

                    if (date.getYear() == year){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void filterByVendor(String vendorSearch){
        System.out.println("\n--- Transactions for Vendor: " + vendorSearch + " ---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split("\\|");
                if (parts.length >= 5){
                   String vendor = parts[3];

                    if (vendor.equalsIgnoreCase(vendorSearch)){
                        System.out.println(line);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }


}

