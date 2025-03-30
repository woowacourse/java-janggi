package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.CHO;
import static piece.Country.HAN;
import static testutil.TestConstant.A5;
import static testutil.TestConstant.B3;
import static testutil.TestConstant.B5;
import static testutil.TestConstant.C5;
import static testutil.TestConstant.D1;
import static testutil.TestConstant.D2;
import static testutil.TestConstant.D3;
import static testutil.TestConstant.D4;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E1;
import static testutil.TestConstant.E2;
import static testutil.TestConstant.E3;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.E7;
import static testutil.TestConstant.E9;
import static testutil.TestConstant.F1;
import static testutil.TestConstant.F2;
import static testutil.TestConstant.F3;
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

    static Stream<Arguments> VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, A5),
                Arguments.of(E5, I5),
                Arguments.of(E5, E1),
                Arguments.of(E5, E9)
        );
    }

    static Stream<Arguments> INVALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, D4),
                Arguments.of(E5, B3),
                Arguments.of(E5, F6)
        );
    }

    static Stream<Arguments> VALID_PALACE_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(D1, F3),
                Arguments.of(F1, D3),
                Arguments.of(F1, D3),
                Arguments.of(D3, F1)
        );
    }



    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 포는_중간에_기물이_하나가_있으면상하좌우_방향으로_이동할_수_있다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                D5, new Elephant(CHO),
                F5, new Elephant(CHO),
                E4, new Elephant(CHO),
                E6, new Elephant(CHO)
        ));

        // when & then
        assertThatCode(() -> cannon.validateMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("INVALID_MOVE_POSITIONS")
    void 포는_상하좌우가_아닌_경로로는_이동할_수_없다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                D5, new Elephant(CHO),
                F5, new Elephant(CHO),
                E4, new Elephant(CHO),
                E6, new Elephant(CHO)
        ));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 포는_중간에_기물이_두_개_이상_있으면_이동할_수_없다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                E4, new Horse(CHO),
                E6, new Horse(CHO),
                C5, new Horse(CHO),
                G5, new Horse(CHO),
                E3, new Horse(CHO),
                E7, new Horse(CHO),
                B5, new Horse(CHO),
                F5, new Horse(CHO)

        ));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 포는_중간에_기물이_포면_이동할_수_없다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                E4, new Cannon(CHO),
                E6, new Cannon(CHO),
                C5, new Cannon(CHO),
                F5, new Cannon(CHO)

        ));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 먹거나 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 포는_마지막에_아군이_있으면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                E4, new Elephant(CHO),
                E6, new Elephant(CHO),
                C5, new Elephant(CHO),
                F5, new Elephant(CHO),
                toPosition, new Elephant(CHO)
        ));

        // then
        assertThatThrownBy(() -> cannon.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아군 기물이 위치해 있습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 포는_마지막에_상대_포가_있으면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                E4, new Elephant(CHO),
                E6, new Elephant(CHO),
                D5, new Elephant(CHO),
                F5, new Elephant(CHO),
                toPosition, new Cannon(HAN)
        ));

        // then
        assertThatThrownBy(() -> cannon.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 먹거나 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_PALACE_MOVE_POSITIONS")
    void 포는_궁에서_중간에_기물이_하나가_있으면상하좌우_방향으로_이동할_수_있다(Position from, Position to) {
        // given
        Cannon cannon = new Cannon(CHO);
        Board board = new Board(Map.of(
                E2, new Elephant(CHO)

        ));

        // when & then
        assertThatCode(() -> cannon.validateMove(from, to, board)).doesNotThrowAnyException();
    }
}
