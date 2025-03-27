package domain.pieces;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {
    @Test
    @DisplayName("피스가 이동할 수 있는 지점들을 전부 반환한다")
    void test_isAbleToArrive() {
        // given
        Elephant elephant = new Elephant(Team.CHO, BoardStub.generateElephantMovement());
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(3, 2);

        // when
        boolean actual = elephant.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_getRoutePoints() {
        // given
        Elephant elephant = new Elephant(Team.CHO, BoardStub.generateElephantMovement());
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(3, 2);

        // when
        List<BoardPoint> routeBoardPoints = elephant.getRoutePoints(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(routeBoardPoints).containsExactlyInAnyOrder(
                new BoardPoint(1, 0),
                new BoardPoint(2, 1),
                new BoardPoint(3, 2)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceOnRoute() {
        //given
        Elephant elephant = new Elephant(Team.CHO, BoardStub.generateElephantMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(elephant), null);

        //when&then
        assertThat(elephant.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 없다.")
    void test_isMovable() {
        //given
        Elephant elephant = new Elephant(Team.CHO, BoardStub.generateElephantMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), null);

        //when&then
        assertThat(elephant.isMovable(pieceOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeam() {
        //given
        Elephant elephant = new Elephant(Team.CHO, BoardStub.generateElephantMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), elephant);

        //when&then
        assertThat(elephant.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Elephant elephantHan = new Elephant(Team.HAN, BoardStub.generateElephantMovement());
        Elephant elephantCho = new Elephant(Team.CHO, BoardStub.generateElephantMovement());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), elephantCho);

        //when&then
        assertThat(elephantHan.isMovable(pieceOnRoute)).isTrue();
    }
}
