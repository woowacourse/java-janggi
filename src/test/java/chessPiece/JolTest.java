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

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("jolNonMovePositionProvider")
    void nonMove(BoardPosition boardPosition) {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new BoardPosition(5, 5));
        //when then
        assertThatThrownBy(() -> jol.move(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource("jolMovePositionProvider")
    void move(BoardPosition boardPosition) {
        //given
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatCode(() -> jol.move(boardPosition))
                .doesNotThrowAnyException();
    }


    private static Stream<Arguments> jolNonMovePositionProvider() {
        return Stream.of(Arguments.of(new BoardPosition(6, 5)), Arguments.of(new BoardPosition(6, 3)),
                Arguments.of(new BoardPosition(6, 6)));
    }

    private static Stream<Arguments> jolMovePositionProvider() {
        return Stream.of(Arguments.of(new BoardPosition(5, 4)), Arguments.of(new BoardPosition(5, 6)),
                Arguments.of(new BoardPosition(4, 5)));
    }
}
