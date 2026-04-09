package domain.position;

import domain.board.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {
    @Test
    @DisplayName("장기판안에 맞는 좌표를 생성할 수 있다.")
    void 장기판에_좌표_생성_테스트() {
        Position position = Position.of(1, 2);

        assertThat(position.getRow()).isEqualTo(1);
        assertThat(position.getColumn()).isEqualTo(2);
    }

    @Test
    @DisplayName("장기판안에 맞지 않는 좌표를 생성할 시 예외가 발생한다")
    void 장기판_좌표_생성_실패_테스트() {
        assertThatThrownBy(() -> Position.of(10, 11))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("appendCases")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트(Position position, Direction direction, Position expected) {
        Position newPosition = position.append(direction);
        assertThat(newPosition).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("distanceCases")
    void 출발지_좌표에서_목적지_좌표까지의_거리_구하기_테스트(Position startPosition, Position endPosition, Coordinate expected) {
        Coordinate coordinate = startPosition.minus(endPosition);
        assertThat(coordinate).isEqualTo(expected);
    }

    static Stream<Arguments> appendCases() {
        return Stream.of(
                Arguments.of(Position.of(5, 5), Direction.UP, Position.of(6,5)),
                Arguments.of(Position.of(5, 5), Direction.UP_RIGHT, Position.of(6,6)),
                Arguments.of(Position.of(5, 5), Direction.RIGHT, Position.of(5,6)),
                Arguments.of(Position.of(5, 5), Direction.DOWN_RIGHT, Position.of(4,6)),
                Arguments.of(Position.of(5, 5), Direction.DOWN, Position.of(4,5)),
                Arguments.of(Position.of(5, 5), Direction.DOWN_LEFT, Position.of(4,4)),
                Arguments.of(Position.of(5, 5), Direction.LEFT, Position.of(5,4)),
                Arguments.of(Position.of(5, 5), Direction.UP_LEFT, Position.of(6,4))
        );
    }

    static Stream<Arguments> distanceCases() {
        return Stream.of(
                Arguments.of(Position.of(4,4), Position.of(5,5), new Coordinate(-1,-1)),
                Arguments.of(Position.of(4,6), Position.of(5,5), new Coordinate(-1,1)),
                Arguments.of(Position.of(6,4), Position.of(5,5), new Coordinate(1,-1)),
                Arguments.of(Position.of(6,6), Position.of(5,5), new Coordinate(1,1))
        );
    }
}
