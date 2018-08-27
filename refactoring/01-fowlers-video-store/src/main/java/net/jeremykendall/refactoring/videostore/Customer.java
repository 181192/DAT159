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
            thisAmount = determineAmount(thisAmount, each);

            frequentRenterPoints = getFrequentRenterPoints(frequentRenterPoints, each);

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" +
                    String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        result = addFooterLines(totalAmount, frequentRenterPoints, result);
        return result;
    }

    private int getFrequentRenterPoints(int frequentRenterPoints, Rental each) {
        Movie movie = each.getMovie();
        boolean isNewRelease = movie.getPriceCode() == Movie.NEW_RELEASE;
        boolean isRentedOneDay = each.getDaysRented() > 1;

        frequentRenterPoints++;

        if (isNewRelease && isRentedOneDay) frequentRenterPoints++;

        return frequentRenterPoints;
    }

    private double determineAmount(double thisAmount, Rental each) {
        // determine amount for each line
        int daysRented = each.getDaysRented();
        switch (each.getMovie().getPriceCode()) {
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
