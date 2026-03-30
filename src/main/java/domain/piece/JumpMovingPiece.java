package domain.piece;

import domain.Direction;
import domain.Offset;

import java.util.List;

public abstract class JumpMovingPiece extends Piece {
    public JumpMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> getPathPositions(Offset offset) {
        validateMoveRule(offset);

        Direction mainDirection = decideMainDirection(offset);
        Direction subDirection = decideSubDirection(offset);

        return generatePaths(mainDirection, subDirection);
    }

    protected abstract void validateMoveRule(Offset offset);

    protected abstract List<Offset> generatePaths(Direction main, Direction sub);


    private Direction decideMainDirection(Offset offset) {
        if (Math.abs(offset.dx()) > Math.abs(offset.dy())) {
            return decideXDirection(offset.dx());
        }
        return decideYDirection(offset.dy());
    }

    private Direction decideSubDirection(Offset offset) {
        if (Math.abs(offset.dx()) > Math.abs(offset.dy())) {
            return decideYDirection(offset.dy());
        }
        return decideXDirection(offset.dx());
    }

    private Direction decideXDirection(int dx) {
        if (dx > 0) {
            return Direction.RIGHT;
        }
        return Direction.LEFT;
    }

    private Direction decideYDirection(int dy) {
        if (dy > 0) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }
}
