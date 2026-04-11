package service;

import domain.manager.JanggiGameManager;

public record JanggiGameSession(
        long gameId,
        JanggiGameManager janggiGameManager
) {
}

