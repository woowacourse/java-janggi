package domain.strategy;

import domain.position.Position;
import domain.PieceProvider;

import java.util.List;

public interface Strategy {
    List<Position> getMoveCandidates(Position from, PieceProvider board);
}
