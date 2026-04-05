package dao;

public record GameInfo(
    long gameId,
    String choName,
    String hanName,
    String currentTurn
) {}

