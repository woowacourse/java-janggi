package janggi.repository;

import janggi.domain.game.JanggiGame;

public record SavedGame(long id, JanggiGame janggiGame) {
}
