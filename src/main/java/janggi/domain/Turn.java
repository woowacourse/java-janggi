package janggi.domain;

import janggi.domain.side.TeamType;

public class Turn {

    private final TeamType movedTeam;
    private final Board board;

    public Turn(TeamType movedTeam, Board board) {
        this.movedTeam = movedTeam;
        this.board = board;
    }
}
