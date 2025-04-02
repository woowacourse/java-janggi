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

class ChaTest {

    private Cha cha;

    @BeforeEach
    void setUp() {
        cha = new Cha(TeamType.CHO);
    }

    @ParameterizedTest
    @MethodSource
    void 차는_전후좌우_장애물_없이_원하는_만큼_이동한다(final int currentY, final int currentX, final int arrivalY, final int arrivalX,
                                    final List<Position> expected) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = cha.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 차는_전후좌우_장애물_없이_원하는_만큼_이동한다() {
        return Stream.of(
                Arguments.of(1, 1, 10, 1, List.of(
                        new Position(2, 1), new Position(3, 1), new Position(4, 1),
                        new Position(5, 1), new Position(6, 1), new Position(7, 1),
                        new Position(8, 1), new Position(9, 1), new Position(10, 1))
                ),

                Arguments.of(1, 2, 1, 1, List.of(new Position(1, 1))),

                Arguments.of(1, 1, 1, 9, List.of(
                        new Position(1, 2), new Position(1, 3), new Position(1, 4),
                        new Position(1, 5), new Position(1, 6), new Position(1, 7),
                        new Position(1, 8), new Position(1, 9))
                ),

                Arguments.of(2, 1, 1, 1, List.of(new Position(1, 1)))
        );
    }

    @Test
    void 차는_한_번에_여러_방향으로_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 5;
        final int arrivalX = 4;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> cha.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 차는 이어진 선을 따라서만 이동할 수 있습니다.");
    }

    @ParameterizedTest
    @MethodSource
    void 차는_궁성_안에서_간선을_따라_대각선으로_이동한다(final int currentY, final int currentX, final int arrivalY,
                                     final int arrivalX, final List<Position> expected) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = cha.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path)
                .isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 차는_궁성_안에서_간선을_따라_대각선으로_이동한다() {
        return Stream.of(
                Arguments.of(1, 4, 3, 6, List.of(new Position(2, 5), new Position(3, 6))),

                Arguments.of(1, 4, 2, 5, List.of(new Position(2, 5))),

                Arguments.of(1, 6, 3, 4, List.of(new Position(2, 5), new Position(3, 4)))
        );
    }

    @Test
    void 차는_궁성_안에서_간선이_이어지지_않았다면_이동할_수_없다() {
        // Given
        final int currentY = 8;
        final int currentX = 5;
        final int arrivalY = 9;
        final int arrivalX = 4;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> cha.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 차는 이어진 선을 따라서만 이동할 수 있습니다.");
    }
}
