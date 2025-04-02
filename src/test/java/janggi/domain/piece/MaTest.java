package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MaTest {

    private Ma ma;

    @BeforeEach
    void setUp() {
        ma = new Ma(TeamType.CHO);
    }

    @ParameterizedTest
    @MethodSource
    void 마는_한_칸_전후좌후_이동_후_좌우로_대각선_한_칸_움직인다(final int currentY, final int currentX, final int arrivalY,
                                           final int arrivalX,
                                           final List<Position> expected) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = ma.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 마는_한_칸_전후좌후_이동_후_좌우로_대각선_한_칸_움직인다() {
        return Stream.of(
                Arguments.of(3, 3, 5, 4, List.of(
                        new Position(4, 3), new Position(5, 4))
                ),
                Arguments.of(3, 3, 5, 2, List.of(
                        new Position(4, 3), new Position(5, 2))
                ),
                Arguments.of(3, 3, 1, 2, List.of(
                        new Position(2, 3), new Position(1, 2))
                ),
                Arguments.of(3, 3, 1, 4, List.of(
                        new Position(2, 3), new Position(1, 4))
                ),
                Arguments.of(3, 3, 4, 5, List.of(
                        new Position(3, 4), new Position(4, 5))
                ),
                Arguments.of(3, 3, 2, 5, List.of(
                        new Position(3, 4), new Position(2, 5))
                ),
                Arguments.of(3, 3, 4, 1, List.of(
                        new Position(3, 2), new Position(4, 1))
                ),
                Arguments.of(3, 3, 2, 1, List.of(
                        new Position(3, 2), new Position(2, 1))
                )
        );
    }

    @Test
    void 마는_정해진_방식_이외의_방법으로_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 4;
        final int arrivalX = 6;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> ma.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 말은 직선 1칸 이동 후 대각선 1칸으로만 이동할 수 있습니다.");
    }
}
