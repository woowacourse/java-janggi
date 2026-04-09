package janggi.domain.team;

public enum TeamType {
    RED("한나라", 1.5D),
    BLUE("초나라", 0D);

    private final String name;

    private final double scoreOffset;

    TeamType(final String name, final double scoreOffset) {
        this.name = name;
        this.scoreOffset = scoreOffset;
    }

    public String getName() {
        return name;
    }

    public double getScoreOffset() {
        return scoreOffset;
    }
}
