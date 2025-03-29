package janggiGame.piece;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.List;

public class King extends Piece {
    public King(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        if (validatePieceInPalaceDiagonal(origin, destination)) {
            return List.of();
        }
        validateRoute(dx, dy);

        return List.of();
    }

    private boolean validatePieceInPalaceDiagonal(Position origin, Position destination) {
        if (origin.equals(destination)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (!(origin.isInPalace() && destination.isInPalace())) {
            throw new IllegalArgumentException("[ERROR] 장은 궁 안에서만 움직일 수 있습니다.");
        }

        if (origin.isInChoPalaceDiagonal() && destination.isInChoPalaceDiagonal()) {
            if (origin.isCenterOfChoPalace() || destination.isCenterOfChoPalace()) {
                return true;
            }
        }

        if (origin.isInHanPalaceDiagonal() && destination.isInHanPalaceDiagonal()) {
            if (origin.isCenterOfHanPalace() || destination.isCenterOfHanPalace()) {
                return true;
            }
        }

        return false;
    }

    private void validateRoute(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 장이 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
