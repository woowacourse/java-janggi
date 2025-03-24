package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        Piece piece = new Horse(Team.CHO);

        //when&then
        assertThat(piece.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(Team.HAN)).isFalse();
    }

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
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(horse, null));

        //when&then
        assertThat(horse.isMovable(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 없다.")
    void test_isMovable() {
        //given
        Horse horse = new Horse(Team.CHO);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null));

        //when&then
        assertThat(horse.isMovable(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeam() {
        //given
        Horse horse = new Horse(Team.CHO);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, horse));

        //when&then
        assertThat(horse.isMovable(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 적군 기물이 있을 경우, 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Horse horseHan = new Horse(Team.HAN);
        Horse horseCho = new Horse(Team.CHO);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, horseCho));

        //when&then
        assertThat(horseHan.isMovable(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("마는 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        Piece pieceForCho = new Horse(Team.CHO);
        Piece pieceForHan = new Horse(Team.HAN);

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("마");
        assertThat(pieceForHan.getName()).isEqualTo("馬");
    }
}
