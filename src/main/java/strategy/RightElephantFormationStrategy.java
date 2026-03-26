package strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class RightElephantFormationStrategy extends InitializeStrategy {
    @Override
    public Map<Position, Piece> initializeElephantHorseFormation(Team team) {
        return new HashMap<>();
    }
}
