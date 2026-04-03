package domain.strategy;

import domain.Piece;
import domain.vo.Position;

import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    List<Position> getPath(final Position from, final Position to);

    boolean canMove(final Piece mover, final Position from, final Position to, final Map<Position, Piece> piecesOnPath);
}