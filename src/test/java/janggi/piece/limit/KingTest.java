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
    @DisplayName("궁은 8개의 방향으로 이동 가능하다.")
    void test2() {
        King king = new King(Side.CHO);

        List<Route> candidatePositions = king.computeCandidatePositions(new Position(4, 8));

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(5, 8),
                        new Position(3, 8),
                        new Position(4, 9),
                        new Position(4, 7),
                        new Position(5, 9),
                        new Position(3, 7),
                        new Position(5, 7),
                        new Position(3, 9));
    }
}
