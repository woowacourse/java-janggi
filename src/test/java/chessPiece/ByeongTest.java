package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @MethodSource("byeongNonMovePositionProvider")
    void nonMove(BoardPosition boardPosition) {
        //given
        Byeong byeong = new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(5, 5));
        //when then
        assertThatThrownBy(() -> byeong.move(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource("byeongMovePositionProvider")
    void move(BoardPosition boardPosition) {
        //given
        Byeong byeong = new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatCode(() -> byeong.move(boardPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> byeongNonMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(4, 5)),
                Arguments.of(new BoardPosition(6, 3)),
                Arguments.of(new BoardPosition(6, 6))
        );
    }

    private static Stream<Arguments> byeongMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(6, 5)),
                Arguments.of(new BoardPosition(5, 6)),
                Arguments.of(new BoardPosition(5, 4))
        );
    }

}
