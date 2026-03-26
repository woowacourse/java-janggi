package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import java.util.List;

public interface MoveCondition {

    void checkPath(List<Position> path, Camp camp, Board board, PieceRule pieceRule);
}
