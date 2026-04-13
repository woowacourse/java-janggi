package model;

import model.board.Board;

public interface JanggiState {

    JanggiState next(Board board);

    Team resolveWinner(Board board);

    boolean canPlaying();

    GameStatus status();

    Team turn();
}
