package domain;

import domain.piece.Blank;
import domain.piece.Piece;
import domain.position.Position;

public class JanggiGame {

    public void play(Position from, Position to, PieceProvider janggiBoard) {
        Piece piece = janggiBoard.getPiece(from);
        validateBlank(piece);
        piece.canMove(from, to, janggiBoard);
    }

    private void validateBlank(Piece piece) {
        if (piece instanceof Blank) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }
}
