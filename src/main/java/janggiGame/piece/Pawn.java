package janggiGame.piece;

import janggiGame.board.Dot;

import java.util.List;
import java.util.Map;

public class Pawn extends Piece {

    public static final String NAME = "병";

    public Pawn(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        validateRoute(dx, dy);

        return List.of();
    }

    @Override
    public void validateRoute(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 병이 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dynasty == Dynasty.HAN && dy > 0) {
            throw new UnsupportedOperationException("[ERROR] 병은 뒤로 이동할 수 없습니다.");
        }

        if (dynasty == Dynasty.CHO && dy < 0) {
            throw new UnsupportedOperationException("[ERROR] 병은 뒤로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
