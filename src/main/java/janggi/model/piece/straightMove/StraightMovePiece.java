package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.StraightMovement;
import janggi.model.movement.palace.PalaceMultipleMovement;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class StraightMovePiece extends Piece {

    private final Movement palaceMovement;

    private StraightMovePiece(
            Team team,
            PieceType pieceType,
            Movement defaultMovement,
            Movement palaceMovement
    ) {
        super(team, pieceType, defaultMovement);
        this.palaceMovement = palaceMovement;
    }

    public StraightMovePiece(
            Team team,
            PieceType pieceType
    ) {
        this(team, pieceType, new StraightMovement(), new PalaceMultipleMovement());
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if (from.isInSamePalaceWith(to)) {
            palaceMovement.move(from, to);
        }

        return defaultMovement
                .move(from, to);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
