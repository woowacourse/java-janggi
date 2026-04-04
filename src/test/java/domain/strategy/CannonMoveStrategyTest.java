package domain.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CannonMoveStrategyTest {
    private final PieceInfo cannonPieceInfo = new PieceInfo(PieceType.CANNON, CountryType.CHO);

    @Test
    @DisplayName("포의 from, to 사이 경로에 기물이 1개가 아니면 예외가 발생한다.")
    void cannonJumpPieceCountExceptionTest() {
        MoveStrategy moveStrategy = new CannonMoveStrategy();

        Position from = new Position(1, 1);
        Position to = new Position(1, 4);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, cannonPieceInfo);
        pathPieceInfos.put(new Position(1, 2), new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        pathPieceInfos.put(new Position(1, 3), new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        // to(1, 4)는 비어있음
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.");
    }

    @Test
    @DisplayName("포가 포를 뛰어 넘으려 할 경우 예외가 발생한다.")
    void cannonJumpCannonExceptionTest() {
        MoveStrategy moveStrategy = new CannonMoveStrategy();

        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, cannonPieceInfo);
        pathPieceInfos.put(new Position(1, 2), new PieceInfo(PieceType.CANNON, CountryType.HAN));
        // to(1, 4)는 비어있음
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어 넘을 수 없습니다.");
    }

    @Test
    @DisplayName("포가 포를 잡으려 할 경우 예외가 발생한다.")
    void cannonKillCannonExceptionTest() {
        MoveStrategy moveStrategy = new CannonMoveStrategy();

        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, cannonPieceInfo);
        pathPieceInfos.put(new Position(1, 2), new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        pathPieceInfos.put(to, new PieceInfo(PieceType.CANNON, CountryType.HAN));
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }
}
