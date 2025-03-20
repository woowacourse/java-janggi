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

class JolTest {

    @DisplayName("졸병은 이름과 위치 정보를 가진다,")
    @Test
    void jolByeongBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), boardPosition);

        //then
        assertThat(jol.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolNonIsMovePositionProvider")
    void nonIsMove(BoardPosition boardPosition) {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> jol.isMove(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolIsMovePositionProvider")
    void isMove(BoardPosition boardPosition) {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new BoardPosition(5, 5));

        //when
        boolean actual = jol.isMove(boardPosition);

        //then
        assertThat(actual).isTrue();
    }


    @DisplayName("졸은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new BoardPosition(5, 5));
        BoardPosition futurePosition = new BoardPosition(4, 5);

        //when
        List<BoardPosition> actual = jol.makeRoute(futurePosition);

        //then
        assertThat(actual.contains(futurePosition)).isTrue();
    }

    private static Stream<Arguments> jolNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(6, 5)),
                Arguments.of(new BoardPosition(6, 3)),
                Arguments.of(new BoardPosition(6, 6)));
    }

    private static Stream<Arguments> jolIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(5, 4)),
                Arguments.of(new BoardPosition(5, 6)),
                Arguments.of(new BoardPosition(4, 5)));
    }
}
