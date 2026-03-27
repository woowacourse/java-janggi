package domain.strategy;

import domain.Position;
import domain.piece.PieceProvider;

import java.util.List;

public interface MoveStrategy {
    List<Position> getMoveCandidates(Position currentPosition, PieceProvider board);
}
