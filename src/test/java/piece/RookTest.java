package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.CHO;
import static testutil.TestConstant.A5;
import static testutil.TestConstant.B3;
import static testutil.TestConstant.B5;
import static testutil.TestConstant.C5;
import static testutil.TestConstant.D4;
import static testutil.TestConstant.E1;
import static testutil.TestConstant.E2;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.E8;
import static testutil.TestConstant.E9;
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

public class RookTest {

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


    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 차는_상하좌우_방향으로_이동할_수_있다(Position from, Position to) {
        // given
        Rook rook = new Rook(CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatCode(() -> rook.validateMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("INVALID_MOVE_POSITIONS")
    void 차는_상하좌우가_아닌_경로로는_이동할_수_없다(Position from, Position to) {
        // given
        Rook rook = new Rook(CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> rook.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 차는_중간에_기물이_있으면_이동할_수_없다(Position from, Position to) {
        // given
        Rook rook = new Rook(CHO);
        Board board = new Board(Map.of(
                E4, new Horse(CHO),
                E6, new Horse(CHO),
                C5, new Horse(CHO),
                G5, new Horse(CHO)
        ));

        // when & then
        assertThatThrownBy(() -> rook.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 차는_중간에_기물이_있으면_이동할_수_없다2(Position from, Position to) {
        // given
        Rook rook = new Rook(CHO);
        Board board = new Board(Map.of(
                B5, new Horse(CHO),
                G5, new Horse(CHO),
                E2, new Horse(CHO),
                E8, new Horse(CHO)
        ));

        // when & then
        assertThatThrownBy(() -> rook.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");
    }
}
