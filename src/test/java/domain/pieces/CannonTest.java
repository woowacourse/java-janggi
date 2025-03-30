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

class CannonTest {
    @Nested
    @DisplayName("도착 가능 여부를 확인할 때")
    class TestIsAbleToArrive {
        @Test
        @DisplayName("이동 가능하다면 true 를 반환한다.")
        void test_IsAbleToArrive() {
            // given
            Cannon cannon = new Cannon(Team.CHO);
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(0, 3);

            // when
            boolean actual = cannon.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("이동할 수 없다면 false 를 반환한다.")
        void test_IsNotAbleToArrive() {
            // given
            Cannon cannon = new Cannon(Team.CHO);
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(3, 3);

            // when
            boolean actual = cannon.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }

        private static Stream<Arguments> testCasesForIsAbleToArriveInPalace() {
            return Stream.of(
                    Arguments.of(new BoardPoint(0, 3), new BoardPoint(2, 3)),
                    Arguments.of(new BoardPoint(0, 3), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(0, 4), new BoardPoint(1, 4)),
                    Arguments.of(new BoardPoint(0, 5), new BoardPoint(2, 3)),
                    Arguments.of(new BoardPoint(1, 3), new BoardPoint(1, 5))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForIsAbleToArriveInPalace")
        @DisplayName("출발 지점과 도착 지점이 모두 궁 내부이고 도착 가능한 경우 true 를 반환한다")
        void test_IsAbleToArriveInPalace(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint) {
            // given
            Cannon cannon = new Cannon(Team.CHO);

            // when
            boolean actual = cannon.isAbleToArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }
    }


    @Test
    @DisplayName("도착점까지의 경로를 모두 반환한다.")
    void test_getRoutePoints() {
        // given
        Cannon cannon = new Cannon(Team.CHO);
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
        Cannon cannon = new Cannon(Team.CHO);
        Chariot chariot = new Chariot(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot), null);

        //when&then
        assertThat(cannon.isMovable(pieceOnRoute)).isTrue();
    }

    @Test
    @DisplayName("경로 상 포가 있으면 이동할 수 없다.")
    void test_isMovableWhenCannonOnRoute() {
        //given
        Cannon cannon = new Cannon(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(cannon), null);

        //when&then
        assertThat(cannon.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 없다.")
    void test_isMovable() {
        //given
        Cannon Cannon = new Cannon(Team.CHO);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(), null);

        //when&then
        assertThat(Cannon.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 둘이면 이동할 수 없다.")
    void test_isMovableWhenPiecesOnRoute() {
        //given
        Cannon Cannon = new Cannon(Team.CHO);
        Chariot chariot = new Chariot(Team.CHO);

        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot, chariot), null);

        //when&then
        assertThat(Cannon.isMovable(pieceOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Cannon cannon = new Cannon(Team.HAN);
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnRoute pieceOnRoute = new PieceOnRoute(List.of(chariot), chariot);

        //when&then
        assertThat(cannon.isMovable(pieceOnRoute)).isFalse();
    }
}
