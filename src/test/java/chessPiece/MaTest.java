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

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("maNonIsMovePositionProvider")
    void nonIsMove(BoardPosition boardPosition) {
        //given
        Ma ma = new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> ma.isMove(boardPosition)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 직선으로 한칸 + 대각선으로 한칸 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("maIsMovePositionProvider")
    void isMove(BoardPosition boardPosition) {
        //given
        Ma ma = new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(5, 5));

        //when
        boolean actual = ma.isMove(boardPosition);

        //then
        assertThat(actual).isTrue();

    }

    @DisplayName("마은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Ma ma = new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(5, 5));
        BoardPosition futurePosition = new BoardPosition(3, 6);

        //when
        List<BoardPosition> actual = ma.makeRoute(futurePosition);

        //then
        assertThat(actual).containsExactly(new BoardPosition(4, 5), new BoardPosition(3, 6));
    }

    private static Stream<Arguments> maNonIsMovePositionProvider() {
        return Stream.of(Arguments.of(new BoardPosition(3, 5)), Arguments.of(new BoardPosition(3, 3)),
                Arguments.of(new BoardPosition(3, 7)), Arguments.of(new BoardPosition(5, 7)),
                Arguments.of(new BoardPosition(3, 7)), Arguments.of(new BoardPosition(7, 7)),
                Arguments.of(new BoardPosition(7, 5)), Arguments.of(new BoardPosition(7, 3)),
                Arguments.of(new BoardPosition(5, 3)));
    }

    private static Stream<Arguments> maIsMovePositionProvider() {
        return Stream.of(Arguments.of(new BoardPosition(3, 4)), Arguments.of(new BoardPosition(3, 6)),
                Arguments.of(new BoardPosition(4, 7)), Arguments.of(new BoardPosition(6, 7)),
                Arguments.of(new BoardPosition(7, 6)), Arguments.of(new BoardPosition(7, 4)),
                Arguments.of(new BoardPosition(6, 3)), Arguments.of(new BoardPosition(4, 3)));
    }

}
