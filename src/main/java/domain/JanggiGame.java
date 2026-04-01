package domain;

import domain.piece.Blank;
import domain.piece.Piece;
import domain.position.Position;

public class JanggiGame {

    public void play(JanggiBoard janggiBoard, Position from, Position to) {
        Piece piece = janggiBoard.getPiece(from);
        validateBlank(piece);
        piece.canMove(from, to);
    }

    private  void validateBlank(Piece piece) {
        if (piece instanceof Blank) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }
}
