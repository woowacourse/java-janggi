package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static piece.Country.Cho;
import static testutil.StaticTest.B3;
import static testutil.StaticTest.B7;
import static testutil.StaticTest.C2;
import static testutil.StaticTest.C8;
import static testutil.StaticTest.D5;
import static testutil.StaticTest.E4;
import static testutil.StaticTest.E5;
import static testutil.StaticTest.E6;
import static testutil.StaticTest.F4;
import static testutil.StaticTest.F6;
import static testutil.StaticTest.G2;
import static testutil.StaticTest.G6;
import static testutil.StaticTest.G8;
import static testutil.StaticTest.H3;
import static testutil.StaticTest.H7;

import game.Board;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ElephantTest {

    @Test
    void 상은_시작지와_목적지에_따른_이동경로를_반환한다() {
        // given
        Elephant elephant = new Elephant(Cho);
        Board board = new Board(Map.of());

        // then
        assertAll(
                () -> assertThatCode(() -> elephant.canMove(E5, C8, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, B7, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, C2, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, B3, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, G8, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, G2, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, H7, board)).doesNotThrowAnyException(),
                () -> assertThatCode(() -> elephant.canMove(E5, H3, board)).doesNotThrowAnyException()
        );
    }

    @Test
    void 상은_정해진_루트가_아니면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant(Cho);
        Board board = new Board(Map.of());

        // then
        assertThatThrownBy(() -> elephant.canMove(E5, D5, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> elephant.canMove(E5, F6, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> elephant.canMove(E5, G6, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 상은_중간에_기물이_있으면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant(Cho);
        Board board = new Board(Map.of(
                D5, new Elephant(Cho),
                F4, new Elephant(Cho),
                E4, new Elephant(Cho),
                E6, new Elephant(Cho)
        ));

        // then
        assertThatThrownBy(() -> elephant.canMove(E5, C8, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");

        assertThatThrownBy(() -> elephant.canMove(E5, B7, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");

        assertThatThrownBy(() -> elephant.canMove(E5, C2, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");
    }


}
