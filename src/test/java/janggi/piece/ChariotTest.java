package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChariotTest {

    @Test
    @DisplayName("차 이동 가능 후보군 리턴 테스트")
    void test1() {
        Chariot chariot = new Chariot(Side.CHO);

        List<Route> candidatePositions = chariot.computeCandidatePositions(new Position(2, 9));

        assertAll(
                () -> assertThat(candidatePositions).hasSize(4),
                () -> assertThat(candidatePositions.getFirst().getLastPosition()).isEqualTo(new Position(12, 9))
        );
    }

}
