package movementRule.omniDirectionMover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class ChoSaTest {

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("JanggunNonCanMoveToPositionProvider")
    void canMoveToValidate(Position position) {
        //given
        ChoSa choSa = new ChoSa();
        Position startPosition = new Position(5, 5);

        //when //then
        assertThatThrownBy(() -> choSa.canMoveTo(startPosition, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("왕은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        ChoSa choSa = new ChoSa();
        Position startPosition = new Position(5, 5);
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = choSa.makeRoute(startPosition, futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isFalse();
    }

    private static Stream<Arguments> JanggunNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(7, 5)),
                Arguments.of(new Position(3, 5)),
                Arguments.of(new Position(5, 7)),
                Arguments.of(new Position(5, 3)),
                Arguments.of(new Position(6, 6)),
                Arguments.of(new Position(4, 6)),
                Arguments.of(new Position(6, 4)),
                Arguments.of(new Position(4, 4))
        );
    }

    @Test
    @DisplayName("궁성을 벗어나면 예외 발생")
    void isNotInPalace() {
        ChoJanggun choJanggun = new ChoJanggun();

        assertThatThrownBy(() ->
                choJanggun.canMoveTo(new Position(0,2), new Position(0,3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");

        assertThatThrownBy(() ->
                choJanggun.canMoveTo(new Position(0,3), new Position(0,2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
