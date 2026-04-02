package domain.move;

import domain.intersection.Intersection;
import domain.intersection.palace.TopRightPalace;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TopRightPalaceTest {

    int topRightY = 0;
    int topRightX = 5;

    @Test
    @DisplayName("우상 궁성에 포가 있으면 왼쪽 아래 방향을 두개 얻는다.")
    void shouldGetDoubleLeftDownDiagonalWhenCannonInPalace() {
        // given
        Point topRightPoint = new Point(topRightY, topRightX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Directions expected = Directions.cumulative(Vector.LEFT_DOWN, 2);
        Intersection intersection = new TopRightPalace(topRightPoint, cannon);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우상 궁성에 차가 있으면 왼쪽 아래 방향을 두개 얻는다.")
    void shouldGetDoubleLeftDownDiagonalWhenChariotInPalace() {
        // given
        Point topRightPoint = new Point(topRightY, topRightX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.cumulative(Vector.LEFT_DOWN, 2);
        Intersection intersection = new TopRightPalace(topRightPoint, chariot);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우상 궁성에 장군이 있으면 왼쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneLeftDownDiagonalWhenGeneralInPalace() {
        // given
        Point topRightPoint = new Point(topRightY, topRightX);
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Directions expected = Directions.cumulative(Vector.LEFT_DOWN, 1);
        Intersection intersection = new TopRightPalace(topRightPoint, general);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우상 궁성에 사가 있으면 왼쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneLeftDownDiagonalWhenGuardInPalace() {
        // given
        Point topRightPoint = new Point(topRightY, topRightX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Directions expected = Directions.cumulative(Vector.LEFT_DOWN, 1);
        Intersection intersection = new TopRightPalace(topRightPoint, guard);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우상 궁성에 졸병이 있으면 왼쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneLeftDownDiagonalWhenSoliderInPalace() {
        // given
        Point topRightPoint = new Point(topRightY, topRightX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Directions expected = Directions.cumulative(Vector.LEFT_DOWN, 1);
        Intersection intersection = new TopRightPalace(topRightPoint, solider);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
