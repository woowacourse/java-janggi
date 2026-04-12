package janggi.domain;

import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.board.Vector;
import janggi.domain.piece.Piece;
import janggi.domain.rule.route.Direction;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntersectionTest {

    @Test
    @DisplayName("격자점이 궁성인 경우 True를 반환한다.")
    void shouldReturnTrueWhenIntersectionIsPalace() {
        // given
        List<Vector> vectors = List.of(new Vector(Direction.BACK, 2));
        Piece piece = new TestPiece(Side.CHO);

        // when
        Intersection intersection = Intersection.of(Location.of(1, 1), vectors, piece, true);

        // then
        Assertions.assertThat(intersection.isPalace()).isTrue();
    }

    @Test
    @DisplayName("격자점이 궁성이 아닌 경우 False를 반환한다.")
    void shouldReturnFalseWhenIntersectionIsNotPalace() {
        // given
        List<Vector> vectors = List.of(new Vector(Direction.BACK, 2));
        Piece piece = new TestPiece(Side.CHO);

        // when
        Intersection intersection = Intersection.of(Location.of(1, 1), vectors, piece, false);

        // then
        Assertions.assertThat(intersection.isPalace()).isFalse();
    }

    @Test
    @DisplayName("격자점은 주입받은 이동 벡터 리스트를 반환한다.")
    void shouldReturnAssignedVectors() {
        // given
        List<Vector> vectors = List.of(new Vector(Direction.BACK, 2), new Vector(Direction.FRONT, 5));
        Piece piece = new TestPiece(Side.CHO);

        // when
        Intersection intersection = Intersection.of(Location.of(1, 1), vectors, piece, false);

        // then
        Assertions.assertThat(intersection.getVectors()).isEqualTo(vectors);
    }
}
