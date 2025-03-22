package domain.piece;

import domain.TeamType;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class PieceFactory {

    public Map<Position, Piece> createAllPieces(HorseElephantSetupStrategy choStrategy,
                                                HorseElephantSetupStrategy hanStrategy) {
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.putAll(choStrategy.createElephantHorse(TeamType.CHO));
        allPieces.putAll(hanStrategy.createElephantHorse(TeamType.HAN));
        allPieces.putAll(createHanDefaultPieces());
        allPieces.putAll(createChoDefaultPieces());

        return allPieces;
    }

    private Map<Position, Piece> createHanDefaultPieces() {
        return Map.ofEntries(
                Map.entry(Position.of(6, 0), new Soldier(TeamType.HAN)),
                Map.entry(Position.of(6, 2), new Soldier(TeamType.HAN)),
                Map.entry(Position.of(6, 4), new Soldier(TeamType.HAN)),
                Map.entry(Position.of(6, 6), new Soldier(TeamType.HAN)),
                Map.entry(Position.of(6, 8), new Soldier(TeamType.HAN)),
                Map.entry(Position.of(7, 1), new Cannon(TeamType.HAN)),
                Map.entry(Position.of(7, 7), new Cannon(TeamType.HAN)),
                Map.entry(Position.of(8, 4), new King(TeamType.HAN)),
                Map.entry(Position.of(9, 3), new Guard(TeamType.HAN)),
                Map.entry(Position.of(9, 5), new Guard(TeamType.HAN)),
                Map.entry(Position.of(9, 0), new Chariot(TeamType.HAN)),
                Map.entry(Position.of(9, 8), new Chariot(TeamType.HAN))
        );
    }

    private Map<Position, Piece> createChoDefaultPieces() {
        return Map.ofEntries(
                Map.entry(Position.of(3, 0), new Soldier(TeamType.CHO)),
                Map.entry(Position.of(3, 2), new Soldier(TeamType.CHO)),
                Map.entry(Position.of(3, 4), new Soldier(TeamType.CHO)),
                Map.entry(Position.of(3, 6), new Soldier(TeamType.CHO)),
                Map.entry(Position.of(3, 8), new Soldier(TeamType.CHO)),
                Map.entry(Position.of(2, 1), new Cannon(TeamType.CHO)),
                Map.entry(Position.of(2, 7), new Cannon(TeamType.CHO)),
                Map.entry(Position.of(1, 4), new King(TeamType.CHO)),
                Map.entry(Position.of(0, 3), new Guard(TeamType.CHO)),
                Map.entry(Position.of(0, 5), new Guard(TeamType.CHO)),
                Map.entry(Position.of(0, 0), new Chariot(TeamType.CHO)),
                Map.entry(Position.of(0, 8), new Chariot(TeamType.CHO))
        );
    }
}
