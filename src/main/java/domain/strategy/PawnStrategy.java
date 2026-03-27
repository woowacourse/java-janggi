package domain.strategy;

import domain.Position;
import domain.piece.PieceProvider;

import java.util.List;

public class PawnStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position source, PieceProvider board) {
        return List.of();
    }
}
