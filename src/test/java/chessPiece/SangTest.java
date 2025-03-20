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

class SangTest {

    @DisplayName("상은 위치 정보를 가진다,")
    @Test
    void sangBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Sang sang = new Sang(new PieceProfile("상", Nation.HAN), boardPosition);

        //then
        assertThat(sang.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("sangNonIsMovePositionProvider")
    void nonIsMove(BoardPosition boardPosition) {
        //given
        Sang sang = new Sang(new PieceProfile("상", Nation.HAN), new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> sang.isMove(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("상은 자신의 위치를 기준으로 직선으로 한칸 대각선으로 두칸 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("sangIsMovePositionProvider")
    void isMove(BoardPosition boardPosition) {
        //given
        Sang sang = new Sang(new PieceProfile("상", Nation.HAN), new BoardPosition(5, 5));

        //when
        boolean actual = sang.isMove(boardPosition);

        //then
        assertThat(actual).isTrue();
    }


    @DisplayName("병은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Sang sang = new Sang(new PieceProfile("상", Nation.HAN), new BoardPosition(5, 5));
        BoardPosition futurePosition = new BoardPosition(3, 2);

        //when
        List<BoardPosition> actual = sang.makeRoute(futurePosition);

        //then
        assertThat(actual).containsExactly(
                new BoardPosition(5, 4),
                new BoardPosition(4, 3),
                new BoardPosition(3, 2)
        );
    }

    private static Stream<Arguments> sangIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(2, 3)),
                Arguments.of(new BoardPosition(2, 7)),
                Arguments.of(new BoardPosition(7, 8)),
                Arguments.of(new BoardPosition(3, 8)),
                Arguments.of(new BoardPosition(8, 3)),
                Arguments.of(new BoardPosition(8, 7)),
                Arguments.of(new BoardPosition(3, 2)),
                Arguments.of(new BoardPosition(7, 2))
        );
    }

    private static Stream<Arguments> sangNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(2, 5)),
                Arguments.of(new BoardPosition(2, 4)),
                Arguments.of(new BoardPosition(2, 6)),
                Arguments.of(new BoardPosition(4, 8)),
                Arguments.of(new BoardPosition(5, 8)),
                Arguments.of(new BoardPosition(6, 8)),
                Arguments.of(new BoardPosition(8, 4)),
                Arguments.of(new BoardPosition(8, 5)),
                Arguments.of(new BoardPosition(8, 6)),
                Arguments.of(new BoardPosition(4, 2)),
                Arguments.of(new BoardPosition(5, 2)),
                Arguments.of(new BoardPosition(6, 2))
        );
    }


}

