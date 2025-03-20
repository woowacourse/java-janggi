package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {
    @Test
    @DisplayName("피스가 이동할 수 있는 지점들을 전부 반환한다")
    void test_isAbleToArrive() {
        // given
        Horse horse = new Horse(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(2, 1);

        // when
        boolean actual = horse.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_getRoutePoints() {
        // given
        Horse horse = new Horse(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(2, 1);

        // when
        List<Point> routePoints = horse.getRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(1, 0),
                new Point(2, 1)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceOnRoute() {
        //given
        Horse horse = new Horse(Team.CHO);
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(horse, empty));

        //when&then
        assertThat(horse.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 없다.")
    void test_isMovable() {
        //given
        Horse horse = new Horse(Team.CHO);
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(empty, empty));

        //when&then
        assertThat(horse.isMovable(pieceOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeam() {
        //given
        Horse horse = new Horse(Team.CHO);
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(empty, horse));

        //when&then
        assertThat(horse.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Horse horseHan = new Horse(Team.HAN);
        Horse horseCho = new Horse(Team.CHO);
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(empty, horseCho));

        //when&then
        assertThat(horseHan.isMovable(pieceOnRoute)).isTrue();
    }
}
