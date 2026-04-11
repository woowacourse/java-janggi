package janggi.infrastructure.dao.dto;

public record GameSaveRequest(
        String choName,
        String hanName,
        String choFormation,
        String hanFormation,
        String currentTurn
) {}
