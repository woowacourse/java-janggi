package domain.movement;

import domain.game.Piece;
import domain.vo.PieceType;
import domain.vo.Team;

public final class MovementFactory {
    private MovementFactory() {
    }

    public static Movement create(Piece piece) {
        return createForType(piece.getPieceType(), piece.getTeam());
    }

    public static Movement createForType(PieceType type, Team team) {
        return switch (type) {
            case GENERAL -> new GeneralMovement();
            case GUARD -> new GuardMovement();
            case CHARIOT, CANNON -> new StepPieceMovement();
            case ELEPHANT -> new ElephantMovement();
            case HORSE -> new HorseMovement();
            case SOLDIER -> new SoldierMovement(team);
        };
    }
}
