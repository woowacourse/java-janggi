package janggi.domain;

import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @ParameterizedTest
    @DisplayName("기물의 X 좌표 범위는 1~9, Y 좌표 범위는 1~10 이다.")
    @CsvSource({
            "1,1",
            "4,6",
            "7,10",
            "9,10"
    })
    void testPositionWithinValidXYRange(int x, int y) {
        assertThat(new Position(x, y))
                .isInstanceOf(Position.class);
    }

    @ParameterizedTest
    @DisplayName("기물의 X 좌표가 1~9 사이가 아니면 예외가 발생한다.")
    @CsvSource({
            "0,1",
            "10,5",
            "15,8"
    })
    void testPositionWithinValidXRange(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("기물의 Y 좌표가 1~10 사이가 아니면 예외가 발생한다.")
    @CsvSource({
            "1,0",
            "4,11",
            "7,15"
    })
    void testPositionWithinValidYRange(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("같은 X,Y 좌표를 가진 Position은 서로 동등하다.")
    @CsvSource({
            "1, 1, 1, 1",
            "1, 10, 1, 10",
            "9, 10, 9, 10"
    })
    void testPositionEquality(int x1, int y1, int x2, int y2) {
        Position p1 = new Position(x1, y1);
        Position p2 = new Position(x2, y2);

        assertThat(p1).isEqualTo(p2);
    }

    @ParameterizedTest
    @DisplayName("두 위치 사이의 X 거리를 계산한다.")
    @CsvSource({
            "1, 1, 5, 1, 4",
            "5, 1, 1, 1, -4",
            "3, 3, 3, 4, 0"
    })
    void testCalculateX(int x1, int y1, int x2, int y2, int result) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.calculateX(to)).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("두 위치 사이의 Y 거리를 계산한다.")
    @CsvSource({
            "1, 1, 1, 5, 4",
            "1, 5, 1, 1, -4",
            "3, 3, 7, 3, 0"
    })
    void testCalculateY(int x1, int y1, int x2, int y2, int result) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.calculateY(to)).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("두 위치 사이의 맨해튼 거리를 계산한다.")
    @CsvSource({
            "5, 5, 5, 6, 1",
            "5, 5, 6, 5, 1",
            "1, 1, 3, 4, 5",
            "5, 5, 5, 5, 0"
    })
    void testIsApartFrom(int x1, int y1, int x2, int y2, int result) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.calculateManhattanDistance(to)).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("같은 행 또는 같은 열에 있으면 직선상에 있다.")
    @CsvSource({
            "1, 1, 1, 5",
            "1, 1, 9, 1",
            "5, 3, 5, 10"
    })
    void testTrueWhenInSameLine(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.isInSameLine(to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("다른 행과 다른 열에 있거나 동일 위치이면 직선상에 있지 않다.")
    @CsvSource({
            "1, 1, 2, 2",
            "3, 3, 5, 7",
            "5, 5, 5, 5"
    })
    void testFalseWhenSameLineFalse(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.isInSameLine(to)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("두 위치 사이 거리가 주어진 값 쌍과 일치하면 true를 반환한다.")
    @CsvSource({
            "5, 5, 6, 7, 1, 2",
            "5, 5, 6, 7, 2, 1",
            "5, 5, 7, 6, 1, 2",
            "5, 5, 7, 6, 2, 1",
            "5, 5, 3, 4, 1, 2",
            "5, 5, 7, 8, 2, 3",
            "5, 5, 8, 7, 2, 3",
            "5, 5, 3, 2, 2, 3"
    })
    void testTrueWhenIsMatchDistance(int x1, int y1, int x2, int y2, int v1, int v2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.isMatchDistance(to, v1, v2)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("두 위치 사이 거리가 주어진 값 쌍과 일치하지 않으면 false를 반환한다.")
    @CsvSource({
            "5, 5, 6, 6, 1, 2",
            "5, 5, 8, 8, 2, 3",
            "5, 5, 5, 6, 1, 2"
    })
    void testFalseWhenIsMatchDistance(int x1, int y1, int x2, int y2, int v1, int v2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.isMatchDistance(to, v1, v2)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("시작 위치에서 도착 위치 방향으로 직선 한 칸 이동한다.")
    @CsvSource({
            "5, 5, 8, 5, 6, 5",
            "5, 5, 2, 5, 4, 5",
            "5, 5, 5, 8, 5, 6",
            "5, 5, 5, 2, 5, 4",
            "5, 5, 7, 6, 6, 5"
    })
    void testMoveStraight(int x1, int y1, int x2, int y2, int nx, int ny) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when
        Position position = from.moveStraight(to);

        // then
        assertThat(position).isEqualTo(new Position(nx, ny));
    }

    @ParameterizedTest
    @DisplayName("도착지를 향해 대각선 방향으로 1칸 이동한 위치를 반환한다.")
    @CsvSource({
            "5, 5, 7, 7, 6, 6",
            "5, 5, 3, 7, 4, 6",
            "5, 5, 7, 3, 6, 4",
            "5, 5, 3, 3, 4, 4"
    })
    void testMoveDiagonal(int x1, int y1, int x2, int y2, int nx, int ny) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when
        Position expected = new Position(nx, ny);

        // then
        assertThat(from.moveDiagonal(to)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("좌표가 대각선 상에 존재하면 true를 반환한다.")
    @CsvSource({
            "5, 4, 6, 5",
            "1, 2, 2, 3",
            "5, 5, 6, 6",
            "1, 1, 6, 6",
            "6, 1, 4, 3"
    })
    void testIsOnSameDiagonal(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.isOneSameDiagonal(to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("좌표가 대각선 상에 존재하지 않으면 false를 반환한다.")
    @CsvSource({
            "1, 1, 1, 3",
            "1, 2, 2, 4",
            "1, 5, 6, 6"
    })
    void testIsNotOnSameDiagonal(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(from.isOneSameDiagonal(to)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("위치가 범위 안에 있으면 true를 반환한다.")
    @CsvSource({
            "4, 1",
            "5, 2",
            "6, 3"
    })
    void testTrueWhenInRange(int x, int y) {
        // given
        Position position = new Position(x, y);

        // when & then
        assertThat(position.isInRange(4, 6, 1, 3)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("위치가 범위 밖에 있으면 false를 반환한다.")
    @CsvSource({
            "3, 2",
            "7, 2",
            "4, 4",
            "5, 4"
    })
    void testFalseWhenOutOfRange(int x, int y) {
        // given
        Position position = new Position(x, y);

        // when & then
        assertThat(position.isInRange(4, 6, 1, 3)).isFalse();
    }
}
