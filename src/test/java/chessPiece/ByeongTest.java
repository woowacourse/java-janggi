package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ByeongTest {
    @DisplayName("볃은 이름과 위치를 가진다.")
    @Test
    void byenogBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(0, 0);

        //when
        Byeong byeong = new Byeong(new PieceProfile("병", Nation.HAN), boardPosition);

        //then
        assertThat(byeong.getBoardPosition().getCol()).isEqualTo(0);
        assertThat(byeong.getBoardPosition().getRow()).isEqualTo(0);
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("byeongNonIsMovePositionProvider")
    void nonIsMove(BoardPosition boardPosition) {
        //given
        Byeong byeong = new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(5, 5));

        //when
        assertThatThrownBy(() -> byeong.isMove(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동을 할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("byeongIsMovePositionProvider")
    void isMove(BoardPosition boardPosition) {
        //given
        Byeong byeong = new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(5, 5));

        //when
        boolean actual = byeong.isMove(boardPosition);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("병은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Byeong byeong = new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(5, 5));
        BoardPosition futurePosition = new BoardPosition(4, 5);

        //when
        List<BoardPosition> actual = byeong.makeRoute(futurePosition);

        //then
        assertThat(actual.contains(futurePosition)).isTrue();
    }

    private static Stream<Arguments> byeongNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(4, 5)),
                Arguments.of(new BoardPosition(6, 3)),
                Arguments.of(new BoardPosition(6, 6))
        );
    }

    private static Stream<Arguments> byeongIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(6, 5)),
                Arguments.of(new BoardPosition(5, 6)),
                Arguments.of(new BoardPosition(5, 4))
        );
    }
}
