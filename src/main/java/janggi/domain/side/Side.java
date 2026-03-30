package janggi.domain.side;

public enum Side {
    HAN("한"),
    CHO("초"),
    ;

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public static boolean isSameSide(Side firstSide, Side secondSide) {
        return firstSide.equals(secondSide);
    }

    public String getName() {
        return name;
    }
}
