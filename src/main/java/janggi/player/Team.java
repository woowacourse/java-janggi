package janggi.player;

public enum Team {
    HAN("한"), // RED
    CHO("초"), // GREEN
    ;

    private final String description;

    Team(final String description) {
        this.description = description;
    }

    public static int decideRow(final int row, final Team team) {
        if (team.isCho()) {
            final int sumOfEachTeamPieceRow = 11;
            return sumOfEachTeamPieceRow - row;
        }
        return row;
    }

    public static Team from(final String team) {
        return Team.valueOf(team);
    }

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public String getDescription() {
        return description;
    }
}
