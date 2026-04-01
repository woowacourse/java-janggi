package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.StraightMovement;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;

public abstract class StraightMovePiece extends Piece {

    protected final Movement movement;

    private StraightMovePiece(
            Team team,
            PieceType pieceType,
            Movement movement
    ) {
        super(team, pieceType);
        this.movement = movement;
    }

    protected StraightMovePiece(
            Team team,
            PieceType pieceType
    ) {
        this(
                team,
                pieceType,
                new StraightMovement()
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }


    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
