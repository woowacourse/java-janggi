package domain.palace;

import domain.Position;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceGeometryTest {

    @Test
    void 한나라_궁성_영역에_포함되는지_판단한다() {
        PalaceGeometry palaceGeometry = new PalaceGeometry();

        assertThat(palaceGeometry.isInsideMyPalace(Position.of(0, 3), TeamColor.HAN)).isTrue();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(1, 4), TeamColor.HAN)).isTrue();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(2, 5), TeamColor.HAN)).isTrue();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(3, 3), TeamColor.HAN)).isFalse();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(1, 2), TeamColor.HAN)).isFalse();
    }

    @Test
    void 초나라_궁성_영역에_포함되는지_판단한다() {
        PalaceGeometry palaceGeometry = new PalaceGeometry();

        assertThat(palaceGeometry.isInsideMyPalace(Position.of(7, 3), TeamColor.CHO)).isTrue();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(8, 4), TeamColor.CHO)).isTrue();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(9, 5), TeamColor.CHO)).isTrue();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(6, 3), TeamColor.CHO)).isFalse();
        assertThat(palaceGeometry.isInsideMyPalace(Position.of(8, 6), TeamColor.CHO)).isFalse();
    }

    @Test
    void 궁성_대각선_링크는_중앙과_네_모서리를_연결한다() {
        PalaceGeometry palaceGeometry = new PalaceGeometry();

        Position hanCenter = Position.of(1, 4);
        Position hanCorner = Position.of(0, 3);

        assertThat(palaceGeometry.isPalaceDiagonalLink(hanCenter, hanCorner)).isTrue();
        assertThat(palaceGeometry.isPalaceDiagonalLink(hanCorner, hanCenter)).isTrue();
        assertThat(palaceGeometry.isPalaceDiagonalLink(Position.of(0, 4), Position.of(1, 3))).isFalse();
    }

    @Test
    void 궁_사_이동은_궁성_내부_선으로_연결된_인접점만_허용한다() {
        PalaceGeometry palaceGeometry = new PalaceGeometry();

        List<Position> adjacentFromHanTopEdge = palaceGeometry.adjacentPositionsInsideMyPalace(
                Position.of(0, 4),
                TeamColor.HAN
        );

        assertThat(adjacentFromHanTopEdge).containsExactlyInAnyOrder(
                Position.of(0, 3),
                Position.of(0, 5),
                Position.of(1, 4)
        );
    }

    @Test
    void 궁_사_이동은_중앙에서는_직교4방과_대각4방이_모두_허용된다() {
        PalaceGeometry palaceGeometry = new PalaceGeometry();

        List<Position> adjacentFromHanCenter = palaceGeometry.adjacentPositionsInsideMyPalace(
                Position.of(1, 4),
                TeamColor.HAN
        );

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

