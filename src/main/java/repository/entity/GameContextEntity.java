package repository.entity;

public record GameContextEntity(
        Long gameContextId,
        String currentTurnOwnTeam,
        String gameState
) {
}
