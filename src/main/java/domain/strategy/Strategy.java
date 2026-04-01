package domain.strategy;

import domain.piece.Blank;
import domain.piece.Piece;
import domain.position.Position;
import domain.PieceProvider;

import java.util.List;

public interface Strategy {
    List<Position> getMoveCandidates(Position from, PieceProvider board);

    private void validateBlank(Piece piece) {
        if (piece instanceof Blank) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }
}
