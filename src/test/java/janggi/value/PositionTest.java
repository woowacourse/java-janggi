package janggi.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @DisplayName("위치의 합연산을 구할 수 있다.")
    @Test
    void canCalculateSum() {
        Position firstPosition = new Position(1, 2);
        Position secondPosition = new Position(2, 1);

        Position total = firstPosition.calculateSum(secondPosition);
        assertThat(total).isEqualTo(new Position(3, 3));
    }

    @DisplayName("동일한 X축선에 존재하는 출발지에서 목적지까지의 위치값들을 구할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void canMakePositionsInXLine(Position start, Position end, List<Position> expectedResult) {
        List<Position> positions = start.makeInXLine(end);
        assertThat(positions).containsExactlyElementsOf(expectedResult);
    }

    static Stream<Arguments> canMakePositionsInXLine() {
        return Stream.of(
                Arguments.of(new Position(3, 2), new Position(5, 2),
                        List.of(new Position(3, 2), new Position(4, 2), new Position(5, 2))),
                Arguments.of(new Position(5, 2), new Position(3, 2),
                        List.of(new Position(5, 2), new Position(4, 2), new Position(3, 2))),
                Arguments.of(new Position(3, 2), new Position(3, 2),
                        List.of(new Position(3, 2)))
        );
    }

    @DisplayName("동일한 X축선 내에 출발지에서 목적지가 존재하지 않을 경우 예외를 발생시킨다.")
    @Test
    void canNotMakePositionsOutOfXLine() {
        Position start = new Position(3, 2);
        Position end = new Position(5, 3);

        assertThatThrownBy(() -> start.makeInXLine(end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("동일한 X축 선에 시작점과 도착점이 존재하지 않습니다.");
    }

    @DisplayName("동일한 y축선에 존재하는 출발지에서 목적지까지의 위치값들을 구할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void canMakePositionsInYLine(Position start, Position end, List<Position> expectedResult) {
        List<Position> positions = start.makeInYLine(end);
        assertThat(positions).containsExactlyElementsOf(expectedResult);
    }

    static Stream<Arguments> canMakePositionsInYLine() {
        return Stream.of(
                Arguments.of(new Position(2, 3), new Position(2, 5),
                        List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5))),
                Arguments.of(new Position(2, 5), new Position(2, 3),
                        List.of(new Position(2, 5), new Position(2, 4), new Position(2, 3))),
                Arguments.of(new Position(2, 3), new Position(2, 3),
                        List.of(new Position(2, 3)))
        );
    }

    @DisplayName("동일한 Y축선 내에 출발지에서 목적지가 존재하지 않을 경우 예외를 발생시킨다.")
    @Test
    void canNotMakePositionsOutOfYLine() {
        Position start = new Position(3, 5);
        Position end = new Position(2, 3);

        assertThatThrownBy(() -> start.makeInYLine(end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("동일한 y축 선에 시작점과 도착점이 존재하지 않습니다.");
    }

    @DisplayName("기울기가 1인 대각선에 존재하는 출발지에서 목적지까지의 위치값들을 구할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void canMakePositionsInPlusOneSlopDiagonal(Position start, Position end, List<Position> expectedResult) {
        List<Position> positions = start.makeInDiagonalWithPlusOneSlop(end);
        assertThat(positions).containsExactlyElementsOf(expectedResult);
    }

    static Stream<Arguments> canMakePositionsInPlusOneSlopDiagonal() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(2, 2),
                        List.of(new Position(0, 0), new Position(1, 1), new Position(2, 2))),
                Arguments.of(new Position(2, 2), new Position(0, 0),
                        List.of(new Position(2, 2), new Position(1, 1), new Position(0, 0))),
                Arguments.of(new Position(0, 0), new Position(0, 0),
                        List.of(new Position(0, 0)))
        );
    }

    @DisplayName("시작점과 도착점이 기울기 1 대각선을 이루지 않을 경우 예외를 발생시킨다.")
    @Test
    void canNotMakePositionsOutOfPlusOneSlopDiagonal() {
        Position start = new Position(0, 1);
        Position end = new Position(0, 2);

        assertThatThrownBy(() -> start.makeInDiagonalWithPlusOneSlop(end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작점과 도착점이 기울기 1 대각선을 이루지 않습니다.");
    }

    @DisplayName("기울기가 -1인 대각선에 존재하는 출발지에서 목적지까지의 위치값들을 구할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void canMakePositionsInMinusOneSlopDiagonal(Position start, Position end, List<Position> expectedResult) {
        List<Position> positions = start.makeInDiagonalWithMinusOneSlop(end);
        assertThat(positions).containsExactlyElementsOf(expectedResult);
    }

    static Stream<Arguments> canMakePositionsInMinusOneSlopDiagonal() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(2, -2),
                        List.of(new Position(0, 0), new Position(1, -1), new Position(2, -2))),
                Arguments.of(new Position(-2, 2), new Position(0, 0),
                        List.of(new Position(-2, 2), new Position(-1, 1), new Position(0, 0))),
                Arguments.of(new Position(0, 0), new Position(0, 0),
                        List.of(new Position(0, 0)))
        );
    }

    @DisplayName("시작점과 도착점이 기울기 -1 대각선을 이루지 않을 경우 예외를 발생시킨다.")
    @Test
    void canNotMakePositionsOutOfMinusOneSlopDiagonal() {
        Position start = new Position(0, 1);
        Position end = new Position(0, 2);

        assertThatThrownBy(() -> start.makeInDiagonalWithMinusOneSlop(end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작점과 도착점이 기울기 -1 대각선을 이루지 않습니다.");
    }
}