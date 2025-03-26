package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import java.util.List;
import org.junit.jupiter.api.Test;
import position.Position;

public class HorseTest {

    @Test
    void 말은_시작지와_목적지에_따른_이동경로를_반환한다() {
        // given
        Horse horse = new Horse();

        // when
        List<Position> path1 = horse.getPathForMoving(E5, D7); // ↑ ↖
        List<Position> path2 = horse.getPathForMoving(E5, F7); // ↑ ↗
        List<Position> path3 = horse.getPathForMoving(E5, D3); // ↓ ↙
        List<Position> path4 = horse.getPathForMoving(E5, F3); // ↓ ↘
        List<Position> path5 = horse.getPathForMoving(E5, C6); // ← ↖
        List<Position> path6 = horse.getPathForMoving(E5, C4); // ← ↙
        List<Position> path7 = horse.getPathForMoving(E5, G6); // → ↗
        List<Position> path8 = horse.getPathForMoving(E5, G4); // → ↘

        // then
        assertThat(path1).containsExactly(E6, D7);
        assertThat(path2).containsExactly(E6, F7);
        assertThat(path3).containsExactly(E4, D3);
        assertThat(path4).containsExactly(E4, F3);
        assertThat(path5).containsExactly(D5, C6);
        assertThat(path6).containsExactly(D5, C4);
        assertThat(path7).containsExactly(F5, G6);
        assertThat(path8).containsExactly(F5, G4);
    }

    @Test
    void 말은_정해진_루트가_아니면_이동할_수_없다() {
        // given
        Horse horse = new Horse();

        // then
        assertThatThrownBy(() -> horse.getPathForMoving(E5, E6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> horse.getPathForMoving(E5, F5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> horse.getPathForMoving(E5, F6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
