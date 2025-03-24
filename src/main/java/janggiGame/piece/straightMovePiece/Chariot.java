package janggiGame.piece.straightMovePiece;

import janggiGame.board.Dot;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.Map;
import java.util.Objects;

public class Chariot extends StraightMovePiece {
    private static final String NAME = "차";

    public Chariot(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        boolean isBlocked = routesWithPiece.values().stream().anyMatch(Objects::nonNull);

        if(isBlocked) {
            throw new UnsupportedOperationException("[ERROR] 차는 경로에 말이 존재하면 이동할 수 없습니다.");
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
