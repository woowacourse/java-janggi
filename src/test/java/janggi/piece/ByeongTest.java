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

class ByeongTest {

    @DisplayName("병은 이름과 위치를 가진다.")
    @Test
    void byenogBoardPosition() {
        //given
        final Position position = new Position(0, 0);

        //when
        final Byeong byeong = new Byeong(new PieceProfile("병", Team.HAN), position);

        //then
        assertThat(byeong.getBoardPosition().getCol()).isEqualTo(0);
        assertThat(byeong.getBoardPosition().getRow()).isEqualTo(0);
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면(가로,세로 한칸을 제외한 경로) 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("byeongNonIsMovePositionProvider")
    void nonIsMove(final Position position) {
        //given
        final Byeong byeong = new Byeong(new PieceProfile("병", Team.HAN), new Position(5, 5));

        //when
        assertThatThrownBy(() -> byeong.isMove(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> byeongNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(4, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6))
        );
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동을 할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("byeongIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final Byeong byeong = new Byeong(new PieceProfile("병", Team.HAN), new Position(5, 5));

        //when
        final boolean actual = byeong.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    private static Stream<Arguments> byeongIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(5, 6)),
                Arguments.of(new Position(5, 4))
        );
    }

    @DisplayName("병은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Byeong byeong = new Byeong(new PieceProfile("병", Team.HAN), new Position(5, 5));
        final Position futurePosition = new Position(4, 5);

        //when
        final List<Position> actual = byeong.makeRoute(futurePosition);

        //then
        assertThat(actual.contains(futurePosition)).isTrue();
    }

    @DisplayName("병의 이동 경로에 장애물이 있다면 예외를 던진다.")
    @Test
    void hasObstacle() {
        //given
        final Byeong byeong = new Byeong(new PieceProfile("병", Team.HAN), new Position(5, 5));

        final Map<Position, Piece> board = Map.of(
                new Position(6, 5), new Cha(new PieceProfile("차", Team.HAN), new Position(6, 5))
        );

        final Position futurePosition = new Position(6, 5);

        //when //then
        assertThatThrownBy(() -> byeong.checkObstacle(futurePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("병의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
    @Test
    void nonObstacle() {
        //given
        final Byeong byeong = new Byeong(new PieceProfile("병", Team.HAN), new Position(5, 5));

        final Map<Position, Piece> board = Map.of(
                new Position(7, 5), new Cha(new PieceProfile("차", Team.HAN), new Position(7, 5))
        );

        final Position futurePosition = new Position(6, 5);

        //when //then
        assertThatCode(() -> byeong.checkObstacle(futurePosition, board))
                .doesNotThrowAnyException();
    }
}
