package domain.movement;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public final class MovementFactory {
    private static final Map<PieceType, Function<Piece, Movement>> MOVEMENT_SUPPLIERS = movementSuppliers();

    private MovementFactory() {
    }

    public static Movement create(Piece piece) {
        return MOVEMENT_SUPPLIERS.get(piece.getPieceType())
                .apply(piece);
    }

    private static Map<PieceType, Function<Piece, Movement>> movementSuppliers() {
        EnumMap<PieceType, Function<Piece, Movement>> suppliers = new EnumMap<>(PieceType.class);
        suppliers.put(PieceType.GENERAL, piece -> new GeneralMovement());
        suppliers.put(PieceType.GUARD, piece -> new GuardMovement());
        suppliers.put(PieceType.CHARIOT, piece -> new ChariotMovement());
        suppliers.put(PieceType.CANNON, piece -> new CannonMovement());
        suppliers.put(PieceType.ELEPHANT, piece -> new ElephantMovement());
        suppliers.put(PieceType.HORSE, piece -> new HorseMovement());
        suppliers.put(PieceType.SOLDIER, piece -> new SoldierMovement(piece.getTeam()));
        return Map.copyOf(suppliers);
    }
}
