package persistence;

import domain.GameSnapshot;
import domain.GameStatus;
import domain.TeamColor;
import java.util.Optional;

public record SavedGameState(
        GameSnapshot snapshot, TeamColor currentTurn, GameStatus gameStatus, Optional<TeamColor> winner) {}
