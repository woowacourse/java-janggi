package service;

import core.JanggiGame;

public record PreparedGame(Long gameId, JanggiGame game, MoveCount moveCount) {
}
