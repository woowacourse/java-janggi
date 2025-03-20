package piece;

public enum TeamType {
    RED, BLUE;

    public static TeamType changeTeamType(final TeamType teamType) {
        if (teamType == BLUE) {
            return RED;
        }
        return BLUE;
    }
}
