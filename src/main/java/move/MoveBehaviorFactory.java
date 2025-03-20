package move;

import java.util.HashMap;
import java.util.Map;
import piece.PieceType;

public class MoveBehaviorFactory {

    private static final String INVALID_TYPE = "존재하지 않는 타입입니다.";

    private static final Map<PieceType, MoveBehavior> moveBehaviorCache = new HashMap<>();

    static {
        moveBehaviorCache.put(PieceType.CHA, new ChaMoveBehavior());
        moveBehaviorCache.put(PieceType.SANG, new SangMoveBehavior());
        moveBehaviorCache.put(PieceType.MA, new MaMoveBehavior());
        moveBehaviorCache.put(PieceType.SA, new SaMoveBehavior());
        moveBehaviorCache.put(PieceType.GUNG, new GungMoveBehavior());
        moveBehaviorCache.put(PieceType.JOL, new JolMoveBehavior());
        moveBehaviorCache.put(PieceType.FO, new FoMoveBehavior());
    }

    public static MoveBehavior create(PieceType pieceType) {
        MoveBehavior Behavior = moveBehaviorCache.get(pieceType);
        if (Behavior == null) {
            throw new IllegalArgumentException(INVALID_TYPE);
        }
        return Behavior;
    }
}
