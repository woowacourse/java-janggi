package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.TestConstant.A1;
import static testutil.TestConstant.A2;
import static testutil.TestConstant.A3;
import static testutil.TestConstant.B1;
import static testutil.TestConstant.D1;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.F5;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class GuardTest {

    public static Stream<Arguments> VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, E4),
                Arguments.of(E5, E6),
                Arguments.of(E5, D5),
                Arguments.of(E5, F5)
        );
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 사는_위_아래_왼쪽_오른쪽_으로_움직일_수_있다(Position fromPosition, Position toPosition) {
        // given
        Guard guard = new Guard(Country.CHO);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> guard.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @Test
    void 사는_위_아래_왼쪽_오른쪽_제외하고_움직일_수_없다() {
        // given
        Guard guard = new Guard(Country.CHO);
        Board board = new Board(Map.of());
        // then
        assertThatThrownBy(() -> guard.validateMove(A1, A3, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> guard.validateMove(A2, B1, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> guard.validateMove(B1, D1, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");


    }
}
