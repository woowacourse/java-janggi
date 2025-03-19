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

class PoTest {

    @DisplayName("포는 위치 정보를 가진다,")
    @Test
    void poBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Po po = new Po(new PieceProfile("포", Nation.HAN), boardPosition);

        //then
        assertThat(po.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @Test
    void nonMove() {
        //given
        BoardPosition boardPosition = new BoardPosition(0, 0);
        Po po = new Po(new PieceProfile("포", Nation.HAN), boardPosition);

        //when //than
        assertThatThrownBy(() -> po.move(new BoardPosition(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동한다.")
    @ParameterizedTest
    @MethodSource("poMovePositionProvider")
    void move(BoardPosition boardPosition) {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(0, 0));

        //when - then
        assertThatCode(() -> po.move(boardPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> poMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(0, 1),
                        new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(0, 1))),
                Arguments.of(new BoardPosition(1, 0),
                        new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(1, 0)))
        );
    }

}
