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

class RightBottomPalaceTest {

    int rightBottomY = 2;
    int rightBottomX = 5;

    @Test
    @DisplayName("우하 궁성에 포가 있으면 왼쪽 위 방향을 두개 얻는다.")
    void shouldGetDoubleLeftUpDiagonalWhenCannonInPalace() {
        // given
        Point rightBottomPoint = new Point(rightBottomY, rightBottomX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Directions expected = Directions.cumulative(Vector.LEFT_UP, 2);
        Intersection intersection = new RightBottomPalace(rightBottomPoint, cannon);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우하 궁성에 차가 있으면 왼쪽 위 방향을 두개 얻는다.")
    void shouldGetDoubleLeftUpDiagonalWhenChariotInPalace() {
        // given
        Point rightBottomPoint = new Point(rightBottomY, rightBottomX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.cumulative(Vector.LEFT_UP, 2);
        Intersection intersection = new RightBottomPalace(rightBottomPoint, chariot);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우하 궁성에 장군이 있으면 왼쪽 위 방향을 한개 얻는다.")
    void shouldGetOneLeftUpDiagonalWhenGeneralInPalace() {
        // given
        Point rightBottomPoint = new Point(rightBottomY, rightBottomX);
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Directions expected = Directions.cumulative(Vector.LEFT_UP, 1);
        Intersection intersection = new RightBottomPalace(rightBottomPoint, general);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우하 궁성에 사가 있으면 왼쪽 위 방향을 한개 얻는다.")
    void shouldGetOneLeftUpDiagonalWhenGuardInPalace() {
        // given
        Point rightBottomPoint = new Point(rightBottomY, rightBottomX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Directions expected = Directions.cumulative(Vector.LEFT_UP, 1);
        Intersection intersection = new RightBottomPalace(rightBottomPoint, guard);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("우하 궁성에 졸병이 있으면 왼쪽 위 방향을 한개 얻는다.")
    void shouldGetOneLeftUpDiagonalWhenSoliderInPalace() {
        // given
        Point rightBottomPoint = new Point(rightBottomY, rightBottomX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Directions expected = Directions.cumulative(Vector.LEFT_UP, 1);
        Intersection intersection = new RightBottomPalace(rightBottomPoint, solider);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
