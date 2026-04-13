package model.game;

import model.board.Board;

public record GameSession(Board board, JanggiGame game) {
}
