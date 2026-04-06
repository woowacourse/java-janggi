package persistence;

import domain.GameSnapshot;
import domain.TeamColor;

public record SavedGameState(GameSnapshot snapshot, TeamColor currentTurn) {}
