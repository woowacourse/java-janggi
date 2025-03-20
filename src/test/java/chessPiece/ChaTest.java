package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonIsMove() {
        //given
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new BoardPosition(0, 0));

        //when //then
        assertThatThrownBy(() -> cha.isMove(new BoardPosition(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("chaIsMovePositionProvider")
    void isMove(BoardPosition boardPosition) {
        //given
        Cha cha = new Cha(new PieceProfile("차", Nation.HAN), new BoardPosition(0, 0));

        //when
        boolean actual = cha.isMove(boardPosition);

        //then
        assertThat(actual).isTrue();
    }

    private static Stream<Arguments> chaIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(0, 1)),
                Arguments.of(new BoardPosition(1, 0)));
    }
}
