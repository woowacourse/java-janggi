package object.factory;

import java.util.HashMap;
import java.util.Map;
import object.piece.PieceType;
import object.strategy.ChariotStrategy;
import object.strategy.CannonStrategy;
import object.strategy.GeneralStrategy;
import object.strategy.SoldierStrategy;
import object.strategy.HorseStrategy;
import object.strategy.MoveStrategy;
import object.strategy.GuardStrategy;
import object.strategy.ElephantStrategy;

public class MoveStrategyFactory {

    private static final Map<PieceType, MoveStrategy> moveStrategyCache = new HashMap<>();

    static {
        moveStrategyCache.put(PieceType.CHARIOT, new ChariotStrategy());
        moveStrategyCache.put(PieceType.ELEPHANT, new ElephantStrategy());
        moveStrategyCache.put(PieceType.HORSE, new HorseStrategy());
        moveStrategyCache.put(PieceType.GUARD, new GuardStrategy());
        moveStrategyCache.put(PieceType.GENERAL, new GeneralStrategy());
        moveStrategyCache.put(PieceType.SOLIDER, new SoldierStrategy());
        moveStrategyCache.put(PieceType.CANNON, new CannonStrategy());
    }

    public static MoveStrategy create(PieceType pieceType) {
        MoveStrategy strategy = moveStrategyCache.get(pieceType);
        if (strategy == null) {
            throw new IllegalArgumentException(PieceType.INVALID_TYPE);
        }
        return strategy;
    }
}
