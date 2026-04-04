package repository.entity;

public record GameContext(
        Long gameContextId,
        String currentTurnOwnTeam,
        String gameState
) {
}
