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

class LeftTopPalaceTest {

    int leftTopY = 0;
    int leftTopX = 3;

    @Test
    @DisplayName("좌상 궁성에 포가 있으면 오른쪽 아래 방향을 두개 얻는다.")
    void shouldGetDoubleRightDownDiagonalWhenCannonInPalace() {
        // given
        Point leftTopPoint = new Point(leftTopY, leftTopX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 2);
        Intersection intersection = new LeftTopPalace(leftTopPoint, cannon);

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
        Point leftTopPoint = new Point(leftTopY, leftTopX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 2);
        Intersection intersection = new LeftTopPalace(leftTopPoint, chariot);

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
        Point leftTopPoint = new Point(leftTopY, leftTopX);
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 1);
        Intersection intersection = new LeftTopPalace(leftTopPoint, general);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌상 궁성에 졸병이 있으면 오른쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneRightDownDiagonalWhenSoliderInPalace() {
        // given
        Point leftTopPoint = new Point(leftTopY, leftTopX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 1);
        Intersection intersection = new LeftTopPalace(leftTopPoint, solider);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("좌상 궁성에 사가 있으면 오른쪽 아래 방향을 한개 얻는다.")
    void shouldGetOneRightDownDiagonalWhenGuardInPalace() {
        // given
        Point leftTopPoint = new Point(leftTopY, leftTopX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Directions expected = Directions.cumulative(Vector.RIGHT_DOWN, 1);
        Intersection intersection = new LeftTopPalace(leftTopPoint, guard);

        // when
        Directions actual = intersection.getDiagonalDirections()
                .limitDistance(GENERAL_PIECE_MAX_DISTANCE);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
