package persistence;

import java.util.Arrays;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import move.JanggiMoveBehavior;
import move.JolMoveBehavior;
import move.MaMoveBehavior;
import move.SaMoveBehavior;
import move.SangMoveBehavior;
import piece.PieceType;

public enum JanggiTypeMoveBehaviorMapper {
    CHA(PieceType.CHA, new ChaMoveBehavior()),
    FO(PieceType.FO, new FoMoveBehavior()),
    JOL(PieceType.JOL, new JolMoveBehavior()),
    GUNG(PieceType.GUNG, new GungMoveBehavior()),
    MA(PieceType.MA, new MaMoveBehavior()),
    SANG(PieceType.SANG, new SangMoveBehavior()),
    SA(PieceType.SA, new SaMoveBehavior()),
    ;

    private static final String INVALID_TYPE = "지원하지 않는 타입입니다.";

    private final PieceType pieceType;
    private final JanggiMoveBehavior janggiMoveBehavior;

    JanggiTypeMoveBehaviorMapper(PieceType pieceType, JanggiMoveBehavior janggiMoveBehavior) {
        this.pieceType = pieceType;
        this.janggiMoveBehavior = janggiMoveBehavior;
    }

    public static JanggiMoveBehavior from(String pieceType) {
        JanggiTypeMoveBehaviorMapper findMoveBehaviorMapper = Arrays.stream(JanggiTypeMoveBehaviorMapper.values())
                .filter((janggiTypeMoveBehaviorMapper) -> janggiTypeMoveBehaviorMapper.pieceType.name()
                        .equals(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TYPE));
        return findMoveBehaviorMapper.janggiMoveBehavior;
    }
}
