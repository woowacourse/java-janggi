package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GuardTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다")
    void computeCandidatePositions() {

        Guard guard = new Guard(Side.CHO);
        Position currentPosition = new Position(3, 9);
        List<Route> candidatePositions = guard.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                        .contains(
                                new Position(4, 9),
                                new Position(3, 8),
                                new Position(3, 10),
                                new Position(2, 9)
                        );
    }

}
