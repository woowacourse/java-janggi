package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NormalPalaceTest {

    int normalPalaceY = 0;
    int normalPalaceX = 4;

    @Test
    @DisplayName("일반 궁성에 포가 있어도 추가 이동 방향을 얻을 수 없다.")
    void shouldNotGetAnyDiagonalWhenCannonInNormalPalace() {
        // given
        Point normalPalacePoint = new Point(normalPalaceY, normalPalaceX);
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

        Directions expected = Directions.empty();
        Intersection intersection = new NormalPalace(normalPalacePoint, cannon);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("일반 궁성에 차가 있어도 추가 이동 방향을 얻을 수 없다.")
    void shouldNotGetAnyDiagonalWhenChariotInNormalPalace() {
        // given
        Point generalPalacePoint = new Point(normalPalaceY, normalPalaceX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.empty();
        Intersection intersection = new NormalPalace(generalPalacePoint, chariot);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("일반 궁성에 장군이 있어도 추가 이동 방향을 얻을 수 없다.")
    void shouldNotGetAnyDiagonalWhenGeneralInNormalPalace() {
        // given
        Point generalPalacePoint = new Point(normalPalaceY, normalPalaceX);
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Directions expected = Directions.empty();
        Intersection intersection = new NormalPalace(generalPalacePoint, chariot);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("일반 궁성에 사가 있어도 추가 이동 방향을 얻을 수 없다.")
    void shouldNotGetAnyDiagonalWhenGuardInNormalPalace() {
        // given
        Point generalPalacePoint = new Point(normalPalaceY, normalPalaceX);
        Piece guard = new Piece(Team.CHO, PieceType.GUARD);

        Directions expected = Directions.empty();
        Intersection intersection = new NormalPalace(generalPalacePoint, guard);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("일반 궁성에 졸병이 있어도 추가 이동 방향을 얻을 수 없다.")
    void shouldNotGetAnyDiagonalWhenSoliderInNormalPalace() {
        // given
        Point generalPalacePoint = new Point(normalPalaceY, normalPalaceX);
        Piece solider = new Piece(Team.CHO, PieceType.SOLDIER);

        Directions expected = Directions.empty();
        Intersection intersection = new NormalPalace(generalPalacePoint, solider);

        // when
        Directions actual = intersection.getDiagonalDirections();

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
