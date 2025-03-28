package domain.piece.move.area;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceOnlyConstraintTest {

    static Stream<Position> canMoveAreaTrue() {
        return Stream.of(
                Position.of(0, 3),
                Position.of(0, 4),
                Position.of(0, 5),
                Position.of(1, 3),
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(2, 3),
                Position.of(2, 4),
                Position.of(2, 5)
        );
    }

    static Stream<Position> canMoveAreaFalse() {
        return Stream.of(
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
    @DisplayName("좌표가 궁성 내부에 존재하면 true를 반환한다")
    void canMoveAreaTrue(Position position) {
        // given
        PalaceOnlyConstraint palaceOnlyConstraint = new PalaceOnlyConstraint();

        // when & then
        boolean actual = palaceOnlyConstraint.canMoveArea(position);
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("좌표가 궁성 내부에 존재하면 false를 반환한다")
    void canMoveAreaFalse(Position position) {
        // given
        PalaceOnlyConstraint palaceOnlyConstraint = new PalaceOnlyConstraint();

        // when & then
        boolean actual = palaceOnlyConstraint.canMoveArea(position);
        assertThat(actual).isFalse();
    }
}
