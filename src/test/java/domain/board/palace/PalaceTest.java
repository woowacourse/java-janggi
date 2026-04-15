package domain.board.palace;

import static org.assertj.core.api.Assertions.assertThat;

import domain.coordination.Coordination;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class PalaceTest {

    private final Palace palace = new Palace();

    @Test
    void 같은_궁성에_있는지_판단할_수_있다() {
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(6, 8);

        assertThat(palace.isSamePalace(from, to)).isTrue();
    }

    @Test
    void 궁성_밖의_좌표는_같은_궁성으로_판단하지_않는다() {
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(4, 7);

        assertThat(palace.isSamePalace(from, to)).isFalse();
    }

    @Test
    void 진영_기준으로_상대_궁성인지_판단할_수_있다() {
        assertThat(palace.isEnemyPalace(Coordination.of(4, 3), Team.CHO)).isTrue();
        assertThat(palace.isEnemyPalace(Coordination.of(4, 8), Team.HAN)).isTrue();
    }

    @Test
    void 궁성_대각선_경로를_찾을_수_있다() {
        PalaceRoute route = palace.diagonalRoute(Coordination.of(4, 10), Coordination.of(6, 8));

        assertThat(route.exists()).isTrue();
        assertThat(route.path()).containsExactly(Coordination.of(5, 9));
    }

    @Test
    void 궁성_대각선이_아니면_빈_경로를_반환한다() {
        PalaceRoute route = palace.diagonalRoute(Coordination.of(4, 10), Coordination.of(4, 8));

        assertThat(route.exists()).isFalse();
        assertThat(route.path()).isEmpty();
    }
}
