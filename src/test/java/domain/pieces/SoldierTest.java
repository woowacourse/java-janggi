package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {
    @Nested
    @DisplayName("이동 가능 여부를 판단할 때")
    class TestAbleToArrive {
        @Test
        @DisplayName("한나라인 경우 북쪽으로 이동할 수 없다.")
        void test_isAbleToArriveByHan() {
            // given
            Soldier soldier = new Soldier(Team.HAN);
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(1, 0);

            // when
            boolean actual = soldier.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("초나라인 경우 남쪽으로 이동할 수 없다.")
        void test_isAbleToArriveByCho() {
            // given
            Soldier soldier = new Soldier(Team.CHO);
            BoardPoint startBoardPoint = new BoardPoint(1, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(0, 0);

            // when
            boolean actual = soldier.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }

        private static Stream<Arguments> testCasesForIsAbleToArriveInPalaceForCho() {
            return Stream.of(
                    Arguments.of(new BoardPoint(0, 4), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(0, 5), new BoardPoint(1, 5)),
                    Arguments.of(new BoardPoint(0, 5), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(1, 3), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(1, 4), new BoardPoint(2, 5))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForIsAbleToArriveInPalaceForCho")
        @DisplayName("초나라의 졸이 출발 지점과 도착 지점이 모두 궁 내부이고 도착 가능한 경우 true 를 반환한다")
        void test_IsAbleToArriveInPalaceForCho(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint) {
            // given
            Soldier chariot = new Soldier(Team.CHO);

            // when
            boolean actual = chariot.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }

        private static Stream<Arguments> testCasesForIsAbleToArriveInPalaceForHan() {
            return Stream.of(
                    Arguments.of(new BoardPoint(2, 5), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(1, 4), new BoardPoint(0, 3)),
                    Arguments.of(new BoardPoint(2, 5), new BoardPoint(1, 5)),
                    Arguments.of(new BoardPoint(2, 3), new BoardPoint(1, 3))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForIsAbleToArriveInPalaceForHan")
        @DisplayName("한나라의 졸이 출발 지점과 도착 지점이 모두 궁 내부이고 도착 가능한 경우 true 를 반환한다")
        void test_IsAbleToArriveInPalaceForHan(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint) {
            // given
            Soldier soldier = new Soldier(Team.HAN);

            // when
            boolean actual = soldier.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_getRoutePoints() {
        // given
        Soldier soldier = new Soldier(Team.CHO);
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
        Soldier soldier = new Soldier(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), soldier);

        //when&then
        assertThat(soldier.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Soldier soldierHan = new Soldier(Team.HAN);
        Soldier soldierCho = new Soldier(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), soldierCho);

        //when&then
        assertThat(soldierHan.isMovable(pieceOnRoute)).isTrue();
    }
}
