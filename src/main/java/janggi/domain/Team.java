package janggi.domain;

public enum Team {
    RED("초"),
    GREEN("한"),
    ;

    private final String country;

    Team(final String country) {
        this.country = country;
    }

    public static int decideRow(final int row, final Team team) {
        if (team.isGreen()) {
            int rowFlipBase = 11;
            return rowFlipBase - row;
        }
        return row;
    }

    public boolean isRed() {
        return this == RED;
    }

    public boolean isGreen() {
        return this == GREEN;
    }

    public String getCountry() {
        return country;
    }
}
