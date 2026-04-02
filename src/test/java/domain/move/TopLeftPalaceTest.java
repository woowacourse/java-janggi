package domain.move;

import domain.intersection.Intersection;
import domain.intersection.palace.TopLeftPalace;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TopLeftPalaceTest {

    int topLeftY = 0;
    int topLeftX = 3;

    @Test
    @DisplayName("좌상 궁성에 포가 있으면 오른쪽 아래 방향을 두개 얻는다.")
    void shouldGetDoubleRightDownDiagonalWhenCannonInPalace() {
        // given
        Point topLeftPoint = new Point(topLeftY, topLeftX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 2);
        Intersection intersection = new TopLeftPalace(topLeftPoint, cannon);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌상 궁성에 포가 있으면 오른쪽 아래 방향을 두개 얻는다.")
    void shouldGetDoubleRightDownDiagonalWhenChariotInPalace() {
        // given
        Point topLeftPoint = new Point(topLeftY, topLeftX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 2);
        Intersection intersection = new TopLeftPalace(topLeftPoint, chariot);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌상 궁성에 장군이 있으면 오른쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneRightDownDiagonalWhenGeneralInPalace() {
        // given
        Point topLeftPoint = new Point(topLeftY, topLeftX);
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 1);
        Intersection intersection = new TopLeftPalace(topLeftPoint, general);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌상 궁성에 졸병이 있으면 오른쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneRightDownDiagonalWhenSoliderInPalace() {
        // given
        Point topLeftPoint = new Point(topLeftY, topLeftX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 1);
        Intersection intersection = new TopLeftPalace(topLeftPoint, solider);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌상 궁성에 사가 있으면 오른쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneRightDownDiagonalWhenGuardInPalace() {
        // given
        Point topLeftPoint = new Point(topLeftY, topLeftX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 1);
        Intersection intersection = new TopLeftPalace(topLeftPoint, guard);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
