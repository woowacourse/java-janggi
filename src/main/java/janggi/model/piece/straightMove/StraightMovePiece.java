package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.movement.MovementSelector;
import janggi.model.movement.StraightMovement;
import janggi.model.movement.palace.PalaceMultipleMovement;
import janggi.model.palace.PalaceFactory;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class StraightMovePiece extends Piece {

    private final MovementSelector movementSelector;

    protected StraightMovePiece(
            Team team,
            PieceType pieceType
    ) {
        super(team, pieceType);
        this.movementSelector = new MovementSelector(
                new StraightMovement(),
                new PalaceMultipleMovement(
                        new PalaceFactory().create()
                )
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movementSelector.select(from, to)
                .move(from, to);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
