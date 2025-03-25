package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BlockOnlyOnceStrategy;
import janggi.domain.rule.move.StraightMoveStrategy;
import java.util.List;

public class Cannon extends Piece {

    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public Cannon(final Team team) {
        super(team, PieceType.CANNON, new MoveRule(new StraightMoveStrategy(), new BlockOnlyOnceStrategy()));
    }

    @Override
    public void checkCanMove(final Board board, final Position departure, final Position destination) {
        validateMove(board, departure, destination, MOVEMENT);
        validateCannonRestrict(board, departure, destination);
    }

    private void validateCannonRestrict(final Board board, final Position departure, final Position destination) {
        checkExistCannonInDestination(board, destination);
        checkIsOverCannon(board, Route.of(departure, destination));
    }

    private void checkExistCannonInDestination(final Board board, final Position destination) {
        if (board.exists(destination) && board.isSameType(destination, this.pieceType)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void checkIsOverCannon(final Board board, final Route route) {
        if (route.isExistSameTypePiece(board, this.pieceType)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }
}
