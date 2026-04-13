package repository;

import domain.game.JanggiGame;

public record StoredGame(long id, JanggiGame game) {
}
