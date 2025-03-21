package janggi.domain.moveRule;

import janggi.domain.piece.Piece;
import java.util.List;

public class DefaultMoveRule implements MoveRule{
    private static final DefaultMoveRule DEFAULT_MOVE_RULE = new DefaultMoveRule();

    private DefaultMoveRule() {}

    public static DefaultMoveRule getRule() {
        return DEFAULT_MOVE_RULE;
    }

    @Override
    public boolean canMove(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        return piece.isOtherTeam(destination) && piece.countPieceInRoute(piecesInRoute) == 0;
    }
}
