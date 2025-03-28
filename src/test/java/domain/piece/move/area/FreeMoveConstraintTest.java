package domain.piece.move.area;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class FreeMoveConstraintTest {

    static Stream<Position> canMoveAreaTest() {
        return Stream.of(
                Position.of(0, 3),
                Position.of(0, 4),
                Position.of(0, 5),
                Position.of(1, 3),
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(2, 3),
                Position.of(2, 4),
                Position.of(2, 5),
                Position.of(2, 2),
                Position.of(2, 6),
                Position.of(1, 2),
                Position.of(1, 6),
                Position.of(0, 2),
                Position.of(0, 6),
                Position.of(3, 3),
                Position.of(3, 4),
                Position.of(3, 5)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("어떤 위치로도 이동이 가능하다")
    void canMoveAreaTest(Position position) {
        // given
        FreeMoveConstraint freeMoveConstraint = new FreeMoveConstraint();

        // when & then
        assertThat(freeMoveConstraint.canMoveArea(position)).isTrue();
    }
}
