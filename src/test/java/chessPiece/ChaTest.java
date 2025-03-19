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

class ChaTest {

    @DisplayName("차은 위치 정보를 가진다,")
    @Test
    void chaBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), boardPosition);

        //then
        assertThat(cha.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @Test
    void nonMove() {
        //given
        BoardPosition boardPosition = new BoardPosition(0, 0);
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), boardPosition);

        //when //then
        assertThatThrownBy(() -> cha.move(new BoardPosition(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동한다.")
    @ParameterizedTest
    @MethodSource("chaMovePositionProvider")
    void move(BoardPosition boardPosition) {
        //given
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new BoardPosition(0, 0));

        //when - then
        assertThatCode(() -> cha.move(boardPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> chaMovePositionProvider() {
        return Stream.of(Arguments.of(new BoardPosition(0, 1)), Arguments.of(new BoardPosition(1, 0)));
    }
}
