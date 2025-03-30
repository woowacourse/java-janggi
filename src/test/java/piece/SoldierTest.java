package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.TestConstant.C9;
import static testutil.TestConstant.D1;
import static testutil.TestConstant.D3;
import static testutil.TestConstant.D4;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.D8;
import static testutil.TestConstant.D9;
import static testutil.TestConstant.E2;
import static testutil.TestConstant.E3;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.E7;
import static testutil.TestConstant.E8;
import static testutil.TestConstant.E9;
import static testutil.TestConstant.F1;
import static testutil.TestConstant.F3;
import static testutil.TestConstant.F4;
import static testutil.TestConstant.F5;
import static testutil.TestConstant.F6;
import static testutil.TestConstant.F8;
import static testutil.TestConstant.F9;
import static testutil.TestConstant.G9;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class SoldierTest {

    static Stream<Arguments> CHO_VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, E6),
                Arguments.of(E5, D5),
                Arguments.of(E5, F5)
        );
    }

    static Stream<Arguments> CHO_INVALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, E4),
                Arguments.of(E5, D4),
                Arguments.of(E5, F6)
        );
    }

    static Stream<Arguments> HAN_VALID_POSITIONS() {
        return Stream.of(
                Arguments.of(E4, E3),
                Arguments.of(E4, D4),
                Arguments.of(E4, F4)
        );
    }

    static Stream<Arguments> HAN_INVALID_POSITIONS() {
        return Stream.of(
                Arguments.of(E4, E5),
                Arguments.of(E4, D5),
                Arguments.of(E4, F3)
        );
    }

    static Stream<Arguments> HAN_PALACE_DIAGONAL_POSITIONS() {
        return Stream.of(
                Arguments.of(E2, D1),
                Arguments.of(E2, F1),
                Arguments.of(D3, E2),
                Arguments.of(F3, E2)
        );
    }

    static Stream<Arguments> CHO_PALACE_DIAGONAL_POSITIONS() {
        return Stream.of(
                Arguments.of(E2.reverse(), D1.reverse()),
                Arguments.of(E2.reverse(), F1.reverse()),
                Arguments.of(D3.reverse(), E2.reverse()),
                Arguments.of(F3.reverse(), E2.reverse())
        );
    }

    static Stream<Arguments> HAN_PALACE_INVALID_DIAGONAL_POSITIONS() {
        return Stream.of(
                Arguments.of(D9, E8),
                Arguments.of(F9, E8),
                Arguments.of(D9, F9)
        );
    }



    @ParameterizedTest
    @MethodSource("CHO_VALID_MOVE_POSITIONS")
    void 초나라_병사는_앞_또는_좌우로_한칸_이동할_수_있다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.CHO);
        Board board = new Board(Map.of());

        assertThatCode(() -> soldier.validateMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("CHO_INVALID_MOVE_POSITIONS")
    void 초나라_병사는_뒤쪽이나_대각선으로_이동할_수_없다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.CHO);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> soldier.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("HAN_VALID_POSITIONS")
    void 한나라_병사는_앞_또는_좌우로_한칸_이동할_수_있다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.HAN);
        Board board = new Board(Map.of());

        assertThatCode(() -> soldier.validateMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("HAN_INVALID_POSITIONS")
    void 한나라_병사는_뒤쪽이나_대각선으로_이동할_수_없다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.HAN);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> soldier.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("HAN_PALACE_DIAGONAL_POSITIONS")
    void 한나라_병사는_궁성_중앙과_꼭짓점_사이_대각선_이동이_가능하다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.HAN);
        Board board = new Board(Map.of());

        assertThatCode(() -> soldier.validateMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("CHO_PALACE_DIAGONAL_POSITIONS")
    void 초나라_병사는_궁성_중앙과_꼭짓점_사이_대각선_이동이_가능하다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.CHO);
        Board board = new Board(Map.of());

        assertThatCode(() -> soldier.validateMove(from, to, board)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("HAN_PALACE_INVALID_DIAGONAL_POSITIONS")
    void 병사는_궁성_십자점에서_대각선으로_이동할_수_없다(Position from, Position to) {
        Soldier soldier = new Soldier(Country.HAN);
        Board board = new Board(Map.of());

        assertThatThrownBy(() -> soldier.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
