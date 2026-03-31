package strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class CustomInitializeStrategy extends InitializeStrategy{

    private final Map<Position, Piece> customPieces;

    public CustomInitializeStrategy(Map<Position, Piece> customPieces) {
        this.customPieces = customPieces;
    }

    @Override
    public Map<Position, Piece> initialize(Team team) {
        return customPieces;
    }


    @Override
    protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
        return Map.of();
    }

}
