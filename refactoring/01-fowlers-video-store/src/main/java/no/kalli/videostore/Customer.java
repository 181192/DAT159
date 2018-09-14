package no.kalli.videostore;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {
    private String _name;
    private ArrayList<Rental> _rentals = new ArrayList<>();

    public Customer(String name) {
        _name = name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Iterator<Rental> rentals = _rentals.iterator();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasNext()) {
            Rental each = rentals.next();
            int daysRented = each.getDaysRented();
            Movie movie = each.getMovie();

            frequentRenterPoints += movie.getFrequentRenterPoints(frequentRenterPoints, daysRented);

            String title = movie.getTitle();
            double thisAmount = movie.determineAmount(daysRented);
            result += printFiguresForRental(title, thisAmount);
            totalAmount += thisAmount;
        }
        result += getFooterLines(totalAmount, frequentRenterPoints, result);
        return result;
    }

    private String printFiguresForRental(String title, double thisAmount) {
        return ("\t" + title + "\t" + thisAmount + "\n");
    }

    private String getFooterLines(double totalAmount, int frequentRenterPoints, String result) {
        return result
                + "Amount owed is " + totalAmount + "\n"
                + "You earned " + frequentRenterPoints
                + " frequent renter points";
    }

    public void addRental(Rental arg) {
        _rentals.add(arg);
    }

    public String getName() {
        return _name;
    }
}
