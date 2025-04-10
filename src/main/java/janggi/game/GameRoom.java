package janggi.game;

import janggi.board.Turn;
import janggi.piece.Piece;
import janggi.team.Team;

public class GameRoom {

    private final String roomName;
    private Turn turn;

    public GameRoom(String roomName, Turn turn) {
        this.roomName = roomName;
        this.turn = turn;
    }

    public void checkTurn(Piece attacker) {
        turn.checkTurn(attacker);
    }

    public Turn turnOver() {
        return turn = turn.turnOver();
    }

    public String getRoomName() {
        return roomName;
    }

    public Team getTurnTeam() {
        return turn.getTurn();
    }

}
