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

class ChariotTest {
    @Nested
    @DisplayName("도착 가능 여부를 확인할 때")
    class TestIsAbleToArrive {
        @Test
        @DisplayName("true 를 반환한다.")
        void test_IsAbleToArrive() {
            // given
            Chariot chariot = new Chariot(Team.CHO);
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(0, 8);

            // when
            boolean actual = chariot.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("도착할 수 없는지 확인한다.")
        void test_IsNotAbleToArrive() {
            // given
            Chariot chariot = new Chariot(Team.CHO);
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(3, 3);

            // when
            boolean actual = chariot.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }

        private static Stream<Arguments> testCasesForIsAbleToArriveInPalace() {
            return Stream.of(
                    Arguments.of(new BoardPoint(0, 3), new BoardPoint(2, 5)),
                    Arguments.of(new BoardPoint(0, 4), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(0, 5), new BoardPoint(1, 5)),
                    Arguments.of(new BoardPoint(0, 5), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(1, 3), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(1, 4), new BoardPoint(2, 5))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForIsAbleToArriveInPalace")
        @DisplayName("출발 지점과 도착 지점이 모두 궁 내부이고 도착 가능한 경우 true 를 반환한다")
        void test_IsAbleToArriveInPalace(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint) {
            // given
            Chariot chariot = new Chariot(Team.CHO);

            // when
            boolean actual = chariot.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }
    }

    @Test
    @DisplayName("도착 위치까지의 경로를 모두 반환한다.")
    void test_getRoutePoints() {
        // given
        Chariot chariot = new Chariot(Team.CHO);
        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint arrivalBoardPoint = new BoardPoint(0, 3);

        // when
        List<BoardPoint> routeBoardPoints = chariot.getRoutePoints(startBoardPoint, arrivalBoardPoint);

        // then
        assertThat(routeBoardPoints).containsExactlyInAnyOrder(
                new BoardPoint(0, 1),
                new BoardPoint(0, 2),
                new BoardPoint(0, 3)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceOnRoute() {
        //given
        Chariot chariot = new Chariot(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot), null);

        //when&then
        assertThat(chariot.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 있다.")
    void test_isMovable() {
        //given
        Chariot chariot = new Chariot(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), null);

        //when&then
        assertThat(chariot.isMovable(pieceOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeam() {
        //given
        Chariot chariot = new Chariot(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), chariot);

        //when&then
        assertThat(chariot.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Chariot chariotHan = new Chariot(Team.HAN);
        Chariot chariotCho = new Chariot(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), chariotCho);

        //when&then
        assertThat(chariotHan.isMovable(pieceOnRoute)).isTrue();
    }
}
