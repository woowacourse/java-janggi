package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다")
    void computeCandidatePositions() {

        King king = new King(Side.CHO);
        Position currentPosition = new Position(4, 8);
        List<Route> candidatePositions = king.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(5, 8),
                        new Position(3, 8),
                        new Position(4, 9),
                        new Position(4, 7));
    }

}
