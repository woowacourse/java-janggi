package janggi.domain.repository;

public record GameInfo(
        Long id,
        String choPlayerName,
        String hanPlayerName,
        String currentTurn
) {}
