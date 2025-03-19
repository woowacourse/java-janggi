package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Sang sang = new Sang("상", boardPosition);

        //then
        assertThat(sang.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("sangNonMovePositionProvider")
    void nonMove(BoardPosition boardPosition) {
        //given
        Sang sang = new Sang("상", new BoardPosition(5, 5));

        //when //then
        assertThatThrownBy(() -> sang.move(boardPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("상은 자신의 위치를 기준으로 직선으로 한칸 대각선으로 두칸 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("sangMovePositionProvider")
    void move(BoardPosition boardPosition, Sang expected) {
        //given
        Sang sang = new Sang("상", new BoardPosition(5, 5));

        //when
        Sang actual = sang.move(boardPosition);

        //then
        assertThat(actual).isEqualTo(expected);
    }


    private static Stream<Arguments> sangMovePositionProvider() {
        return Stream.of(
                Arguments.of(new BoardPosition(2, 3),
                        new Sang("상", new BoardPosition(2, 3
                        ))),
                Arguments.of(new BoardPosition(2, 7),
                        new Sang("상", new BoardPosition(2, 7
                        ))),
                Arguments.of(new BoardPosition(7, 8),
                        new Sang("상", new BoardPosition(7, 8
                        ))),
                Arguments.of(new BoardPosition(3, 8),
                        new Sang("상", new BoardPosition(3, 8
                        ))),
                Arguments.of(new BoardPosition(8, 3),
                        new Sang("상", new BoardPosition(8, 3
                        ))),
                Arguments.of(new BoardPosition(8, 7),
                        new Sang("상", new BoardPosition(8, 7
                        ))),
                Arguments.of(new BoardPosition(3, 2),
                        new Sang("상", new BoardPosition(3, 2
                        ))),
                Arguments.of(new BoardPosition(7, 2),
                        new Sang("상", new BoardPosition(7, 2
                        )))
        );
    }

    private static Stream<Arguments> sangNonMovePositionProvider() {
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

