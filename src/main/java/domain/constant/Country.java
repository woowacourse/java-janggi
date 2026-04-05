package domain.constant;

public enum Country {
    CHO("초나라"),
    HAN("한나라"),
    NONE("");

    private final String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Country getCountry(String name) {
        return Country.valueOf(name);
    }
}
