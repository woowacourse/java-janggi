package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {
    private final Path path = new Path();

    @Test
    @DisplayName("마의 위쪽-왼쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseUpAndLeftUpPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(3, 6);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(3, 6));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 위쪽-오른쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseUpAndRightUpPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(5, 6);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(5, 6));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 오른쪽-오른쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseRightAndRightUpPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(6, 5);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 5));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 오른쪽-오른쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseRightAndRightDownPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(6, 3);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 3));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 아래쪽-오른쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseDownAndRightDownPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(5, 2);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(5, 2));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 아래쪽-왼쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseDownAndLeftDownPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(3, 2);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(3, 2));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 왼쪽-왼쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseLeftAndLeftDownPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(2, 3);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 3));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 왼쪽-왼쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void horseLeftAndLeftUpPathTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(2, 5);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 5));

        assertThat(horse.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("마의 방향의 크기가 2가 아닌 경우 예외가 발생한다.")
    void horseDirectionSizeExceptionTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(1, 0);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> horse.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마가 이동할 수 있는 방향은 2개이어야 합니다.");
    }

    @Test
    @DisplayName("마의 1번째 방향이 대각선이거나, 2번째 방향이 대각선이 아닐 경우 예외가 발생한다.")
    void horseDiagonalExceptionTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(1, 1);
        Position allDiagonalTo = new Position(3, 3);
        Position notExistDiagonalTo = new Position(1, 3);

        assertThatThrownBy(() -> horse.path(from, allDiagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.");
        assertThatThrownBy(() -> horse.path(from, notExistDiagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.");
    }

    @Test
    @DisplayName("말의 경로에 다른 기물이 존재하면 예외가 발생한다.")
    void horseOtherPieceExistPathExceptionTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(2, 3);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, horse.getPieceInfo());
        pathPieceInfos.put(new Position(1, 2), new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        pathPieceInfos.put(to, new PieceInfo(PieceType.NONE, CountryType.NONE));
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> horse.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 경로에 다른 기물이 존재해 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void horseMoveSameCountryPieceExceptionTest() {
        Piece horse = new Horse(CountryType.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(2, 3);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, horse.getPieceInfo());
        pathPieceInfos.put(new Position(1, 2), new PieceInfo(PieceType.NONE, CountryType.NONE));
        pathPieceInfos.put(to, new PieceInfo(PieceType.SOLDIER, CountryType.CHO));
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> horse.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
