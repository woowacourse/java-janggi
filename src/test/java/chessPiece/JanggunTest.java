package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Janggun janggun = new Janggun("왕", boardPosition);

        //then
        assertThat(janggun.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("왕이 움직일 수 없는 위치이면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("JanggunNonMovePositionProvider")
    void moveValidate(BoardPosition boardPosition) {
        //given
        Janggun janggun = new Janggun("왕", new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> janggun.move(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("왕은 상하좌우 한칸을 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource("janggunMovePositionProvider")
    void move(BoardPosition boardPosition, Janggun expected) {
        //given
        Janggun janggun = new Janggun("왕", new BoardPosition(5, 5));

        //when
        Janggun actual = janggun.move(boardPosition);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> JanggunNonMovePositionProvider() {
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

    private static Stream<Arguments> janggunMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(6, 5),
                        new Janggun("왕", new BoardPosition(6, 5))),
                Arguments.of(new BoardPosition(5, 6),
                        new Janggun("왕", new BoardPosition(5, 6))),
                Arguments.of(new BoardPosition(5, 4),
                        new Janggun("왕", new BoardPosition(5, 4))),
                Arguments.of(new BoardPosition(4, 5),
                        new Janggun("왕", new BoardPosition(4, 5)))
        );
    }


}
