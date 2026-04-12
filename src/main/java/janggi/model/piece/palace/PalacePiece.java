package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.palace.PalaceAdjacentMovement;
import janggi.model.palace.PalaceFactory;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class PalacePiece extends Piece {

    private final Movement defaultMovement;

    private PalacePiece(
            Team team,
            PieceType pieceType,
            Movement defaultMovement
    ) {
        super(team, pieceType);
        this.defaultMovement = defaultMovement;
    }

    protected PalacePiece(Team team, PieceType pieceType) {
        this(team, pieceType,
                new PalaceAdjacentMovement(
                        new PalaceFactory().create()
                )
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return defaultMovement.move(from, to);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }

}
