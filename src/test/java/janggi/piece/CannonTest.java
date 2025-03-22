package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CannonTest {

    @Test
    @DisplayName("포 이동 가능 후보군 리턴 테스트")
    void test1() {
        Cannon cannon = new Cannon(Side.CHO);

        List<Route> candidatePositions = cannon.computeCandidatePositions(new Position(1, 7));

        assertAll(
                () -> assertThat(candidatePositions).hasSize(4),
                () -> assertThat(candidatePositions.getFirst().getLastPosition()).isEqualTo(new Position(11, 7))
        );
    }

}
