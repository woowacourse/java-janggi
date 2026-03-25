package model;

public enum Country {
    HAN("한"), CHO("초");

    private final String title;

    Country(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
