package janggi.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MovementTest {

    @DisplayName("출발 좌표와 도착 좌표가 같으면 예외가 발생한다.")
    @Test
    void 출발_좌표와_도착_좌표가_같으면_예외가_발생한다() {
        // given
        Position from = Position.of(1, 1);
        Position to = Position.of(1, 1);

        // when & then
        assertThatThrownBy(() -> new Movement(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
    }

    @DisplayName("from과 to의 행 좌표 차이를 계산한다.")
    @ParameterizedTest(name = "from={0}, to={1}, diff={2}")
    @MethodSource("rowDiffArguments")
    void from과_to의_행_좌표_차이를_계산한다(Position from, Position to, int diff) {
        // given
        Movement movement = new Movement(from, to);

        // when & then
        assertThat(movement.calculateRowDiff()).isEqualTo(diff);
    }

    private static Stream<Arguments> rowDiffArguments() {
        return Stream.of(
                Arguments.of(Position.of(3, 3), Position.of(3, 6), 0),
                Arguments.of(Position.of(1, 7), Position.of(5, 4), 4),
                Arguments.of(Position.of(7, 7), Position.of(2, 4), -5));
    }

    @DisplayName("from과 to의 열 좌표 차이를 계산한다.")
    @ParameterizedTest(name = "from={0}, to={1}, diff={2}")
    @MethodSource("columnDiffArguments")
    void from과_to의_열_좌표_차이를_계산한다(Position from, Position to, int diff) {
        // given
        Movement movement = new Movement(from, to);

        // when & then
        assertThat(movement.calculateColumnDiff()).isEqualTo(diff);
    }

    private static Stream<Arguments> columnDiffArguments() {
        return Stream.of(
                Arguments.of(Position.of(4, 3), Position.of(3, 3), 0),
                Arguments.of(Position.of(1, 2), Position.of(5, 4), 2),
                Arguments.of(Position.of(7, 7), Position.of(2, 4), -3));
    }
}
