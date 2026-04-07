package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.palace.PalaceAdjacentMovement;
import janggi.model.palace.Palaces;
import janggi.model.piece.PieceType;

public class Sa extends PalacePiece {
    private Sa(
            Team team,
            PieceType pieceType,
            Movement defaultMovement
    ) {
        super(team, pieceType, defaultMovement);
    }

    public Sa(Team team, Palaces palaces) {
        this(team, PieceType.SA, new PalaceAdjacentMovement(palaces));
    }
}
