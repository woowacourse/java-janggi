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

class MaTest {

    @DisplayName("마는 위치 정보를 가진다,")
    @Test
    void maBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Ma ma = new Ma(new PieceProfile("마", Nation.HAN), boardPosition);

        //then
        assertThat(ma.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("maNonMovePositionProvider")
    void nonMove(BoardPosition boardPosition) {
        //given
        Ma ma = new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(5, 5));

        //when then
        assertThatThrownBy(() -> ma.move(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 직선으로 한칸 + 대각선으로 한칸 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("maMovePositionProvider")
    void move(BoardPosition boardPosition) {
        //given
        Ma ma = new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatCode(() -> ma.move(boardPosition))
                .doesNotThrowAnyException();

    }

    private static Stream<Arguments> maNonMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(3, 5)),
                Arguments.of(new BoardPosition(3, 3)),
                Arguments.of(new BoardPosition(3, 7)),
                Arguments.of(new BoardPosition(5, 7)),
                Arguments.of(new BoardPosition(3, 7)),
                Arguments.of(new BoardPosition(7, 7)),
                Arguments.of(new BoardPosition(7, 5)),
                Arguments.of(new BoardPosition(7, 3)),
                Arguments.of(new BoardPosition(5, 3))
        );
    }

    private static Stream<Arguments> maMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(3, 4)),
                Arguments.of(new BoardPosition(3, 6)),
                Arguments.of(new BoardPosition(4, 7)),
                Arguments.of(new BoardPosition(6, 7)),
                Arguments.of(new BoardPosition(7, 6)),
                Arguments.of(new BoardPosition(7, 4)),
                Arguments.of(new BoardPosition(6, 3)),
                Arguments.of(new BoardPosition(4, 3))
        );
    }

}
