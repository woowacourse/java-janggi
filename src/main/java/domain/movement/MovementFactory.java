package domain.movement;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public final class MovementFactory {
    private static final Map<PieceType, Function<Piece, Movement>> MOVEMENT_SUPPLIERS = new EnumMap<>(PieceType.class);

    static {
        MOVEMENT_SUPPLIERS.put(PieceType.GENERAL, piece -> new GeneralMovement());
        MOVEMENT_SUPPLIERS.put(PieceType.GUARD, piece -> new GuardMovement());
        MOVEMENT_SUPPLIERS.put(PieceType.CHARIOT, piece -> new StepPieceMovement());
        MOVEMENT_SUPPLIERS.put(PieceType.CANNON, piece -> new StepPieceMovement());
        MOVEMENT_SUPPLIERS.put(PieceType.ELEPHANT, piece -> new ElephantMovement());
        MOVEMENT_SUPPLIERS.put(PieceType.HORSE, piece -> new HorseMovement());
        MOVEMENT_SUPPLIERS.put(PieceType.SOLDIER, MovementFactory::soldierMovementFor);
    }

    private MovementFactory() {
    }

    public static Movement create(Piece piece) {
        return MOVEMENT_SUPPLIERS.get(piece.getPieceType()).apply(piece);
    }

    private static Movement soldierMovementFor(Piece piece) {
        if (piece.isOwnedBy(Team.HAN)) {
            return new SoldierMovement(Team.HAN);
        }
        return new SoldierMovement(Team.CHO);
    }
}
