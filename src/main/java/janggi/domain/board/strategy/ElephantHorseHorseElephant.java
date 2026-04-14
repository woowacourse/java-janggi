package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;

import java.util.Map;

public class ElephantHorseHorseElephant implements FormationStrategy {
    @Override
    public Map<JanggiPosition, Piece> createPieces(Camp camp) {
        return Map.of(
                JanggiPosition.of(camp.baselineRow(), 1), new Elephant(camp, new ElephantStrategy()),
                JanggiPosition.of(camp.baselineRow(), 2), new Horse(camp, new HorseStrategy()),
                JanggiPosition.of(camp.baselineRow(), 7), new Elephant(camp, new ElephantStrategy()),
                JanggiPosition.of(camp.baselineRow(), 6), new Horse(camp, new HorseStrategy())
        );
    }
}
