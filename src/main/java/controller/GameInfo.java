package controller;

import domain.board.Board;
import domain.game.Turn;

public class GameInfo {
    private final Board board;
    private final Turn turn;
    private final int gameId;

    public GameInfo(Board board, Turn turn, int gameId) {
        this.board = board;
        this.turn = turn;
        this.gameId = gameId;
    }

    public Board board() {
        return board;
    }

    public Turn turn() {
        return turn;
    }

    public int gameId() {
        return gameId;
    }
}
