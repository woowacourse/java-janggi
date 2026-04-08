package application;

import domain.game.JanggiGame;
import java.time.LocalDateTime;

public record GameSession(
        Long gameId,
        LocalDateTime createdAt,
        JanggiGame game
) {
}
