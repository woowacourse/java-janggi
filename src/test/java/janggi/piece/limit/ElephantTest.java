package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ElephantTest {

    @Test
    @DisplayName("상 이동 가능 후보군 리턴 테스트")
    void test1() {
        Elephant elephant = new Elephant(Side.CHO);

        List<Route> candidatePositions = elephant.computeCandidatePositions(new Position(2, 9));

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(
                        new Position(0, 6),
                        new Position(4, 6),
                        new Position(-1, 7),
                        new Position(-1, 11),
                        new Position(0, 12),
                        new Position(4, 12),
                        new Position(5, 7),
                        new Position(5, 11));
    }
}
