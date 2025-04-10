package janggi.game;

import janggi.board.Board;
import janggi.team.Team;

public class JanggiGame {
    private final GameRoom gameRoom;
    private final Board board;

    public JanggiGame(GameRoom gameRoom, Board board) {
        this.gameRoom = gameRoom;
        this.board = board;
    }
}
