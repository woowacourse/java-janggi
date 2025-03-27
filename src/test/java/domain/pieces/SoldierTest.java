package domain.pieces;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SoldierTest {
    @Nested
    @DisplayName("피스가 이동할 수 있는 지점들을 반환할 때")
    class TestAbleToArrive {
        @Test
        @DisplayName("한나라인 경우 북쪽으로 이동한 지점이 반환되지 않는다.")
        void test_isAbleToArriveByHan() {
            // given
            Soldier soldier = new Soldier(Team.HAN, BoardStub.generateSoldierMovementForHan());
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(1, 0);

            // when
            boolean actual = soldier.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("초나라인 경우 남쪽으로 이동한 지점이 반환되지 않는다.")
        void test_isAbleToArriveByCho() {
            // given
            Soldier soldier = new Soldier(Team.CHO, BoardStub.generateSoldierMovementForCho());
            BoardPoint startBoardPoint = new BoardPoint(1, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(0, 0);

            // when
            boolean actual = soldier.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_getRoutePoints() {
        // given
        Soldier soldier = new Soldier(Team.CHO, BoardStub.generateSoldierMovementForCho());
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(1, 0);

        // when
        List<BoardPoint> routeBoardPoints = soldier.getRoutePoints(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(routeBoardPoints).containsExactlyInAnyOrder(
                new BoardPoint(1, 0)
        );
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeam() {
        //given
        Soldier soldier = new Soldier(Team.CHO, BoardStub.generateSoldierMovementForHan());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), soldier);

        //when&then
        assertThat(soldier.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Soldier soldierHan = new Soldier(Team.HAN, BoardStub.generateSoldierMovementForCho());
        Soldier soldierCho = new Soldier(Team.CHO, BoardStub.generateSoldierMovementForHan());
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), soldierCho);

        //when&then
        assertThat(soldierHan.isMovable(pieceOnRoute)).isTrue();
    }
}
