package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {

    @Test
    @DisplayName("3, 9의 위치 (궁성의 왼쪽 아래 꼭지점)에서는 가능한 이동이 대각선 이동 포함 5개이다.")
    void test1() {

        Guard guard = new Guard(Side.CHO);
        Position currentPosition = new Position(3, 9);
        List<Route> candidatePositions = guard.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                        .contains(
                                new Position(4, 9),
                                new Position(3, 8),
                                new Position(2, 9),
                                new Position(4, 8)
                        );
    }

    @Test
    @DisplayName("졸이 3, 8의 위치 (궁성의 왼쪽 가운데)에서는 가능한 이동이 3개이다. (대각선 이동 불가)")
    void test10() {
        Guard guard = new Guard(Side.CHO);
        Position position = new Position(3, 8);

        List<Route> reachableDestinations = guard.computeCandidatePositions(position);

        assertThat(reachableDestinations).extracting(Route::getLastPosition)
                .contains(new Position(3, 7),
                        new Position(4, 8),
                        new Position(3, 9));
    }

    @Test
    @DisplayName("졸이 5, 9의 위치 (궁성의 오른쪽 아래)에서는 가능한 이동이 대각선 이동 포함 3개이다.")
    void test11() {
        Guard guard = new Guard(Side.CHO);
        Position position = new Position(5, 9);

        List<Route> reachableDestinations = guard.computeCandidatePositions(position);

        assertThat(reachableDestinations).extracting(Route::getLastPosition)
                .contains(new Position(5, 8),
                        new Position(4, 8),
                        new Position(4, 9));
    }
}
