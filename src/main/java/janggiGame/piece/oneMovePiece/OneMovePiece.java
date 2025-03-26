package janggiGame.piece.oneMovePiece;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import java.util.List;
import java.util.Map;

public abstract class OneMovePiece extends Piece {
    public OneMovePiece(Dynasty dynasty, Type type) {
        super(dynasty, type);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

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
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);
    }
}
