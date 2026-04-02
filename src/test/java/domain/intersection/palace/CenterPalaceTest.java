package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CenterPalaceTest {

    int centerY = 1;
    int centerX = 4;

    @Test
    @DisplayName("가운데 궁성에 포가 있으면 대각선 네 방향 한개씩 얻는다.")
    void shouldGetAllDiagonalWhenCannonInPalace() {
        // given
        Point centerPoint = new Point(centerY, centerX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Intersection intersection = new CenterPalace(centerPoint, cannon);
        Directions expected = new Directions(List.of(
                new Direction(List.of(Vector.LEFT_UP)),
                new Direction(List.of(Vector.LEFT_DOWN)),
                new Direction(List.of(Vector.RIGHT_UP)),
                new Direction(List.of(Vector.RIGHT_DOWN)))
        );

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("가운데 궁성에 차가 있으면 대각선 네 방향 한개씩 얻는다.")
    void shouldGetAllDiagonalWhenChariotInPalace() {
        // given
        Point centerPoint = new Point(centerY, centerX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Intersection intersection = new CenterPalace(centerPoint, chariot);
        Directions expected = new Directions(List.of(
                new Direction(List.of(Vector.LEFT_UP)),
                new Direction(List.of(Vector.LEFT_DOWN)),
                new Direction(List.of(Vector.RIGHT_UP)),
                new Direction(List.of(Vector.RIGHT_DOWN)))
        );

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("가운데 궁성에 장군이 있으면 대각선 네 방향 한개씩 얻는다.")
    void shouldGetAllDiagonalWhenGeneralInPalace() {
        // given
        Point centerPoint = new Point(centerY, centerX);
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Intersection intersection = new CenterPalace(centerPoint, general);
        Directions expected = new Directions(List.of(
                new Direction(List.of(Vector.LEFT_UP)),
                new Direction(List.of(Vector.LEFT_DOWN)),
                new Direction(List.of(Vector.RIGHT_UP)),
                new Direction(List.of(Vector.RIGHT_DOWN)))
        );

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("가운데 궁성에 사가 있으면 대각선 네 방향 한개씩 얻는다.")
    void shouldGetAllDiagonalWhenGuardInPalace() {
        // given
        Point centerPoint = new Point(centerY, centerX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Intersection intersection = new CenterPalace(centerPoint, guard);
        Directions expected = new Directions(List.of(
                new Direction(List.of(Vector.LEFT_UP)),
                new Direction(List.of(Vector.LEFT_DOWN)),
                new Direction(List.of(Vector.RIGHT_UP)),
                new Direction(List.of(Vector.RIGHT_DOWN)))
        );

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("가운데 궁성에 졸병이 있으면 대각선 네 방향 한개씩 얻는다.")
    void shouldGetAllDiagonalWhenSoliderInPalace() {
        // given
        Point centerPoint = new Point(centerY, centerX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Intersection intersection = new CenterPalace(centerPoint, solider);
        Directions expected = new Directions(List.of(
                new Direction(List.of(Vector.LEFT_UP)),
                new Direction(List.of(Vector.LEFT_DOWN)),
                new Direction(List.of(Vector.RIGHT_UP)),
                new Direction(List.of(Vector.RIGHT_DOWN)))
        );

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
