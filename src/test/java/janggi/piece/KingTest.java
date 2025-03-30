package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다")
    void computeCandidateRoutes() {

        King king = new King(Side.CHO);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = king.filterReachableDestinations(new Position(4, 8), board);

        assertAll(
                () -> assertThat(destinations).hasSize(6),
                () -> assertThat(destinations.getFirst()).isEqualTo(new Position(3, 8))
        );
    }

}
