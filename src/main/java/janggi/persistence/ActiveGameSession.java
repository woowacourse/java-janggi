package janggi.persistence;

import janggi.domain.game.GameManager;

public record ActiveGameSession(long gameId, GameManager gameManager) {
}
