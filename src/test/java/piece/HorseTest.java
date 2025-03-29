package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.CHO;
import static testutil.TestConstant.C4;
import static testutil.TestConstant.C6;
import static testutil.TestConstant.D3;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.D7;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.E6;
import static testutil.TestConstant.F3;
import static testutil.TestConstant.F5;
import static testutil.TestConstant.F6;
import static testutil.TestConstant.F7;
import static testutil.TestConstant.G4;
import static testutil.TestConstant.G6;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class HorseTest {

    public static Stream<Arguments> VALID_MOVE_POSITIONS() {
        return Stream.of(
                Arguments.of(E5, D7),
                Arguments.of(E5, F7),
                Arguments.of(E5, D3),
                Arguments.of(E5, F3),
                Arguments.of(E5, C6),
                Arguments.of(E5, C4),
                Arguments.of(E5, G6),
                Arguments.of(E5, G4)
        );
    }


    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 말은_시작지와_목적지에_따른_이동경로를_반환한다(Position fromPosition, Position toPosition) {
        // given
        Horse horse = new Horse(Country.CHO);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> horse.validateMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @Test
    void 말은_정해진_루트가_아니면_이동할_수_없다() {
        // given
        Horse horse = new Horse(Country.CHO);
        Board board = new Board(Map.of());

        // then
        assertThatThrownBy(() -> horse.validateMove(E5, E6, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> horse.validateMove(E5, F5, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> horse.validateMove(E5, F6, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("VALID_MOVE_POSITIONS")
    void 말은_중간에_기물이_있으면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Horse horse = new Horse(Country.CHO);
        Board board = new Board(Map.of(
                D5, new Elephant(CHO),
                F5, new Elephant(CHO),
                E4, new Elephant(CHO),
                E6, new Elephant(CHO)
        ));

        // then
        assertThatThrownBy(() -> horse.validateMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");


    }

}
