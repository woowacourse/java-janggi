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

public class InsidePalaceMoveStrategyTest {
    @Test
    @DisplayName("초나라 궁성 내부 기물이 궁성 밖으로 나갈 경우 예외가 발생한다.")
    void choMoveOutsidePalaceExceptionTest() {
        MoveStrategy moveStrategy = new InsidePalaceMoveStrategy();

        Position from = new Position(3, 0);
        Position to = new Position(2, 0);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, new PieceInfo(PieceType.GENERAL, CountryType.HAN));
        // to(2, 0)는 비어있음
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 궁성 내부 기물이 궁성 밖으로 나갈 경우 예외가 발생한다.")
    void hanMoveOutsidePalaceExceptionTest() {
        MoveStrategy moveStrategy = new InsidePalaceMoveStrategy();

        Position from = new Position(3, 7);
        Position to = new Position(2, 7);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, new PieceInfo(PieceType.GENERAL, CountryType.HAN));
        // to(2, 7)는 비어있음
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.");
    }
}
