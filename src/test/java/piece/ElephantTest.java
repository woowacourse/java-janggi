package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testutil.StaticTest.C8;
import static testutil.StaticTest.D5;
import static testutil.StaticTest.D7;
import static testutil.StaticTest.E5;
import static testutil.StaticTest.E6;
import static testutil.StaticTest.F6;
import static testutil.StaticTest.G6;

import java.util.List;
import org.junit.jupiter.api.Test;
import position.Position;

public class ElephantTest {

    @Test
    void 상은_시작지와_목적지에_따른_이동경로를_반환한다() {
        // given
        Elephant elephant = new Elephant();

        // when
        List<Position> path1 = elephant.getPathForMoving(E5, C8); // ↑ ↖ ↖
//        List<Position> path2 = elephant.getPathForMoving(E5, G8); // ↑ ↗ ↗
//        List<Position> path3 = elephant.getPathForMoving(E5, C2); // ↓ ↙ ↙
//        List<Position> path4 = elephant.getPathForMoving(E5, G2); // ↓ ↘ ↘
//        List<Position> path5 = elephant.getPathForMoving(E5, B7); // ← ↖ ↖
//        List<Position> path6 = elephant.getPathForMoving(E5, B3); // ← ↙ ↙
//        List<Position> path7 = elephant.getPathForMoving(E5, H7); // → ↗ ↗
//        List<Position> path8 = elephant.getPathForMoving(E5, H3); // → ↘ ↘

        // then
        assertThat(path1).containsExactly(E6, D7, C8);
//        assertThat(path2).containsExactly(F6, G8);
//        assertThat(path3).containsExactly(D4, C2);
//        assertThat(path4).containsExactly(F4, G2);
//        assertThat(path5).containsExactly(C5, B7);
//        assertThat(path6).containsExactly(C5, B3);
//        assertThat(path7).containsExactly(G5, H7);
//        assertThat(path8).containsExactly(G5, H3);
    }

    @Test
    void 상은_정해진_루트가_아니면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant();

        // then
        assertThatThrownBy(() -> elephant.getPathForMoving(E5, D5)) // 직선
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> elephant.getPathForMoving(E5, F6)) // 한칸 대각
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");

        assertThatThrownBy(() -> elephant.getPathForMoving(E5, G6)) // 대각 2칸
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }
}
