package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.country.CountryType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {
    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void soldierMoveSameCountryPieceExceptionTest() {
        Piece piece = PieceFactory.SOLDIER.create(CountryType.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(1, 2);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, piece.pieceInfo());
        pathPieceInfos.put(to, PieceFactory.SOLDIER.create(CountryType.CHO).pieceInfo());
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> piece.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
