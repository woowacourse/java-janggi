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

class JolTest {

    @DisplayName("졸은 이름과 위치 정보를 가진다,")
    @Test
    void jolBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), position);

        //then
        assertThat(jol.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolNonIsMovePositionProvider")
    void nonIsMove(final Position position) {
        //given
        final Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> jol.isMove(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));

        //when
        final boolean actual = jol.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("졸은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));
        final Position futurePosition = new Position(4, 5);

        //when
        final List<Position> actual = jol.makeRoute(futurePosition);

        //then
        assertThat(actual.contains(futurePosition)).isTrue();
    }

    private static Stream<Arguments> jolNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6)));
    }

    private static Stream<Arguments> jolIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 4)),
                Arguments.of(new Position(5, 6)),
                Arguments.of(new Position(4, 5)));
    }

    @DisplayName("졸의 이동 경로에 장애물이 있다면 예외를 던진다.")
    @Test
    void hasObstacle() {
        //given
        final Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));

        final Map<Position, Piece> board = Map.of(
                new Position(6, 5), new Cha(new PieceProfile("차", Nation.HAN), new Position(6, 5))
        );

        final Position futurePosition = new Position(6, 5);

        //when //then
        assertThatThrownBy(() -> jol.checkObstacle(futurePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("졸의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
    @Test
    void nonObstacle() {
        //given
        final Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));

        final Map<Position, Piece> board = Map.of(
                new Position(7, 5), new Cha(new PieceProfile("차", Nation.HAN), new Position(7, 5))
        );

        final Position futurePosition = new Position(6, 5);

        //when //then
        assertThatCode(() -> jol.checkObstacle(futurePosition, board))
                .doesNotThrowAnyException();
    }
}
