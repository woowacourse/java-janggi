package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.strategy.DummyStrategy;

import java.util.Map;

public class ElephantHorseElephantHorse implements FormationStrategy{
    @Override
    public Map<Position, Piece> createPieces(Camp camp) {
        return Map.of(
                Position.of(camp.initRowPosition(), 1), new Elephant(camp, new DummyStrategy()),
                Position.of(camp.initRowPosition(), 2), new Horse(camp, new DummyStrategy()),
                Position.of(camp.initRowPosition(), 6), new Elephant(camp, new DummyStrategy()),
                Position.of(camp.initRowPosition(), 7), new Horse(camp, new DummyStrategy())
        );
    }
}

