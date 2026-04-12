package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.patternBasedMovement.SangMovement;
import janggi.model.piece.PieceType;

public class Sang extends DiagonalMovePiece {

    private Sang(
            Team team,
            PieceType pieceType,
            Movement defaultMovement
    ) {
        super(team, pieceType, defaultMovement);
    }

    public Sang(Team team) {
        this(team, PieceType.SANG, new SangMovement());
    }
}
