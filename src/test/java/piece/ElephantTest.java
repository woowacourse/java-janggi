package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.CHO;
import static piece.Country.HAN;
import static testutil.TestConstant.B3;
import static testutil.TestConstant.B7;
import static testutil.TestConstant.C2;
import static testutil.TestConstant.C8;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.F5;
import static testutil.TestConstant.F6;
import static testutil.TestConstant.G2;
import static testutil.TestConstant.G6;
import static testutil.TestConstant.G8;
import static testutil.TestConstant.H3;
import static testutil.TestConstant.H7;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class ElephantTest {

    static Stream<Arguments> VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, C8),
                Arguments.of(E5, B7),
                Arguments.of(E5, C2),
                Arguments.of(E5, B3),
                Arguments.of(E5, G8),
                Arguments.of(E5, G2),
                Arguments.of(E5, H7),
                Arguments.of(E5, H3),
                Arguments.of(E5, H3)
        );
    }

    static Stream<Arguments> INVALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, D5),
                Arguments.of(E5, F6),
                Arguments.of(E5, G6)
        );
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 상은_정해진_루트로_이동_가능하다(Position fromPosition, Position toPosition) {
        // given
        Elephant elephant = new Elephant(CHO);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> elephant.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("INVALID_MOVE_POSITIONS")
    void 상은_정해진_루트가_아니면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Elephant elephant = new Elephant(CHO);
        Board board = new Board(Map.of());

        // then
        assertThatThrownBy(() -> elephant.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 상은_중간에_기물이_있으면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Elephant elephant = new Elephant(CHO);
        Board board = new Board(Map.of(
                D5, new Elephant(CHO),
                F5, new Elephant(CHO),
                E4, new Elephant(CHO),
                E6, new Elephant(CHO)
        ));

        // then
        assertThatThrownBy(() -> elephant.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 상은_마지막에_아군이_있으면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Elephant elephant = new Elephant(CHO);
        Board board = new Board(Map.of(
                toPosition, new Elephant(CHO)
        ));

        // then
        assertThatThrownBy(() -> elephant.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage( "아군 기물이 위치해 있습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 상은_마지막에_상대_기물이_있으면_이동할_수_있다(Position fromPosition, Position toPosition) {
        // given
        Elephant elephant = new Elephant(CHO);
        Board board = new Board(Map.of(
                toPosition, new Elephant(HAN)
        ));

        // then
        assertThatCode(() -> elephant.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }
}
