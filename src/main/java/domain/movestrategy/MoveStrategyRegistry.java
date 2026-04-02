package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.Map;

public class MoveStrategyRegistry {

    private final Map<PieceType, MoveStrategy> moveStrategies;

    public MoveStrategyRegistry(Map<PieceType, MoveStrategy> moveStrategies) {
        this.moveStrategies = moveStrategies;
    }

    public static MoveStrategyRegistry init() {
        Map<PieceType, MoveStrategy> moveStrategies = new EnumMap<>(PieceType.class);

        moveStrategies.put(PieceType.GENERAL, new GeneralMoveStrategy());
        moveStrategies.put(PieceType.GUARD, new GuardMoveStrategy());
        moveStrategies.put(PieceType.HORSE, new HorseMoveStrategy());
        moveStrategies.put(PieceType.ELEPHANT, new ElephantMoveStrategy());
        moveStrategies.put(PieceType.SOLDIER, new SoldierMoveStrategy());
        moveStrategies.put(PieceType.CANNON, new CannonMoveStrategy());
        moveStrategies.put(PieceType.CHARIOT, new ChariotMoveStrategy());

        return new MoveStrategyRegistry(moveStrategies);
    }

    public MoveStrategy getMoveStrategyBy(Piece piece) {
        if(!moveStrategies.containsKey(piece.getPieceType())) {
            throw new IllegalArgumentException("행마법이 존재하지 않습니다.");
        }
        return moveStrategies.get(piece.getPieceType());
    }
}
