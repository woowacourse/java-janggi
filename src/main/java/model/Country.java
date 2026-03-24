package model;

public enum Country {
    HAN("한"), CHO("초");

    private final String country;
    Country(String country) {
        this.country = country;
    }

    public String country() {
        return country;
    }
}
