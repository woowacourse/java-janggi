package domain.piece.strategy;

import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class StrategyProvider {
    private static final Map<PieceType, MoveStrategy> provideMap;

    static {
        provideMap = new HashMap<>();
        provideMap.put(PieceType.CHA, new SlidingMoveStrategy());
        provideMap.put(PieceType.MA, new MaMoveStrategy());
        provideMap.put(PieceType.SANG, new SangMoveStrategy());
        provideMap.put(PieceType.SA, new AllDirectionsSingleStepMoveStrategy());
        provideMap.put(PieceType.JANG, new AllDirectionsSingleStepMoveStrategy());
        provideMap.put(PieceType.PO, new SlidingMoveStrategy());
        provideMap.put(PieceType.JOL, new JolMoveStrategy());
        provideMap.put(PieceType.BYEONG, new ByeongMoveStrategy());
    }

    private StrategyProvider() {
    }

    public static MoveStrategy getStrategy(PieceType pieceType) {
        return provideMap.get(pieceType);
    }
}
