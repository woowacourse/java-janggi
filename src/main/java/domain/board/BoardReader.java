package domain.board;

import domain.piece.Piece;
import domain.position.Position;

public interface BoardReader {

    boolean isExist(Position position);

    boolean isDifferentPieceType(Position position, Piece piece);
}
