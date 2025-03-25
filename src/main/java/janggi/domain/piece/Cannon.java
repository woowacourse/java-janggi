package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.CannonMoveRule;
import java.util.List;

public class Cannon extends Piece {
    public Cannon(TeamColor color) {
        super(color, PieceType.CANNON, CannonMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        if(path.isInPalacePath() && path.isDiagonal()) {
            return true;
        }
        return path.isStraight();
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return path.getBetweenPositions();
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
