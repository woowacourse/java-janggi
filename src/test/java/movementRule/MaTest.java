package movementRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.MA;

import java.util.stream.Stream;
import movementRule.Ma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class MaTest {

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("maNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Ma ma = new Ma();
        Position startPosition = new Position(5, 5);

        //when //then
        assertThatThrownBy(() -> ma.canMoveTo(startPosition, position)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("마은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Ma ma = new Ma();
        Position startPosition = new Position(5, 5);
        Position futurePosition = new Position(3, 6);

        //when
        Positions actual = ma.makeRoute(startPosition, futurePosition);

        //then
        assertThat(actual.getPositions()).containsExactly(new Position(4, 5));
    }

    private static Stream<Arguments> maNonCanMoveToPositionProvider() {
        return Stream.of(Arguments.of(new Position(3, 5)), Arguments.of(new Position(3, 3)),
                Arguments.of(new Position(3, 7)), Arguments.of(new Position(5, 7)),
                Arguments.of(new Position(3, 7)), Arguments.of(new Position(7, 7)),
                Arguments.of(new Position(7, 5)), Arguments.of(new Position(7, 3)),
                Arguments.of(new Position(5, 3)));
    }

}
