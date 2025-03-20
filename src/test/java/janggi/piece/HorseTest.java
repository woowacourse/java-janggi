package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    @DisplayName("마 이동 가능 후보군 리턴 테스트")
    void test1() {
        Horse horse = new Horse(Side.CHO);

        List<Route> candidatePositions = horse.computeCandidatePositions(new Position(1, 9));

        assertAll(
                () -> assertThat(candidatePositions).hasSize(8),
                () -> assertThat(candidatePositions.getFirst().getDestination()).isEqualTo(new Position(3, 10))
        );
    }

}
