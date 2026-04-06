package persistence;

import domain.GameDeadline;
import domain.GameSnapshot;
import domain.GameStatus;
import domain.TeamColor;

public record SaveGameStateRequest(
        GameSnapshot snapshot,
        TeamColor currentTurn,
        GameStatus gameStatus,
        TeamColor winner,
        GameDeadline deadline) {}

