package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.TestConstant.D4;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E3;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.F3;
import static testutil.TestConstant.F4;
import static testutil.TestConstant.F5;
import static testutil.TestConstant.F6;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class SoldierTest {

    static Stream<Arguments> 초나라_병사_이동_가능한_경로() {
        return Stream.of(
                Arguments.of(E5, E6),
                Arguments.of(E5, D5),
                Arguments.of(E5, F5)
        );
    }

    static Stream<Arguments> 초나라_병사_이동_불가능한_경로() {
        return Stream.of(
                Arguments.of(E5, E4),
                Arguments.of(E5, D4),
                Arguments.of(E5, F6)
        );
    }

    static Stream<Arguments> 한나라_병사_이동_가능한_경로() {
        return Stream.of(
                Arguments.of(E4, E3),
                Arguments.of(E4, D4),
                Arguments.of(E4, F4)
        );
    }

    static Stream<Arguments> 한나라_병사_이동_불가능한_경로() {
        return Stream.of(
                Arguments.of(E4, E5),
                Arguments.of(E4, D5),
                Arguments.of(E4, F3)
        );
    }

    @ParameterizedTest
    @MethodSource("초나라_병사_이동_가능한_경로")
    void 초나라_병사는_앞_또는_좌우로_한칸_이동할_수_있다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.Cho);
        Board board = new Board(Map.of());

        assertThatCode(() -> soldier.canMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("초나라_병사_이동_불가능한_경로")
    void 초나라_병사는_뒤쪽이나_대각선으로_이동할_수_없다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.Cho);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> soldier.canMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("한나라_병사_이동_가능한_경로")
    void 한나라_병사는_앞_또는_좌우로_한칸_이동할_수_있다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.Han);
        Board board = new Board(Map.of());

        assertThatCode(() -> soldier.canMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("한나라_병사_이동_불가능한_경로")
    void 한나라_병사는_뒤쪽이나_대각선으로_이동할_수_없다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.Han);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> soldier.canMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
