package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static common.constant.JanggiConstant.GENERAL_PIECE_MAX_DISTANCE;

class LeftBottomPalaceTest {

    int leftBottomY = 2;
    int leftBottomX = 3;

    @Test
    @DisplayName("좌하 궁성에 포가 있으면 오른쪽 위 방향을 두개 얻는다.")
    void shouldGetDoubleRightUpDiagonalWhenCannonInPalace() {
        // given
        Point leftBottomPoint = new Point(leftBottomY, leftBottomX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Directions expected = Directions.cumulative(Vector.RIGHT_UP, 2);
        Intersection intersection = new LeftBottomPalace(leftBottomPoint, cannon);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌하 궁성에 차가 있으면 오른쪽 위 방향을 두개 얻는다.")
    void shouldGetDoubleRightUpDiagonalWhenChariotInPalace() {
        // given
        Point leftBottomPoint = new Point(leftBottomY, leftBottomX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.cumulative(Vector.RIGHT_UP, 2);
        Intersection intersection = new LeftBottomPalace(leftBottomPoint, chariot);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌하 궁성에 장군이 있으면 오른쪽 위 방향을 한개 얻는다.")
    void shouldGetDoubleRightUpDiagonalWhenGeneralInPalace() {
        // given
        Point leftBottomPoint = new Point(leftBottomY, leftBottomX);
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Directions expected = Directions.cumulative(Vector.RIGHT_UP, 1);
        Intersection intersection = new LeftBottomPalace(leftBottomPoint, general);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌하 궁성에 사가 있으면 오른쪽 위 방향을 한개 얻는다.")
    void shouldGetDoubleRightUpDiagonalWhenGuardInPalace() {
        // given
        Point leftBottomPoint = new Point(leftBottomY, leftBottomX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Directions expected = Directions.cumulative(Vector.RIGHT_UP, 1);
        Intersection intersection = new LeftBottomPalace(leftBottomPoint, guard);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌하 궁성에 졸병이 있으면 오른쪽 위 방향을 한개 얻는다.")
    void shouldGetDoubleRightUpDiagonalWhenSoliderInPalace() {
        // given
        Point leftBottomPoint = new Point(leftBottomY, leftBottomX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Directions expected = Directions.cumulative(Vector.RIGHT_UP, 1);
        Intersection intersection = new LeftBottomPalace(leftBottomPoint, solider);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
