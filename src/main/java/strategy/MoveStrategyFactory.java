package strategy;

import java.util.HashMap;
import java.util.Map;
import piece.PieceType;

public class MoveStrategyFactory {

    private static final String INVALID_TYPE = "존재하지 않는 타입입니다.";

    private static final Map<PieceType, MoveStrategy> moveStrategyCache = new HashMap<>();

    static {
        moveStrategyCache.put(PieceType.CHA, new ChaMoveStrategy());
        moveStrategyCache.put(PieceType.SANG, new SangMoveStrategy());
        moveStrategyCache.put(PieceType.MA, new MaMoveStrategy());
        moveStrategyCache.put(PieceType.SA, new SaMoveStrategy());
        moveStrategyCache.put(PieceType.GUNG, new GungMoveStrategy());
        moveStrategyCache.put(PieceType.JOL, new JolMoveStrategy());
    }

    public static MoveStrategy create(PieceType pieceType) {
        MoveStrategy strategy = moveStrategyCache.get(pieceType);
        if (strategy == null) {
            throw new IllegalArgumentException(INVALID_TYPE);
        }
        return strategy;
    }
}
