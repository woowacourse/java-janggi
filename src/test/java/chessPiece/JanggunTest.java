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

class JanggunTest {

    @DisplayName("왕은 위치 정보를 가진다,")
    @Test
    void janggunBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Janggun janggun = new Janggun(new PieceProfile("왕", Nation.HAN), boardPosition);

        //then
        assertThat(janggun.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("JanggunNonIsMovePositionProvider")
    void isMoveValidate(BoardPosition boardPosition) {
        //given
        Janggun janggun = new Janggun(new PieceProfile("왕", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> janggun.isMove(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("왕은 상하좌우 한칸을 움직일 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("janggunIsMovePositionProvider")
    void isMove(BoardPosition boardPosition) {
        //given
        Janggun janggun = new Janggun(new PieceProfile("왕", Nation.HAN), new BoardPosition(5, 5));

        //when
        boolean actual = janggun.isMove(boardPosition);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("왕은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Janggun janggun = new Janggun(new PieceProfile("왕", Nation.HAN), new BoardPosition(5, 5));
        BoardPosition futurePosition = new BoardPosition(4, 5);

        //when
        List<BoardPosition> actual = janggun.makeRoute(futurePosition);

        //then
        assertThat(actual.contains(futurePosition)).isTrue();
    }

    private static Stream<Arguments> JanggunNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(7, 5)),
                Arguments.of(new BoardPosition(3, 5)),
                Arguments.of(new BoardPosition(5, 7)),
                Arguments.of(new BoardPosition(5, 3)),
                Arguments.of(new BoardPosition(6, 6)),
                Arguments.of(new BoardPosition(4, 6)),
                Arguments.of(new BoardPosition(6, 4)),
                Arguments.of(new BoardPosition(4, 4))
        );
    }

    private static Stream<Arguments> janggunIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(6, 5)),
                Arguments.of(new BoardPosition(5, 6)),
                Arguments.of(new BoardPosition(5, 4)),
                Arguments.of(new BoardPosition(4, 5))
        );
    }
}
