package domain.constant;

public enum Turn {
    CHO, HAN;

    public Turn changeTurn() {
        if (this.equals(CHO)) {
            return HAN;
        }
        return CHO;
    }

    public Country getCountry() {
        return Country.valueOf(this.name());
    }

    public static Turn from(Country country) {
        return Turn.valueOf(country.name());
    }
}
