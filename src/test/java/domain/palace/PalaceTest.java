package domain.palace;

import domain.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    @Test
    void 한나라_궁성_영역에_포함되는지_판단한다() {
        Palace palace = Palace.createHanPalace();

        assertThat(palace.contains(Position.of(0, 3))).isTrue();
        assertThat(palace.contains(Position.of(1, 4))).isTrue();
        assertThat(palace.contains(Position.of(2, 5))).isTrue();
        assertThat(palace.contains(Position.of(3, 3))).isFalse();
        assertThat(palace.contains(Position.of(1, 2))).isFalse();
    }

    @Test
    void 초나라_궁성_영역에_포함되는지_판단한다() {
        Palace palace = Palace.createChoPalace();

        assertThat(palace.contains(Position.of(7, 3))).isTrue();
        assertThat(palace.contains(Position.of(8, 4))).isTrue();
        assertThat(palace.contains(Position.of(9, 5))).isTrue();
        assertThat(palace.contains(Position.of(6, 3))).isFalse();
        assertThat(palace.contains(Position.of(8, 6))).isFalse();
    }

    @Test
    void 궁성_대각선_링크는_중앙과_네_모서리를_연결한다() {
        Palace palace = Palace.createHanPalace();

        Position hanCenter = Position.of(1, 4);
        Position hanCorner = Position.of(0, 3);

        assertThat(palace.isDiagonalLink(hanCenter, hanCorner)).isTrue();
        assertThat(palace.isDiagonalLink(hanCorner, hanCenter)).isTrue();
        assertThat(palace.isDiagonalLink(Position.of(0, 4), Position.of(1, 3))).isFalse();
    }

    @Test
    void 궁_사_이동은_궁성_내부_선으로_연결된_인접점만_허용한다() {
        Palace palace = Palace.createHanPalace();

        List<Position> adjacentFromHanTopEdge = palace.getAdjacentPositions(Position.of(0, 4));

        assertThat(adjacentFromHanTopEdge).containsExactlyInAnyOrder(
                Position.of(0, 3),
                Position.of(0, 5),
                Position.of(1, 4)
        );
    }

    @Test
    void 궁_사_이동은_중앙에서는_직교4방과_대각4방이_모두_허용된다() {
        Palace palace = Palace.createHanPalace();

        List<Position> adjacentFromHanCenter = palace.getAdjacentPositions(Position.of(1, 4));

        assertThat(adjacentFromHanCenter).containsExactlyInAnyOrder(
                Position.of(0, 4),
                Position.of(2, 4),
                Position.of(1, 3),
                Position.of(1, 5),
                Position.of(0, 3),
                Position.of(0, 5),
                Position.of(2, 3),
                Position.of(2, 5)
        );
    }
}
