package domain.pieces;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
import domain.movements.EndlessMovement;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {
    @Test
    @DisplayName("도착할 수 있는지 확인한다.")
    void test_IsAbleToArrive() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new EndlessMovement());
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(0, 3);

        // when
        boolean actual = cannon.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("도착할 수 없는지 확인한다.")
    void test_IsNotAbleToArrive() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new EndlessMovement());
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(3, 3);

        // when
        boolean actual = cannon.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(actual).isFalse();
    }


    @Test
    @DisplayName("도착점까지의 경로를 모두 반환한다.")
    void test_getRoutePoints() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new EndlessMovement());
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(0, 3);

        // when
        List<BoardPoint> routeBoardPoints = cannon.getRoutePoints(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(routeBoardPoints).containsExactlyInAnyOrder(
                new BoardPoint(0, 1),
                new BoardPoint(0, 2),
                new BoardPoint(0, 3)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 하나 있어야 이동 할 수 있다.")
    void test_isMovableWhenPieceOnRoute() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new EndlessMovement());
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot), null);

        //when&then
        assertThat(cannon.isMovable(pieceOnRoute)).isTrue();
    }

    @Test
    @DisplayName("경로 상 포가 있으면 이동할 수 없다.")
    void test_isMovableWhenCannonOnRoute() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new EndlessMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(cannon), null);

        //when&then
        assertThat(cannon.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 없다.")
    void test_isMovable() {
        //given
        Cannon Cannon = new Cannon(Team.CHO, new EndlessMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), null);

        //when&then
        assertThat(Cannon.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 둘이면 이동할 수 없다.")
    void test_isMovableWhenPiecesOnRoute() {
        //given
        Cannon Cannon = new Cannon(Team.CHO, new EndlessMovement());
        Chariot chariot = new Chariot(Team.CHO, new EndlessMovement());

        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot, chariot), null);

        //when&then
        assertThat(Cannon.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Cannon cannon = new Cannon(Team.HAN, new EndlessMovement());
        Chariot chariot = new Chariot(Team.HAN, new EndlessMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot), chariot);

        //when&then
        assertThat(cannon.isMovable(pieceOnRoute)).isFalse();
    }
}
