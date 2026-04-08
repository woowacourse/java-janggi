package domain.palace;

import domain.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    @Test
    void 한나라_궁성_영역에_포함되는지_판단한다() {
        Palace palace = new Palace(Position.of(1, 4));

        assertThat(palace.contains(Position.of(0, 3))).isTrue();
        assertThat(palace.contains(Position.of(1, 4))).isTrue();
        assertThat(palace.contains(Position.of(2, 5))).isTrue();

        assertThat(palace.contains(Position.of(3, 3))).isFalse();
        assertThat(palace.contains(Position.of(1, 2))).isFalse();
    }

    @Test
    void 초나라_궁성_영역에_포함되는지_판단한다() {
        Palace palace = new Palace(Position.of(8, 4));

        assertThat(palace.contains(Position.of(7, 3))).isTrue();
        assertThat(palace.contains(Position.of(8, 4))).isTrue();
        assertThat(palace.contains(Position.of(9, 5))).isTrue();

        assertThat(palace.contains(Position.of(6, 3))).isFalse();
        assertThat(palace.contains(Position.of(8, 6))).isFalse();
    }

    @Test
    void 궁성_내부에서_직선으로_이동_가능한_위치를_정확히_반환한다() {
        Palace palace = new Palace(Position.of(1, 4));

        List<Position> centerAdjacents = palace.getStraightAdjacents(Position.of(1, 4));
        assertThat(centerAdjacents).containsExactlyInAnyOrder(
                Position.of(0, 4), Position.of(2, 4), Position.of(1, 3), Position.of(1, 5)
        );

        List<Position> cornerAdjacents = palace.getStraightAdjacents(Position.of(0, 3));
        assertThat(cornerAdjacents).containsExactlyInAnyOrder(
                Position.of(1, 3), Position.of(0, 4)
        );

        List<Position> edgeAdjacents = palace.getStraightAdjacents(Position.of(0, 4));
        assertThat(edgeAdjacents).containsExactlyInAnyOrder(
                Position.of(1, 4), Position.of(0, 3), Position.of(0, 5)
        );
    }

    @Test
    void 궁성_내부에서_대각선으로_이동_가능한_위치를_정확히_반환한다() {
        Palace palace = new Palace(Position.of(1, 4));

        List<Position> centerDiagonals = palace.getDiagonalAdjacents(Position.of(1, 4));
        assertThat(centerDiagonals).containsExactlyInAnyOrder(
                Position.of(0, 3), Position.of(0, 5),
                Position.of(2, 3), Position.of(2, 5)
        );

        List<Position> cornerDiagonals = palace.getDiagonalAdjacents(Position.of(0, 3));
        assertThat(cornerDiagonals).containsExactly(Position.of(1, 4));

        List<Position> edgeDiagonals = palace.getDiagonalAdjacents(Position.of(0, 4));
        assertThat(edgeDiagonals).isEmpty();
    }

    @Test
    void 궁_사_이동은_대각선이_없는_위치에서_직선_인접점만_허용한다() {
        Palace palace = new Palace(Position.of(1, 4));

        List<Position> adjacents = palace.getAllAdjacents(Position.of(0, 4));

        assertThat(adjacents).containsExactlyInAnyOrder(
                Position.of(0, 3),
                Position.of(0, 5),
                Position.of(1, 4)
        );
    }

    @Test
    void 궁_사_이동은_중앙에서는_직선4방과_대각4방이_모두_허용된다() {
        Palace palace = new Palace(Position.of(1, 4));
        List<Position> adjacents = palace.getAllAdjacents(Position.of(1, 4));

        assertThat(adjacents).containsExactlyInAnyOrder(
                Position.of(0, 4), Position.of(2, 4), Position.of(1, 3), Position.of(1, 5),
                Position.of(0, 3), Position.of(0, 5), Position.of(2, 3), Position.of(2, 5)
        );
    }
}