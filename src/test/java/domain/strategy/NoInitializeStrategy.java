package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import strategy.InitializeStrategy;

public class NoInitializeStrategy extends InitializeStrategy {
    @Override
    public Map<Position, Piece> initialize(Team team) {
        return new HashMap<>();
    }

    @Override
    public Map<Position, Piece> initializeElephantHorseFormation(Team team) {
        return new HashMap<>();
    }
}
