package service;

import domain.manager.GameManager;

public record JanggiGameSession(
    long gameId,
    GameManager gameManager
) {
}

