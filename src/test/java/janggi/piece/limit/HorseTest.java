package janggi.piece.limit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Route;
import janggi.domain.piece.limit.Horse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {

    @Test
    @DisplayName("초기 위치인 1, 9에 위치했을 때 이동 가능 후보군 리턴 테스트")
    void test1() {
        Horse horse = new Horse(Turn.CHO);

        List<Route> candidatePositions = horse.computeCandidatePositions(new Position(1, 9));

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                        .contains(new Position(0, 7),
                                new Position(2, 7),
                                new Position(3, 8));
    }

    @Test
    @DisplayName("3, 5에 위치했을 때 이동 가능 후보군 리턴 테스트")
    void test2() {
        Horse horse = new Horse(Turn.CHO);

        List<Route> candidatePositions = horse.computeCandidatePositions(new Position(3, 5));

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(2, 3),
                        new Position(4, 3),
                        new Position(1, 4),
                        new Position(1, 6),
                        new Position(2, 7),
                        new Position(4, 7),
                        new Position(5, 4),
                        new Position(5, 6));
    }

}
