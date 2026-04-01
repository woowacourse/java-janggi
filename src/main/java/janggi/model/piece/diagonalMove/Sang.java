package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.patternBasedMovement.SangMovement;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;

public class Sang extends DiagonalMovePiece {

    private final Movement movement;

    private Sang(Team team, PieceType pieceType, Movement movement) {
        super(team, pieceType);
        this.movement = movement;
    }

    public Sang(Team team) {
        this(
                team,
                PieceType.SANG,
                new SangMovement()
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}
