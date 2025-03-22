package domain;

public enum Country {
    HAN("한나라"),
    CHO("초나라"),
    ;

    private String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
