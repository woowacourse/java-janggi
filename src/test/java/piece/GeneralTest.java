package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.TestConstant.C8;
import static testutil.TestConstant.C9;
import static testutil.TestConstant.D0;
import static testutil.TestConstant.D8;
import static testutil.TestConstant.D9;
import static testutil.TestConstant.E0;
import static testutil.TestConstant.E7;
import static testutil.TestConstant.E8;
import static testutil.TestConstant.E9;
import static testutil.TestConstant.F0;
import static testutil.TestConstant.F8;
import static testutil.TestConstant.F9;
import static testutil.TestConstant.G9;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class GeneralTest {


    public static Stream<Arguments> HAN_VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E9, E0),
                Arguments.of(E9, E8),
                Arguments.of(E9, D9),
                Arguments.of(E9, F9),

                Arguments.of(E9, D0),
                Arguments.of(E9, F0),
                Arguments.of(E9, D8),
                Arguments.of(E9, F8),

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

                Arguments.of(D9, E0),
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
    void 한_장군은_궁성_중앙에서_상하좌우_대각선로_움직일_수_있다(Position fromPosition, Position toPosition) {
        // given
        General general = new General(Country.HAN);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> general.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("CHO_VALID_MOVE_POSITIONS")
    void 초_장군은_궁성_중앙에서_상하좌우_대각선로_움직일_수_있다2(Position fromPosition, Position toPosition) {
        // given
        General general = new General(Country.CHO);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> general.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("HAN_INVALID_MOVE_POSITIONS")
    void 장군은_궁성_십자_부분에서_대각선_이동_할_수_없다(Position fromPosition, Position toPosition) {
        General general = new General(Country.HAN);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> general.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(  "해당 위치로 이동할 수 없습니다.");

    }

    @ParameterizedTest
    @MethodSource("HAN_OUT_OF_PALACE_MOVE_POSITIONS")
    void 장군은_궁성_밖으로_나갈_수_없다(Position fromPosition, Position toPosition) {
        General general = new General(Country.HAN);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> general.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage( "궁성 내에서만 이동할 수 있습니다.");

    }




}
