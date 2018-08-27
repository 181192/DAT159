package net.jeremykendall.refactoring.videostore;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";

        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();
            Movie movie = each.getMovie();
            int priceCode = movie.getPriceCode();
            int daysRented = each.getDaysRented();
            String title = movie.getTitle();

            thisAmount = determineAmount(thisAmount, priceCode, daysRented);
            frequentRenterPoints = getFrequentRenterPoints(frequentRenterPoints, priceCode, daysRented);

            //show figures for this rental
            result += "\t" + title + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        return addFooterLines(totalAmount, frequentRenterPoints, result);
    }

    private int getFrequentRenterPoints(int frequentRenterPoints, int priceCode, int daysRented) {
        boolean isNewRelease = priceCode == Movie.NEW_RELEASE;
        boolean isRentedOneDay = daysRented > 1;

        frequentRenterPoints++;

        if (isNewRelease && isRentedOneDay) frequentRenterPoints++;

        return frequentRenterPoints;
    }

    private double determineAmount(double thisAmount, int priceCode, int daysRented) {
        switch (priceCode) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (daysRented > 2)
                    thisAmount += (daysRented - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += daysRented * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if (daysRented > 3)
                    thisAmount += (daysRented - 3) * 1.5;
                break;
        }
        return thisAmount;
    }

    private String addFooterLines(double totalAmount, int frequentRenterPoints, String result) {
        return result + ("Amount owed is " + String.valueOf(totalAmount) + "\n" + "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points");
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }
}
