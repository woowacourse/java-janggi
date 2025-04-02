package janggi.piece.limit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Route;
import janggi.domain.piece.limit.Elephant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    @Test
    @DisplayName("상 이동 가능 후보군 리턴 테스트")
    void test1() {
        Elephant elephant = new Elephant(Turn.CHO);

        List<Route> candidatePositions = elephant.computeCandidatePositions(new Position(2, 9));

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(
                        new Position(0, 6),
                        new Position(4, 6),
                        new Position(5, 7));
    }
}
