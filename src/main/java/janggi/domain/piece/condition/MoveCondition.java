package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceType;
import java.util.List;

public interface MoveCondition {

    void checkPath(List<Position> path, Camp camp, BoardChecker board, PieceType pieceType);
}
