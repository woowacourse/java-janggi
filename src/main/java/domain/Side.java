package domain;

public enum Side {
    CHO("초"),
    HAN("한"),
    ;

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public boolean isAlly(Side other) {
        return this.equals(other);
    }

    public String getName() {
        return name;
    }
}
