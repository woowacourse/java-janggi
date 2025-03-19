package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SaTest {

    @DisplayName("사는 위치 정보를 가진다,")
    @Test
    void saBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Sa sa = new Sa("사", boardPosition);

        //then
        assertThat(sa.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("사가 움직일 수 없는 위치이면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("saNonMovePositionProvider")
    void moveValidate(BoardPosition boardPosition) {
        //given
        Sa sa = new Sa("사", new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> sa.move(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("사는 상하좌우 한칸을 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource("saMovePositionProvider")
    void move(BoardPosition boardPosition, Sa expected) {
        //given
        Sa sa = new Sa("사", new BoardPosition(5, 5));

        //when
        Sa actual = sa.move(boardPosition);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> saNonMovePositionProvider() {
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

    private static Stream<Arguments> saMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(6, 5),
                        new Sa("사", new BoardPosition(6, 5))),
                Arguments.of(new BoardPosition(5, 6),
                        new Sa("사", new BoardPosition(5, 6))),
                Arguments.of(new BoardPosition(5, 4),
                        new Sa("사", new BoardPosition(5, 4))),
                Arguments.of(new BoardPosition(4, 5),
                        new Sa("사", new BoardPosition(4, 5)))
        );
    }

}
