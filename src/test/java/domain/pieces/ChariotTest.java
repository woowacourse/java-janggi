package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import domain.movements.EndlessMovement;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
    @Test
    @DisplayName("도착할 수 있는지 확인한다.")
    void test_IsAbleToArrive() {
        // given
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(0, 9);

        // when
        boolean actual = chariot.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("도착할 수 없는지 확인한다.")
    void test_IsNotAbleToArrive() {
        // given
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(3, 3);

        // when
        boolean actual = chariot.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isFalse();
    }


    @Test
    @DisplayName("도착 위치까지의 경로를 모두 반환한다.")
    void test_getRoutePoints() {
        // given
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(0, 3);

        // when
        List<Point> routePoints = chariot.getRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceOnRoute() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot, empty, empty));

        //when&then
        assertThat(chariot.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 있다.")
    void test_isMovable() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(empty, empty, empty));

        //when&then
        assertThat(chariot.isMovable(pieceOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeam() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(empty, empty, chariot));

        //when&then
        assertThat(chariot.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Chariot chariotHan = new Chariot(Team.HAN, new EndlessMovement());
        Chariot chariotCho = new Chariot(Team.CHO, new EndlessMovement());
        Piece empty = new EmptyPiece();
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(empty, empty, chariotCho));

        //when&then
        assertThat(chariotHan.isMovable(pieceOnRoute)).isTrue();
    }
}
