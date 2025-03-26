package save;

import java.util.Arrays;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import move.JanggiMoveBehavior;
import move.JolMoveBehavior;
import move.MaMoveBehavior;
import move.SangMoveBehavior;
import piece.PieceType;

public enum JanggiTypeMoveBehaviorMapper {
    CHA(PieceType.CHA.name(), new ChaMoveBehavior()),
    FO(PieceType.FO.name(), new FoMoveBehavior()),
    JOL(PieceType.JOL.name(), new JolMoveBehavior()),
    GUNG(PieceType.GUNG.name(), new GungMoveBehavior()),
    MA(PieceType.MA.name(), new MaMoveBehavior()),
    SANG(PieceType.SANG.name(), new SangMoveBehavior()),
    SA(PieceType.SA.name(), new SangMoveBehavior()),
    ;

    private static final String INVALID_TYPE = "지원하지 않는 타입입니다.";

    private final String pieceType;
    private final JanggiMoveBehavior janggiMoveBehavior;

    JanggiTypeMoveBehaviorMapper(String pieceType, JanggiMoveBehavior janggiMoveBehavior) {
        this.pieceType = pieceType;
        this.janggiMoveBehavior = janggiMoveBehavior;
    }

    public static JanggiMoveBehavior from(String pieceType) {
        JanggiTypeMoveBehaviorMapper findMoveBehaviorMapper = Arrays.stream(JanggiTypeMoveBehaviorMapper.values())
                .filter((janggiTypeMoveBehaviorMapper) -> janggiTypeMoveBehaviorMapper.pieceType.equals(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TYPE));
        return findMoveBehaviorMapper.janggiMoveBehavior;
    }
}
