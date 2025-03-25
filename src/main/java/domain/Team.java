package domain;

public enum Team {
    RED,
    GREEN;

    public static boolean isGreenTeam(final Team team) {
        return GREEN == team;
    }

    public static boolean isRedTeam(final Team team) {
        return RED == team;
    }

    public static Team opposite(final Team team) {
        if (isGreenTeam(team)) {
            return Team.RED;
        }
        return Team.GREEN;
    }
}
