package domain.movement;

import domain.game.Piece;
import domain.vo.PieceType;
import domain.vo.Team;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public final class MovementFactory {
    private static final Map<PieceType, Supplier<Movement>> MOVEMENT_SUPPLIERS = new EnumMap<>(PieceType.class);

    static {
        MOVEMENT_SUPPLIERS.put(PieceType.GENERAL, GeneralMovement::new);
        MOVEMENT_SUPPLIERS.put(PieceType.GUARD, GuardMovement::new);
        MOVEMENT_SUPPLIERS.put(PieceType.CHARIOT, StepPieceMovement::new);
        MOVEMENT_SUPPLIERS.put(PieceType.CANNON, StepPieceMovement::new);
        MOVEMENT_SUPPLIERS.put(PieceType.ELEPHANT, ElephantMovement::new);
        MOVEMENT_SUPPLIERS.put(PieceType.HORSE, HorseMovement::new);
    }

    private MovementFactory() {
    }

    public static Movement create(Piece piece) {
        if (piece.isSoldier()) {
            return soldierMovementFor(piece);
        }
        return MOVEMENT_SUPPLIERS.get(piece.getPieceType()).get();
    }

    private static Movement soldierMovementFor(Piece piece) {
        if (piece.isOwnedBy(Team.HAN)) {
            return new SoldierMovement(Team.HAN);
        }
        return new SoldierMovement(Team.CHO);
    }
}