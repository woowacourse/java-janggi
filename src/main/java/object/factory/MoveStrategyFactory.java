package object.factory;

import java.util.HashMap;
import java.util.Map;
import object.piece.PieceType;
import object.strategy.ChaMoveStrategy;
import object.strategy.FoMoveStrategy;
import object.strategy.GungMoveStrategy;
import object.strategy.JolMoveStrategy;
import object.strategy.MaMoveStrategy;
import object.strategy.MoveStrategy;
import object.strategy.SaMoveStrategy;
import object.strategy.SangMoveStrategy;

public class MoveStrategyFactory {

    private static final Map<PieceType, MoveStrategy> moveStrategyCache = new HashMap<>();

    static {
        moveStrategyCache.put(PieceType.CHARIOT, new ChaMoveStrategy());
        moveStrategyCache.put(PieceType.ELEPHANT, new SangMoveStrategy());
        moveStrategyCache.put(PieceType.HORSE, new MaMoveStrategy());
        moveStrategyCache.put(PieceType.GUARD, new SaMoveStrategy());
        moveStrategyCache.put(PieceType.GENERAL, new GungMoveStrategy());
        moveStrategyCache.put(PieceType.SOLIDER, new JolMoveStrategy());
        moveStrategyCache.put(PieceType.CANNON, new FoMoveStrategy());
    }

    public static MoveStrategy create(PieceType pieceType) {
        MoveStrategy strategy = moveStrategyCache.get(pieceType);
        if (strategy == null) {
            throw new IllegalArgumentException(PieceType.INVALID_TYPE);
        }
        return strategy;
    }
}
