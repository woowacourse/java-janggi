package service;

import domain.board.Board;
import domain.board.GameStatus;

public record GameData(Board board, GameStatus gameStatus) {
}
