package janggi.domain.moveRule.moveStrategy;

import janggi.domain.piece.Piece;
import java.util.List;

public class DefaultMoveStrategy implements moveStrategy {
    private static final DefaultMoveStrategy INSTANCE = new DefaultMoveStrategy();

    private DefaultMoveStrategy() {}

    public static DefaultMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        return piece.isOtherTeam(destination) && piece.countPieceInRoute(piecesInRoute) == 0;
    }
}
