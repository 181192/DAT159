## Refactorings

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