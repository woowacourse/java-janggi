package persistence;

import domain.GameSnapshot;
import domain.GameStatus;
import domain.GameDeadline;
import domain.TeamColor;
import java.util.Optional;

public record SavedGameState(
        GameSnapshot snapshot,
        TeamColor currentTurn,
        GameStatus gameStatus,
        Optional<TeamColor> winner,
        Optional<GameDeadline> deadline) {}
