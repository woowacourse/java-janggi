package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChaTest {

    @DisplayName("차은 위치 정보를 가진다,")
    @Test
    void chaBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final Cha cha = new Cha(new PieceProfile("차", Nation.HAN), position);

        //then
        assertThat(cha.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonIsMove() {
        //given
        final Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(0, 0));

        //when //then
        assertThatThrownBy(() -> cha.isMove(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("chaIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(0, 0));

        //when
        final boolean actual = cha.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    private static Stream<Arguments> chaIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(1, 0)));
    }

    @DisplayName("차는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(5, 5));
        final Position futurePosition = new Position(0, 5);

        //when
        final List<Position> actual = cha.makeRoute(futurePosition);

        //then
        assertThat(actual).containsExactly(
                new Position(4, 5),
                new Position(3, 5),
                new Position(2, 5),
                new Position(1, 5),
                new Position(0, 5)
        );
    }

    @DisplayName("차의 이동 경로에 장애물이 있다면 예외를 던진다.")
    @Test
    void hasObstacle() {
        //given
        final Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(5, 5));

        final Map<Position, Piece> board = Map.of(
                new Position(6, 5), new Byeong(new PieceProfile("병", Nation.HAN), new Position(6, 5))
        );

        final Position futurePosition = new Position(7, 5);

        //when //then
        assertThatThrownBy(() -> cha.checkObstacle(futurePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
    @Test
    void nonObstacle() {
        //given
        final Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new Position(5, 5));

        final Map<Position, Piece> board = Map.of(
                new Position(7, 5), new Byeong(new PieceProfile("병", Nation.HAN), new Position(7, 5))
        );

        final Position futurePosition = new Position(6, 5);

        //when //then
        assertThatCode(() -> cha.checkObstacle(futurePosition, board))
                .doesNotThrowAnyException();
    }

}
