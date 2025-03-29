package janggiGame.piece.oneMovePiece;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import janggiGame.position.Palace;
import janggiGame.position.Position;
import java.util.List;
import java.util.Map;

public abstract class OneMovePiece extends Piece {
    public OneMovePiece(Dynasty dynasty, Type type) {
        super(dynasty, type);
    }

    @Override
    public List<Position> getRoute(Position origin, Position destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (Palace.isPalaceDiagonalMove(origin, destination)) {
            return List.of();
        }

        validateRoute(dx, dy);
        return List.of();
    }

    @Override
    protected void validateRoute(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    @Override
    public void validateMove(Map<Position, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);
    }

    protected void validateDestinationInPalace(Position destination) {
        if (!Palace.isInPalace(destination)) {
            throw new UnsupportedOperationException("[ERROR] 궁성 밖으로 나갈 수 없는 기물입니다.");
        }
    }
}
