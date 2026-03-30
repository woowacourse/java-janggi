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

        Direction mainDirection = offset.getMainDirection();
        Direction subDirection = offset.getSubDirection();

        return generatePaths(mainDirection, subDirection);
    }

    protected abstract void validateMoveRule(Offset offset);

    protected abstract List<Offset> generatePaths(Direction main, Direction sub);
}
