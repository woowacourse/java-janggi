package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.Cho;
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
                Arguments.of(C8),
                Arguments.of(B7),
                Arguments.of(C2),
                Arguments.of(B3),
                Arguments.of(G8),
                Arguments.of(G2),
                Arguments.of(H7),
                Arguments.of(H3)
        );
    }

    static Stream<Arguments> INVALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(D5),
                Arguments.of(F6),
                Arguments.of(G6)
        );
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 상은_시작지와_목적지에_따른_이동경로를_반환한다(Position toPosition) {
        // given
        Elephant elephant = new Elephant(Cho);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> elephant.canMove(E5, toPosition, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("INVALID_MOVE_POSITIONS")
    void 상은_정해진_루트가_아니면_이동할_수_없다(Position toPosition) {
        // given
        Elephant elephant = new Elephant(Cho);
        Board board = new Board(Map.of());

        // then
        assertThatThrownBy(() -> elephant.canMove(E5, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 상은_중간에_기물이_있으면_이동할_수_없다(Position toPosition) {
        // given
        Elephant elephant = new Elephant(Cho);
        Board board = new Board(Map.of(
                D5, new Elephant(Cho),
                F5, new Elephant(Cho),
                E4, new Elephant(Cho),
                E6, new Elephant(Cho)
        ));

        // then
        assertThatThrownBy(() -> elephant.canMove(E5, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");
    }
}
