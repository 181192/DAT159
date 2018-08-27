# Refactorings

## Extract methods

from 
```
result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
result += "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points";
```

to 

```
public String statement() {
    [...]
    result = addFooterLines(totalAmount, frequentRenterPoints, result);
    [...]
}

private String addFooterLines(double totalAmount, int frequentRenterPoints, String result) {
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points";
        return result;
}
```

from

```
// determine amount for each line
switch (each.getMovie().getPriceCode()) {
    case Movie.REGULAR:
        thisAmount += 2;
        if (each.getDaysRented() > 2)
            thisAmount += (each.getDaysRented() - 2) * 1.5;
        break;
    case Movie.NEW_RELEASE:
        thisAmount += each.getDaysRented() * 3;
        break;
    case Movie.CHILDRENS:
        thisAmount += 1.5;
        if (each.getDaysRented() > 3)
            thisAmount += (each.getDaysRented() - 3) * 1.5;
        break;
}
```

to 

```
thisAmount = determineAmount(thisAmount, each);


private double determineAmount(double thisAmount, Rental each) {
        // determine amount for each line
        switch (each.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += each.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
}

```

## Variable Extraction

from 
```
// add bonus for a two day new release rental
if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
        each.getDaysRented() > 1) frequentRenterPoints++;

//show figures for this rental
result += "\t" + each.getMovie().getTitle() + "\t" +
        String.valueOf(thisAmount) + "\n";
```


to 

```
// add bonus for a two day new release rental
Movie movie = each.getMovie();
if ((movie.getPriceCode() == Movie.NEW_RELEASE) &&
        each.getDaysRented() > 1) frequentRenterPoints++;

//show figures for this rental
result += "\t" + movie.getTitle() + "\t" +
        String.valueOf(thisAmount) + "\n";
totalAmount += thisAmount;
```

from
```

private double determineAmount(double thisAmount, Rental each) {
        // determine amount for each line
        switch (each.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += each.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }
```
to
```
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
```


## Simplification

from 

```
    private String addFooterLines(double totalAmount, int frequentRenterPoints, String result) {
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points";
        return result;
    }
```
to
```
    private String addFooterLines(double totalAmount, int frequentRenterPoints, String result) {
        return result + ("Amount owed is " + String.valueOf(totalAmount) + "\n" + "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points");
    }
```

## Extract method

from
```
 // add frequent renter points
frequentRenterPoints++;

// add bonus for a two day new release rental
Movie movie = each.getMovie();
if ((movie.getPriceCode() == Movie.NEW_RELEASE) &&
        each.getDaysRented() > 1) frequentRenterPoints++;
```

to 
```
frequentRenterPoints = getFrequentRenterPoints(frequentRenterPoints, each);

private int getFrequentRenterPoints(int frequentRenterPoints, Rental each) {
    Movie movie = each.getMovie();
    boolean isNewRelease = movie.getPriceCode() == Movie.NEW_RELEASE;
    boolean isRentedOneDay = each.getDaysRented() > 1;

    frequentRenterPoints++;

    if (isNewRelease && isRentedOneDay) frequentRenterPoints++;

    return frequentRenterPoints;
}

```

## Changed methods signatures

from
```
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
```

to
```
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
    result += "\t" + title + "\t" +
            String.valueOf(thisAmount) + "\n";
    totalAmount += thisAmount;
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


```

## Removed local variable

from
```
result = addFooterLines(totalAmount, frequentRenterPoints, result);
return result;
```

to
```
return addFooterLines(totalAmount, frequentRenterPoints, result);
```
