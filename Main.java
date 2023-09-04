// creating a calculator that asks user for:
// 1. Principal (amount of loan user wants to get)
// 2. Annual Interest Rate (double) ( need to divide by 100 then by 12 to get monthly interest rate)
// 3. Period (years, int)
// n = number of payments ( multiply  period in years by 12 ) ( use Math.pow in math class for this)
// program should display mortgage monthly payments in dollars and create a payment schedule based off loan balance

//formula to calculate: Mortgage = (Principal) * (monthly interest rate + 1)^n / (1 + monthy interest rate)^n -1

import java.text.NumberFormat;
import java.util.Scanner;

public class Main { // this is the class with several methods

    public static void main(String[] args) {

        long loanAmount = (long) readNumber("Principal ($1k - $1M): ", 1_000, 1_000_000);
        double annualInterestRate = readNumber("Annual Interest Rate: ", 1, 30);
        byte yearsOfPayment = (byte) readNumber("Period (Years): ", 1, 30);

        double result = calculateMortgage(loanAmount, annualInterestRate, yearsOfPayment);

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String money = currency.format(result); // money is the same as result just displayed as a currency
        System.out.println("Monthly Payments: " + money);

        calculatePaymentSchedule(loanAmount, annualInterestRate, yearsOfPayment);
    }

    public static double readNumber(String prompt, int min, int max ) {
        Scanner scan =  new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            value= scan.nextDouble();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a number between " + min + " and " + max);
        }
        return value;
    }

    public static double calculateMortgage(
            long loanAmount,
            double annualInterestRate,
            byte year) {
       // Mortgage = (Principal) * (monthly interest rate + 1)^n / (1 + monthy interest rate)^n -1

        double monthlyInterestRate = (annualInterestRate/100) / 12; // this gives me monthly interest rate
        int numberOfPayments = year * 12; // this gives me number of payments

        double topOfFormula = Math.pow((monthlyInterestRate+1), numberOfPayments) * monthlyInterestRate;
        double bottomOfFormula = Math.pow((monthlyInterestRate+1), numberOfPayments) - 1;
        double result = (loanAmount) * (topOfFormula/bottomOfFormula);

        System.out.println(); // make an extra space thats all
        System.out.println("MORTGAGE");
        System.out.println("________");

        return result;
    }

    // payment schedule: formula for calculating loan balance(B) of a payment loan after p months:
    // B = L[(1 + c)^n - (1 + c)^p] / [(1 + c)^n - 1]
    // L = loan amount, c = monthly interest, n = number of payments, p = number of payments made
    public static void calculatePaymentSchedule(
            long loanAmount,
            double annualInterestRate,
            byte yearsOfPayments) { // money is my monthly payment, loan is total loan amount that we will be deducting

        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("________________");

        double remainingPayment = loanAmount; // loan balance
        double monthlyInrestRate = (annualInterestRate/100) / 12; // monthly interest
        int numberOfPayments = yearsOfPayments * 12; // number of payments

        for (int p = 1; p <= numberOfPayments; p++){
            double topOfEquation = (Math.pow((1 + monthlyInrestRate), numberOfPayments) - Math.pow((1 + monthlyInrestRate), p));
            topOfEquation *= loanAmount;
            double bottomOfEquation = Math.pow((1 + monthlyInrestRate), numberOfPayments) - 1;
            remainingPayment = topOfEquation / bottomOfEquation;


            NumberFormat currency = NumberFormat.getCurrencyInstance();
            String dough = currency.format(remainingPayment);
            System.out.println(dough);
        }

    }
}