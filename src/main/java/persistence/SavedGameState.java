package persistence;

import domain.GameSnapshot;
import domain.GameStatus;
import domain.GameDeadline;
import domain.TeamColor;

public record SavedGameState(
        GameSnapshot snapshot,
        TeamColor currentTurn,
        GameStatus gameStatus,
        TeamColor winner,
        GameDeadline deadline) {}
