package janggi.piece;

import janggi.Side;
import janggi.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ElephantTest {

    @Test
    @DisplayName("상 이동 가능 후보군 리턴 테스트")
    void test1() {
        Elephant elephant = new Elephant(Side.CHO);

        List<Position> candidatePositions = elephant.computeCandidatePositions(new Position(2, 9));

        assertAll(
                () -> assertThat(candidatePositions).hasSize(8),
                () -> assertThat(candidatePositions).contains(new Position(5, 7))
        );
    }

}
