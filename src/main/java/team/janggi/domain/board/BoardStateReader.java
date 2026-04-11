package team.janggi.domain.board;

import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;

public interface BoardStateReader {

    Piece getPiece(Position position);

    int size();
}
