package domain.piece;

import domain.Direction;
import domain.Offset;

import java.util.List;

public abstract class JumpMovingPiece extends Piece {
    public JumpMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> generatePaths(Offset offset) {
        validateMoveRule(offset);

        Direction mainDirection = offset.getMainDirection();
        Direction subDirection = offset.getSubDirection();

        return generateRoute(mainDirection, subDirection);
    }

    protected abstract List<Offset> generateRoute(Direction main, Direction sub);
}
