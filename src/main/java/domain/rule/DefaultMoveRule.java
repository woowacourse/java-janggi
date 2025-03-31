package domain.rule;

import domain.piece.Piece;
import java.util.List;

public class DefaultMoveRule implements MoveRule {

    private static final int DEFAULT_OBSTACLE_COUNT = 0;
    private static final DefaultMoveRule INSTANCE = new DefaultMoveRule();

    public static DefaultMoveRule getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidateMoveRule(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        int pieceCountInRoute = piece.countObstacles(piecesInRoute);
        if (pieceCountInRoute != DEFAULT_OBSTACLE_COUNT) {
            return false;
        }

        return piece.isOtherTeam(destination);
    }
}
