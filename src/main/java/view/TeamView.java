package view;

import domain.piece.Team;

public enum TeamView {

    HAN("한"),
    CHO("초"),
    ;

    private final String title;

    TeamView(final String title) {
        this.title = title;
    }

    public static String title(final Team team) {
        return TeamView.valueOf(team.name()).title;
    }
}
