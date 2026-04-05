package application;

import domain.manager.GameManager;

public record GameSession(
    long gameId,
    GameManager gameManager
) {
}

