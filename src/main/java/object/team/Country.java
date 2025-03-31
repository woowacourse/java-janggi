package object.team;

import java.util.List;

public enum Country {

    HAN("한", "\u001B[31m", List.of(8, 7, 3, 2), 1),
    CHO("초", "\u001B[34m", List.of(2, 3, 7, 8), 10),
    ;

    private static final String RESET = "\u001B[0m";

    private final String teamName;
    private final String colorCode;
    private final List<Integer> maSangXCoordinates;
    private final int maSangYCoordinate;

    Country(String teamName, String colorCode, List<Integer> maSangXCoordinates, int maSangYCoordinate) {
        this.teamName = teamName;
        this.colorCode = colorCode;
        this.maSangXCoordinates = maSangXCoordinates;
        this.maSangYCoordinate = maSangYCoordinate;
    }

    public boolean isSameCountry(Country country) {
        return this.equals(country);
    }

    public Country nextCountry() {
        Country[] countries = Country.values();
        int nextIndex = (this.ordinal() + 1) % countries.length;
        return countries[nextIndex];
    }

    public String applyColor(String text) {
        return this.colorCode + text + RESET;
    }

    public String applyColorCountryName() {
        return applyColor(this.teamName);
    }

    public List<Integer> getMaSangXCoordinates() {
        return maSangXCoordinates;
    }

    public int getMaSangYCoordinate() {
        return maSangYCoordinate;
    }
}
