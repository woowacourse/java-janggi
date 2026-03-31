package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public class ElephantHorseHorseElephant implements FormationStrategy {
    @Override
    public List<Piece> createPieces(Camp camp) {
        return List.of(
                new Elephant(camp),
                new Horse(camp),
                new Horse(camp),
                new Elephant(camp)
        );
    }
}
