package domain;

public enum Country {
    HAN("한"),
    CHO("초"),
    ;

    private String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
