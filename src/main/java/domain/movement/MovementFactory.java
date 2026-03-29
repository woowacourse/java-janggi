package domain.movement;

import domain.game.Piece;
import domain.vo.PieceType;
import domain.vo.Team;

public final class MovementFactory {
    private MovementFactory() {
    }

    public static Movement create(Piece piece) {
        return switch (piece.getPieceType()) {
            case GENERAL -> new GeneralMovement();
            case GUARD -> new GuardMovement();
            case CHARIOT, CANNON -> new StepPieceMovement();
            case ELEPHANT -> new ElephantMovement();
            case HORSE -> new HorseMovement();
            case SOLDIER -> soldierMovementFor(piece);
        };
    }

    private static Movement soldierMovementFor(Piece piece) {
        if (piece.isOwnedBy(Team.HAN)) {
            return new SoldierMovement(Team.HAN);
        }
        return new SoldierMovement(Team.CHO);
    }
}
