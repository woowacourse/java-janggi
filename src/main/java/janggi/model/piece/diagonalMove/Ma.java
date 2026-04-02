package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.patternBasedMovement.MaMovement;
import janggi.model.piece.PieceType;

public class Ma extends DiagonalMovePiece {

    private Ma(
            Team team,
            PieceType pieceType,
            Movement defaultMovement
    ) {
        super(team, pieceType, defaultMovement);
    }

    public Ma(Team team) {
        this(team, PieceType.MA, new MaMovement());
    }
}
