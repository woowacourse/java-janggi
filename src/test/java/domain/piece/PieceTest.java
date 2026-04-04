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
        Piece piece = new Piece(new PieceInfo(PieceType.SOLDIER, CountryType.CHO));

        Position from = new Position(1, 1);
        Position to = new Position(1, 2);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, piece.pieceInfo());
        pathPieceInfos.put(to, new PieceInfo(PieceType.SOLDIER, CountryType.CHO));
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> piece.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("초나라 졸병이 후진할 경우 예외가 발생한다.")
    void choSoldierDownExceptionTest() {
        Piece piece = new Piece(new PieceInfo(PieceType.SOLDIER, CountryType.CHO));

        Position from = new Position(4, 1);
        Position straightTo = new Position(4, 0);
        Position diagonalTo = new Position(3, 0);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, piece.pieceInfo());
        // to((4, 0), (3, 0))는 비어있음
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> piece.validateMove(pieceInfos, from, straightTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> piece.validateMove(pieceInfos, from, diagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 졸병이 후진할 경우 예외가 발생한다.")
    void hanSoldierDownExceptionTest() {
        Piece piece = new Piece(new PieceInfo(PieceType.SOLDIER, CountryType.HAN));

        Position from = new Position(4, 8);
        Position straightTo = new Position(4, 9);
        Position diagonalTo = new Position(3, 9);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, piece.pieceInfo());
        // to((4, 9), (3, 9))는 비어있음
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> piece.validateMove(pieceInfos, from, straightTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> piece.validateMove(pieceInfos, from, diagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }
}
