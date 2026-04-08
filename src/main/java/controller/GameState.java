package controller;

import domain.board.Board;
import domain.game.Turn;

public record GameState(Board board, Turn turn, int gameId) {
}
