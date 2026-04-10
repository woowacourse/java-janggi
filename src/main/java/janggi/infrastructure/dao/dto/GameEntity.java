package janggi.infrastructure.dao.dto;

public record GameEntity(
        Long id,
        String choPlayerName,
        String hanPlayerName,
        String choFormation,
        String hanFormation,
        String currentTurn,
        boolean isPlaying
) {}
