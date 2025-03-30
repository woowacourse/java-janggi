package domain;

import domain.piece.TeamType;

public class GameStatus {
    private static final TeamType START_TEAM = TeamType.CHO;
    private final String roomName;
    private TeamType turn;

    public GameStatus(String roomName) {
        this.roomName = roomName;
        turn = START_TEAM;
    }

    public GameStatus(String roomName, TeamType turn) {
        this.roomName = roomName;
        this.turn = turn;
    }

    public void changeTurn() {
        turn = findNextTurn(turn);
    }

    public TeamType getTurn() {
        return turn;
    }

    public String getRoomName() {
        return roomName;
    }

    private TeamType findNextTurn(TeamType nowTurn) {
        if (nowTurn == TeamType.CHO) {
            return TeamType.HAN;
        }
        return TeamType.CHO;
    }
}
