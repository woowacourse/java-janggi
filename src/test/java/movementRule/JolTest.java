package movementRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.JOL;

import java.util.stream.Stream;
import movementRule.Jol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class JolTest {

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Jol jol = new Jol();
        Position startPosition = new Position(5, 5);

        //when //then
        assertThatThrownBy(() -> jol.canMoveTo(startPosition, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("졸은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Jol jol = new Jol();
        Position startPosition = new Position(5, 5);
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = jol.makeRoute(startPosition, futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isFalse();
    }

    private static Stream<Arguments> jolNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(4, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6)));
    }

}
