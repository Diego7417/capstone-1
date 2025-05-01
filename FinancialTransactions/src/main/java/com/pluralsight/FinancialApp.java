package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FinancialApp {
    private static final Scanner scanner = new Scanner(System.in);

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
//        promptReturnToMenu();
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

        LocalDate dateTime = LocalDate.now();

        try {
            FileWriter fileWriter = new FileWriter("data/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = String.format("%s|%s|%s|%.2f", dateTime, description, vendor, amount);
            bufferedWriter.write(line);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Deposit recorded successfully");

//        promptReturnToMenu();
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

        LocalDate dateTime = LocalDate.now();

        try {
            FileWriter fileWriter = new FileWriter("data/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = String.format("%s|%s|%s|%.2f", dateTime, description, vendor, amount);
            bufferedWriter.write(line);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Payment received");


//        promptReturnToMenu();
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
                    break;
                case 2:
                    displayDeposit();
                    break;
                case 3:
                    displayPayments();
                    break;
                case 4:
                    displayReports();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.Please try again");
            }
//        promptReturnToMenu();
        }

    }

    public static void displayAllTransactions(){

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
            fileReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public static void displayDeposit(){
        System.out.println("\n---Deposits---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split("\\|");
                if (parts.length >= 5){
                    double amount = Double.parseDouble(parts[4]);
                    if (amount >= 0){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void  displayPayments(){
        System.out.println("\n---Payments---");

        try {
            FileReader fileReader = new FileReader("data/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

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
    public static void displayReports(){
        System.out.println("\n---Run reports---");

    }

}
