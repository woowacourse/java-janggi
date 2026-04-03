package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.Position;
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
                Position.of(camp.baselineRow(), 2), new Elephant(camp, new ElephantStrategy()),
                Position.of(camp.baselineRow(), 1), new Horse(camp, new HorseStrategy()),
                Position.of(camp.baselineRow(), 6), new Elephant(camp, new ElephantStrategy()),
                Position.of(camp.baselineRow(), 7), new Horse(camp, new HorseStrategy())
        );
    }
}
