package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.StraightMovement;
import janggi.model.movement.palace.PalaceMovement;
import janggi.model.movement.palace.PalaceMultipleMovement;
import janggi.model.palace.Palaces;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class StraightMovePiece extends Piece {

    private final PalaceMovement palaceMovement;

    private StraightMovePiece(
            Team team,
            PieceType pieceType,
            Movement defaultMovement,
            PalaceMovement palaceMovement
    ) {
        super(team, pieceType, defaultMovement);
        this.palaceMovement = palaceMovement;
    }

    public StraightMovePiece(
            Team team,
            PieceType pieceType,
            Palaces palaces
    ) {
        this(
                team,
                pieceType,
                new StraightMovement(),
                new PalaceMultipleMovement(palaces)
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if (palaceMovement.supports(from, to)) {
            return palaceMovement.move(from, to);
        }

        return defaultMovement
                .move(from, to);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
