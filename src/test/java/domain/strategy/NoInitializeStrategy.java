package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import strategy.InitializeStrategy;

public class NoInitializeStrategy extends InitializeStrategy {
    @Override
    protected Map<Position, Piece> initializeDefaultFormation(Team team) {
        return Map.of();
    }
    @Override
    protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
        return Map.of();
    }
}
