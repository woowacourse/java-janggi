package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

import static testutil.TestConstant.*;

public class GuardTest {

    public static Stream<Arguments> HAN_VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E9, E0), // 상
                Arguments.of(E9, E8), // 하
                Arguments.of(E9, D9), // 좌
                Arguments.of(E9, F9), // 우

                Arguments.of(E9, D0), // 좌상
                Arguments.of(E9, F0), // 우상
                Arguments.of(E9, D8), // 좌하
                Arguments.of(E9, F8), // 우하

                Arguments.of(D0, E9),
                Arguments.of(D8, E9),
                Arguments.of(F0, E9),
                Arguments.of(F9, E9)
        );
    }

    public static Stream<Arguments> CHO_VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E9.reverse(), E0.reverse()),
                Arguments.of(E9.reverse(), E8.reverse()),
                Arguments.of(E9.reverse(), D9.reverse()),
                Arguments.of(E9.reverse(), F9.reverse()),
                Arguments.of(E9.reverse(), D0.reverse()),
                Arguments.of(E9.reverse(), F0.reverse()),
                Arguments.of(E9.reverse(), D8.reverse()),
                Arguments.of(E9.reverse(), F8.reverse()),
                Arguments.of(D0.reverse(), E9.reverse()),
                Arguments.of(D8.reverse(), E9.reverse()),
                Arguments.of(F0.reverse(), E9.reverse()),
                Arguments.of(F9.reverse(), E9.reverse())
        );
    }

    public static Stream<Arguments> HAN_INVALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(D9, E0),  // 궁성 꼭짓점 아닌 곳에서 대각선
                Arguments.of(D9, E8),
                Arguments.of(E0, D9),
                Arguments.of(E0, F9)
        );
    }

    public static Stream<Arguments> HAN_OUT_OF_PALACE_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(D9, C9),
                Arguments.of(D8, C8),
                Arguments.of(E8, E7),
                Arguments.of(F9, G9)
        );
    }

    @ParameterizedTest
    @MethodSource("HAN_VALID_MOVE_POSITIONS")
    void 한_사는_궁성_내_상하좌우_및_대각선으로_이동_가능하다(Position fromPosition, Position toPosition) {
        Guard guard = new Guard(Country.HAN);
        Board board = new Board(Map.of());

        assertThatCode(() -> guard.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("CHO_VALID_MOVE_POSITIONS")
    void 초_사는_궁성_내_상하좌우_및_대각선으로_이동_가능하다(Position fromPosition, Position toPosition) {
        Guard guard = new Guard(Country.CHO);
        Board board = new Board(Map.of());

        assertThatCode(() -> guard.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("HAN_INVALID_MOVE_POSITIONS")
    void 사는_궁성_십자점에서는_대각선으로_이동할_수_없다(Position fromPosition, Position toPosition) {
        Guard guard = new Guard(Country.HAN);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> guard.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("HAN_OUT_OF_PALACE_MOVE_POSITIONS")
    void 사는_궁성_밖으로_이동할_수_없다(Position fromPosition, Position toPosition) {
        Guard guard = new Guard(Country.HAN);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> guard.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("궁성 내에서만 이동할 수 있습니다.");
    }
}
