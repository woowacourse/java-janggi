package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;

import java.util.Map;

public class HorseElephantElephantHorse implements FormationStrategy {
    @Override
    public Map<Position, Piece> createPieces(Camp camp) {
        return Map.of(
                Position.of(camp.initRowPosition(), 2), new Elephant(camp),
                Position.of(camp.initRowPosition(), 1), new Horse(camp),
                Position.of(camp.initRowPosition(), 6), new Elephant(camp),
                Position.of(camp.initRowPosition(), 7), new Horse(camp)
        );
    }
}
