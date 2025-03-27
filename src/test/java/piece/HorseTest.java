package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Country.Cho;
import static testutil.StaticTest.C4;
import static testutil.StaticTest.C6;
import static testutil.StaticTest.D3;
import static testutil.StaticTest.D5;
import static testutil.StaticTest.D7;
import static testutil.StaticTest.E4;
import static testutil.StaticTest.E5;
import static testutil.StaticTest.E6;
import static testutil.StaticTest.F3;
import static testutil.StaticTest.F5;
import static testutil.StaticTest.F6;
import static testutil.StaticTest.F7;
import static testutil.StaticTest.G4;
import static testutil.StaticTest.G6;

import game.Board;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class HorseTest {

    public static Stream<Arguments> horseFromToPositions() {
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
    @MethodSource("horseFromToPositions")
    void 말은_시작지와_목적지에_따른_이동경로를_반환한다(Position fromPosition, Position toPosition) {
        // given
        Horse horse = new Horse(Country.Cho);
        Board board = new Board(Map.of());

        // then
        assertThatCode(() -> horse.canMove(fromPosition, toPosition, board)).doesNotThrowAnyException();
    }

    @Test
    void 말은_정해진_루트가_아니면_이동할_수_없다() {
        // given
        Horse horse = new Horse(Country.Cho);
        Board board = new Board(Map.of());

        // then
        assertThatThrownBy(() -> horse.canMove(E5, E6, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> horse.canMove(E5, F5, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> horse.canMove(E5, F6, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("horseFromToPositions")
    void 말은_중간에_기물이_있으면_이동할_수_없다(Position fromPosition, Position toPosition) {
        // given
        Horse horse = new Horse(Country.Cho);
        Board board = new Board(Map.of(
                D5, new Elephant(Cho),
                F5, new Elephant(Cho),
                E4, new Elephant(Cho),
                E6, new Elephant(Cho)
        ));

        // then
        assertThatThrownBy(() -> horse.canMove(fromPosition, toPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");


    }

}
