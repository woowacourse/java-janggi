package janggi.infrastructure.dao.dto;

public record GameDto(
        Long id,
        String choPlayerName,
        String hanPlayerName,
        String currentTurn,
        boolean isPlaying
) {}
