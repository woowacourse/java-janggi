package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.palace.PalaceAdjacentMovement;
import janggi.model.palace.Palaces;
import janggi.model.piece.PieceType;

public class Jang extends PalacePiece {

    private Jang(
            Team team,
            PieceType pieceType,
            Movement defaultMovement
    ) {
        super(team, pieceType, defaultMovement);
    }

    public Jang(
            Team team,
            Palaces palaces
    ) {
        super(team, PieceType.JANG, new PalaceAdjacentMovement(palaces));
    }
}
