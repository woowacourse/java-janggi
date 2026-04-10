package domain.piece;

import domain.Direction;
import domain.Offset;
import domain.board.Palace;
import domain.board.Position;

import java.util.List;
import java.util.Optional;

public abstract class JumpMovingPiece extends Piece {
    public JumpMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> generatePaths(Position from, Position to, Optional<Palace> palace) {
        Offset offset = Offset.of(from, to);
        Direction mainDirection = mainAxisDirection(offset);
        Direction subDirection = subAxisDirection(offset);
        return generateRoute(mainDirection, subDirection);
    }

    private Direction mainAxisDirection(Offset offset) {
        if (offset.absX() > offset.absY()) {
            return Direction.of(new Offset(Integer.signum(offset.dx()), 0));
        }
        return Direction.of(new Offset(0, Integer.signum(offset.dy())));
    }

    private Direction subAxisDirection(Offset offset) {
        if (offset.absX() > offset.absY()) {
            return Direction.of(new Offset(0, Integer.signum(offset.dy())));
        }
        return Direction.of(new Offset(Integer.signum(offset.dx()), 0));
    }

    protected abstract List<Offset> generateRoute(Direction main, Direction sub);
}
