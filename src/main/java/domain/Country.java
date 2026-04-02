package domain;

public class Country {
    private final CountryType countryType;
    private double score;

    public Country(CountryType countryType, double score) {
        this.countryType = countryType;
        this.score = score;
    }

    public void minusScore(double pieceScore) {
        score -= pieceScore;
    }

    public CountryType getCountryType() {
        return countryType;
    }

    public double getScore() {
        return score;
    }
}
