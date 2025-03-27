package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.Cho;
import static testutil.TestConstant.A5;
import static testutil.TestConstant.B3;
import static testutil.TestConstant.B5;
import static testutil.TestConstant.C5;
import static testutil.TestConstant.D4;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E1;
import static testutil.TestConstant.E3;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.E7;
import static testutil.TestConstant.E9;
import static testutil.TestConstant.F5;
import static testutil.TestConstant.F6;
import static testutil.TestConstant.G5;
import static testutil.TestConstant.I5;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class CannonTest {

    static Stream<Arguments> validMovePositions() {
        return Stream.of(
                Arguments.of(E5, A5),
                Arguments.of(E5, I5),
                Arguments.of(E5, E1),
                Arguments.of(E5, E9)
        );
    }

    static Stream<Arguments> invalidMovePositions() {
        return Stream.of(
                Arguments.of(E5, D4),
                Arguments.of(E5, B3),
                Arguments.of(E5, F6)
        );
    }

    static Stream<Arguments> blockedMovePositions() {
        return Stream.of(
                Arguments.of(E5, A5),
                Arguments.of(E5, I5),
                Arguments.of(E5, E1),
                Arguments.of(E5, E9)
        );
    }

    @ParameterizedTest
    @MethodSource("validMovePositions")
    void 포는_중간에_기물이_하나가_있으면상하좌우_방향으로_이동할_수_있다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(Cho);
        Board board = new Board(Map.of(
                D5, new Elephant(Cho),
                F5, new Elephant(Cho),
                E4, new Elephant(Cho),
                E6, new Elephant(Cho)
        ));

        // when & then
        assertThatCode(() -> cannon.canMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("invalidMovePositions")
    void 포는_상하좌우가_아닌_경로로는_이동할_수_없다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(Cho);
        Board board = new Board(Map.of(
                D5, new Elephant(Cho),
                F5, new Elephant(Cho),
                E4, new Elephant(Cho),
                E6, new Elephant(Cho)
        ));

        // when & then
        assertThatThrownBy(() -> cannon.canMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("blockedMovePositions")
    void 포는_중간에_기물이_두_개_이상_있으면_이동할_수_없다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(Cho);
        Board board = new Board(Map.of(
                E4, new Horse(Cho),
                E6, new Horse(Cho),
                C5, new Horse(Cho),
                G5, new Horse(Cho),
                E3, new Horse(Cho),
                E7, new Horse(Cho),
                B5, new Horse(Cho),
                F5, new Horse(Cho)

        ));

        // when & then
        assertThatThrownBy(() -> cannon.canMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("blockedMovePositions")
    void 포는_중간에_기물이_포면_이동할_수_없다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(Cho);
        Board board = new Board(Map.of(
                E4, new Cannon(Cho),
                E6, new Cannon(Cho),
                C5, new Cannon(Cho),
                F5, new Cannon(Cho)

        ));

        // when & then
        assertThatThrownBy(() -> cannon.canMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 해당 위치로 이동할 수 없습니다.");
    }
}
