package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
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

        assertAll(
                () -> assertThat(candidatePositions).hasSize(4),
                () -> assertThat(candidatePositions.getLast().getDestination()).isEqualTo(new Position(3, 10))
        );
    }

}
