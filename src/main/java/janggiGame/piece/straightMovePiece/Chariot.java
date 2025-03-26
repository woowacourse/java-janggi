package janggiGame.piece.straightMovePiece;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import java.util.Map;
import java.util.Objects;

public class Chariot extends StraightMovePiece {

    public Chariot(Dynasty dynasty) {
        super(dynasty, Type.CHARIOT);
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        boolean isBlocked = routesWithPiece.values().stream().anyMatch(Objects::nonNull);

        if(isBlocked) {
            throw new UnsupportedOperationException("[ERROR] 차는 경로에 말이 존재하면 이동할 수 없습니다.");
        }
    }
}
