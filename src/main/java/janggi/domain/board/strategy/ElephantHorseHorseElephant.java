package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;

import java.util.Map;

public class ElephantHorseHorseElephant implements FormationStrategy {
    @Override
    public Map<Position, Piece> createPieces(Camp camp) {
        return Map.of(
                Position.of(camp.initRowPosition(), 1), new Elephant(camp, new ElephantStrategy()),
                Position.of(camp.initRowPosition(), 2), new Horse(camp, new HorseStrategy()),
                Position.of(camp.initRowPosition(), 7), new Elephant(camp, new ElephantStrategy()),
                Position.of(camp.initRowPosition(), 6), new Horse(camp, new HorseStrategy())
        );
    }
}
