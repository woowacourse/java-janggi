package domain.piece;

import domain.position.Position;
import java.util.List;

public class EmptyPiece implements Piece {

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public boolean isAlly(Piece other) {
        return false;
    }

    @Override
    public String display(PieceAppearance colorizer) {
        return colorizer.colorizeEmpty();
    }

    @Override
    public List<Position> searchRoute(Position src, Position dest) {
        throw new IllegalArgumentException("이동할 기물이 없는 위치입니다.");
    }

    @Override
    public String toString() {
        return PieceDefinition.EMPTY.name();
    }
}
